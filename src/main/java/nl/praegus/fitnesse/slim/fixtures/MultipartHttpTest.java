package nl.praegus.fitnesse.slim.fixtures;

import nl.hsac.fitnesse.fixture.slim.HttpTest;
import nl.hsac.fitnesse.fixture.slim.SlimFixtureException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.io.IOException;

public class MultipartHttpTest extends HttpTest {

    private final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    public MultipartHttpTest() {
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    }

    public void createFilePart(String filename) {
        String filePath = getFilePathFromWikiUrl(filename);
        File file = new File(filePath);
        FormBodyPart bodyPart = FormBodyPartBuilder.create()
                .setName("Screenshot from 2018-10-03 21-19-19.png")
                .addField("Content-Transfer-Encoding", "binary")
                .addField("Content-Type", "image/x-png; name=\"Screenshot from 2018-10-03 21-19-19.png\"")
                .addField("Content-ID", "<Screenshot.png>")
                .addField("Content-Disposition","attachment; name=\"Screenshot.png\"; filename=\"Screenshot from 2018-10-03 21-19-19.png\"")
                .setBody(new FileBody(file))
                .build();
        builder.addPart(bodyPart);
    }

    public void createTextPartWithNameAndBodyAndContentType(String name, String body, String contentType) {
        FormBodyPart bodyPart = FormBodyPartBuilder.create()
                .setName(name)
                .addField("Content-Transfer-Encoding", "8bit")
                .addField("Content-ID", "rootpart@fitnesse")
                .addField("Content-Type", "text/xml; charset=UTF-8")
                .setBody(new StringBody(body, ContentType.TEXT_XML))
                .build();

        builder.addPart(bodyPart);
    }

    public boolean postMessageTo(String url) {
        final HttpPost post = new HttpPost(url);
        HttpEntity entity = builder.build();
        post.setEntity(entity);

        try {
            System.out.println(IOUtils.toString(post.getEntity().getContent()));
            org.apache.http.HttpResponse resp = getEnvironment().getHttpClient().getHttpClient().execute(post);

           return true;
        } catch (IOException e) {
            throw new SlimFixtureException(e);
        }
    }
}
