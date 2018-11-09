package nl.praegus.fitnesse.slim.fixtures;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.jcraft.jsch.*;
import nl.hsac.fitnesse.fixture.slim.FileFixture;
import nl.hsac.fitnesse.fixture.slim.SlimFixtureException;
import nl.hsac.fitnesse.fixture.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

public class SftpFixture extends FileFixture {

    private JSch jsch = new JSch();
    private String username;
    private String password;
    private String host;
    private int port = 22;
    private Session session;
    private ChannelSftp sftpChannel;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPrivateKey(String privateKey) throws JSchException {
        jsch.addIdentity(privateKey);
    }

    public void setPrivateKeyFileWithPassphrase(String privateKey, String passphrase) throws JSchException {
        jsch.addIdentity(privateKey, passphrase);
    }

    public String downloadFileTo(String remoteFile, String localFile) {
        try {
            connect();
            openChannel();
            return createContaining(localFile, FileUtil.streamToString(sftpChannel.get(remoteFile), remoteFile));
        } catch (JSchException | SftpException e) {
            throw new SlimFixtureException("Failed to download: " + remoteFile, e);
        } finally {
            sftpChannel.exit();
            session.disconnect();
        }
    }

    public boolean uploadFileTo(String localFile, String remoteFile) {
        try {
            connect();
            openChannel();
            sftpChannel.put(getFullName(localFile), remoteFile);
            return true;
        } catch (JSchException | SftpException e) {
            throw new SlimFixtureException("Failed to upload " + localFile + " to " + remoteFile, e);
        } finally {
            sftpChannel.exit();
            session.disconnect();
        }
    }

    public boolean deleteFile(String remoteFile) {
        try {
            connect();
            openChannel();
            sftpChannel.rm(remoteFile);
            return true;
        } catch (JSchException | SftpException e) {
            throw new SlimFixtureException("Failed to delete: " + remoteFile, e);
        } finally {
            sftpChannel.exit();
            session.disconnect();
        }
    }

    public boolean deleteDirectory(String remoteDir) {
        try {
            connect();
            openChannel();
            sftpChannel.rmdir(remoteDir);
            return true;
        } catch (JSchException | SftpException e) {
            throw new SlimFixtureException("Failed to delete directory: " + remoteDir, e);
        } finally {
            sftpChannel.exit();
            session.disconnect();
        }
    }

    public boolean createDirectory(String remoteDir) {
        try {
            connect();
            openChannel();
            sftpChannel.mkdir(remoteDir);
            return true;
        } catch (JSchException | SftpException e) {
            throw new SlimFixtureException("Failed to create directory: " + remoteDir, e);
        } finally {
            sftpChannel.exit();
            session.disconnect();
        }
    }

    public List<String> listFiles(String remoteDir) {
        try {
            List<String> result = new ArrayList<>();
            connect();
            openChannel();
            Vector<ChannelSftp.LsEntry> list = sftpChannel.ls(remoteDir);
            for (ChannelSftp.LsEntry entry : list) {
                result.add(entry.getFilename());
            }
            return result;
        } catch (JSchException | SftpException e) {
            throw new SlimFixtureException("Failed to list contents of directory: " + remoteDir, e);
        } finally {
            sftpChannel.exit();
            session.disconnect();
        }
    }

    public String textInRemoteFile(String remoteFile) {
        try {
            connect();
            openChannel();
            InputStream stream = sftpChannel.get(remoteFile);
            ByteSource streamBytes = new ByteSource() {
                @Override
                public InputStream openStream() throws IOException {
                    return stream;
                }
            };
            return streamBytes.asCharSource(Charsets.UTF_8).read();
        } catch (JSchException | SftpException | IOException e) {
            throw new SlimFixtureException("Failed to list contents of file: " + remoteFile, e);
        } finally {
            sftpChannel.exit();
            session.disconnect();
        }
    }

    public String contentOfRemoteFile(String remoteFile) {
        String content = this.textInRemoteFile(remoteFile);
        return this.getEnvironment().getHtml(content);
    }

    public String pollUntilFileMatchingExistsIn(String filePattern, String directory) {
        if(repeatUntil(fileExistsInDirectoryCompletion(1, filePattern, directory))) {
            return findPatternInDirectory(filePattern, directory).first();
        }
        else {
            return null;
        }
    }

    public List<String> pollUntilFilesMatchingExistIn(int number, String filePattern, String directory) {
        if(repeatUntil(fileExistsInDirectoryCompletion(number, filePattern, directory))) {
            return new ArrayList<>(findPatternInDirectory(filePattern, directory));
        }
        else {
            return null;
        }
    }

    protected FunctionalCompletion fileExistsInDirectoryCompletion(int number, String filePattern, String directory) {
        return new FunctionalCompletion(() -> atLeastfilesExistInDirectory(number, filePattern, directory));
    }

    protected boolean atLeastfilesExistInDirectory(int number, String filePattern, String directory) {
        return findPatternInDirectory(filePattern, directory).size() >= number;
    }

    protected TreeSet<String> findPatternInDirectory(String filePattern, String directory) {
        List<String> ls = listFiles(directory);
        TreeSet<String> result = new TreeSet<>();
        for(String file : ls) {
            if (file.matches(filePattern)) {
                result.add(file);
            }
        }
        return result;
    }

    public boolean setPermissionsOfTo(String remoteFile, String chmodPermissions) {
        try {
            connect();
            openChannel();
            int decimalPermissions = Integer.parseInt(chmodPermissions, 8);
            sftpChannel.chmod(decimalPermissions, remoteFile);
            return true;
        } catch (JSchException | SftpException e) {
            throw new SlimFixtureException("Failed to set permissions of : " + remoteFile + " to " + chmodPermissions, e);
        } finally {
            sftpChannel.exit();
            session.disconnect();
        }
    }

    private void openChannel() throws JSchException {
        Channel channel = session.openChannel("sftp");
        channel.connect();
        sftpChannel = (ChannelSftp) channel;
    }

    private void connect() throws JSchException {
        if (null == session || !session.isConnected()) {
            session = jsch.getSession(username, host, port);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no"); //Convenient, but not very secure. Use only for testing
            session.setConfig(config);

            if (null != password) {
                session.setPassword(password);
            }
            session.connect();
        }
    }
}
