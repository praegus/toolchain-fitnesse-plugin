package nl.praegus.fitnesse.slim.fixtures;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.StringUtils;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;
import nl.hsac.fitnesse.fixture.slim.SlimFixture;
import nl.hsac.fitnesse.fixture.util.FileUtil;
import org.apache.commons.text.StringEscapeUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GmailOauthFixture extends SlimFixture {
    private String APPLICATION_NAME;
    private FileDataStoreFactory DATA_STORE_FACTORY;
    private JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private HttpTransport HTTP_TRANSPORT;
    private List<String> SCOPES = Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM);
    private String filterQuery = "";
    private Gmail service;
    private String user = "me"; //Default to special user "me" - pointing to the logged in user

    private String latestMessageBody = "";
    private List<String> latestMessageAttachments = new ArrayList<>();
    private String latestMessageId;
    private String clientSecretPostfix; //use a postfixes if you need more than 1 gmail account in one test project
    private String bodyMimeType = "text/plain";
    private static final Pattern URL_PATTERN =
            Pattern.compile("https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_+.~#?&/=!]*)");

    public GmailOauthFixture(String appName) {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            APPLICATION_NAME = appName;
            File DATA_STORE_DIR = new File(this.filesDir, String.format("gmailOauthFixture/%s/.credentials/gmail-credentials", APPLICATION_NAME));
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
            service = getGmailService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setClientSecretPostfix(String clientSecretPostfix) {
        this.clientSecretPostfix = clientSecretPostfix;
    }

    public void setFilterQuery(String filterQuery) {
        this.filterQuery = filterQuery;
    }

    public void setMessageFormat(String format) { //Can be plain or html
        bodyMimeType = "text/" + format;
    }

    public String latestMessageBody() {
        if (bodyMimeType.equalsIgnoreCase("text/html")) {
            return "<pre>" + StringEscapeUtils.escapeHtml4(latestMessageBody) + "</pre>";
        }
        return latestMessageBody;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public String saveLatestEmailBody(String fileName) {
        String fullName = String.format("%s/emails/%s.html", this.filesDir, fileName);
        File f = new File(fullName);
        File parentFile = f.getParentFile();
        parentFile.mkdirs();
        f = FileUtil.writeFile(fullName, latestMessageBody);
        return String.format("<a href=\"%s\" target=\"_blank\">%s</a>", this.getEnvironment().getWikiUrl(f.getAbsolutePath()), f.getName());
    }

    public boolean latestMessageBodyContains(String needle) {
        return latestMessageBody.contains(needle);
    }

    public List<String> latestMessageAttachments() {
        return latestMessageAttachments;
    }

    public String getLinkContaining(String needle) {
        Matcher urlMatcher = URL_PATTERN.matcher(latestMessageBody);
        while (urlMatcher.find()) {
            if (urlMatcher.group(0).contains(needle)) {
                return urlMatcher.group(0);
            }
        }
        return "No match in message URL's";
    }

    public boolean trashCurrentMessage() {
        try {
            service.users().messages().trash(user, latestMessageId).execute();
            return true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteCurrentMessage() {
        try {
            service.users().messages().delete(user, latestMessageId).execute();
            return true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    private void processPart(MessagePart part, StringBuilder sb, List<String> attachments) {
        if (part.getParts() != null) {
            for (MessagePart prt : part.getParts()) {
                processPart(prt, sb, attachments);
            }
        } else {
            if (part.getMimeType().equalsIgnoreCase(bodyMimeType)) {
                sb.append(part.getBody().getData());
            } else if (part.getMimeType().toLowerCase().startsWith("application")) {
                attachments.add(part.getFilename());
            }
        }
    }

    public boolean pollUntilMessageArrives() {
        return repeatUntil(inboxRetrievedCompletion());
    }

    private FunctionalCompletion inboxRetrievedCompletion() {
        return new FunctionalCompletion(() -> ((null != filteredInbox() && Objects.requireNonNull(filteredInbox()).size() != 0)));
    }

    private void getLatestMessageInfo() {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> attachments = new ArrayList<>();
            Message message = service.users().messages().get(user, latestMessageId).setFormat("full").execute();
            for (MessagePart part : message.getPayload().getParts()) {
                processPart(part, sb, attachments);
            }
            latestMessageBody = StringUtils.newStringUtf8(Base64.decodeBase64(sb.toString()));
            latestMessageAttachments = attachments;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Credential authorize() throws IOException {
        // Load client secrets. - Make sure to put them in your resources folder!
        String clientSecret = "/gmail_client_secret";
        if (null != clientSecretPostfix) {
            clientSecret += "_" + clientSecretPostfix;
        }
        InputStream in =
                GmailOauthFixture.class.getResourceAsStream(clientSecret + ".json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();

        return new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
    }

    private Gmail getGmailService() throws IOException {
        Credential credential = authorize();
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private List<Message> filteredInbox() {
        try {
            ListMessagesResponse msgResponse = service.users().messages().list(user).setQ(filterQuery).execute();
            List<Message> messages = msgResponse.getMessages();
            if (null != messages && messages.size() > 0) {
                latestMessageId = messages.get(0).getId();
                getLatestMessageInfo();
            }
            return messages;
        } catch (IOException e) {
            System.err.println("Exception fetching e-mail: " + e.getMessage());
            return null;
        }
    }

}
