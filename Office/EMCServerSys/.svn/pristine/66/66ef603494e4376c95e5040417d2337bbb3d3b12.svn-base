/*
 * To change ftpClient template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;

/**
 *
 * @author wikus
 */
public class EMCFTClient {

    private boolean useFTP;
    //private EMCFTPClient ftpClient;
    public static int BUFFER_SIZE = 10240;
    private File currentFile;

    public EMCFTClient(String host, String userName, String password) throws IOException {
       // ftpClient = new EMCFTPClient(host);
      //  ftpClient.login(userName, password);
       // ftpClient.binary();
       // useFTP = true;
    }

    public EMCFTClient(String rootDirectoryPath) throws IOException {
        currentFile = new File(rootDirectoryPath);
        if (!currentFile.exists()) {
            throw new IOException("Root File Directory does not exist: " + rootDirectoryPath);
        }
        useFTP = false;
    }

   /* public void disconnect() {
        if (useFTP) {
            if (ftpClient != null) {
                try {
                    ftpClient.closeServer();
                } catch (IOException ex) {
                }
            }
        } else {
            currentFile = null;
        }
    }

    public void changeDirectory(String directory) throws IOException {
        if (useFTP) {
            ftpClient.cd(directory);
            System.out.println("Directory: " + directory);
        } else {
            File oldCurrent = new File(currentFile.getPath());
            currentFile = new File(currentFile.getPath() + directory);
            if (!currentFile.exists()) {
                currentFile = oldCurrent;
                throw new IOException("Directory not found: " + currentFile.getPath());
            }
        }
    }

    public List<String> listFiles() throws IOException {
        if (useFTP) {
            TelnetInputStream inputStream = ftpClient.list();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String lineRead;
            StringTokenizer tokenizer;
            String token;
            int tokenIndex;
            StringBuilder fileName;
            List<String> fileList = new ArrayList<String>();

            while ((lineRead = bufferedReader.readLine()) != null) {
                System.out.println(lineRead);
                tokenizer = new StringTokenizer(lineRead);
                tokenIndex = 0;
                fileName = new StringBuilder();
                while (tokenizer.hasMoreTokens()) {
                    token = tokenizer.nextToken();
                    if (tokenIndex >= 8) {
                        if (tokenIndex > 8) {
                            fileName.append(" ");
                        }
                        fileName.append(token);
                    }
                    tokenIndex++;
                }
                fileList.add(fileName.toString());
            }

            bufferedReader.close();
            reader.close();
            inputStream.close();

            return fileList;
        } else {
            String[] files = currentFile.list();
            List<String> fileList = Arrays.asList(files);
            return fileList;
        }
    }

    public void getFile(String remoteFileName, File localFile) throws IOException {
        if (useFTP) {
            byte[] buffer = new byte[BUFFER_SIZE];
            FileOutputStream out = new FileOutputStream(localFile);
            InputStream in = ftpClient.get(remoteFileName);
            while (true) {
                int bytes = in.read(buffer);
                if (bytes < 0) {
                    break;
                }

                out.write(buffer, 0, bytes);
            }
            out.close();
            in.close();
        } else {
            FileInputStream in = new FileInputStream(currentFile.getPath() + File.separator + remoteFileName);
            FileOutputStream out = new FileOutputStream(localFile);
            byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                int bytes = in.read(buffer);
                if (bytes < 0) {
                    break;
                }

                out.write(buffer, 0, bytes);
            }
            out.close();
            in.close();
        }
    }

    public void putFile(File localFile, String remoteFileName) throws FileNotFoundException, IOException {
        if (useFTP) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int size = (int) localFile.length();
            System.out.println("File " + localFile + ": " + size + " bytes");
            FileInputStream in = new FileInputStream(localFile);
            OutputStream out = ftpClient.put(remoteFileName);

            int counter = 0;
            while (true) {
                int bytes = in.read(buffer);
                if (bytes < 0) {
                    break;
                }
                out.write(buffer, 0, bytes);
                counter += bytes;
                System.out.println(counter + " of " + size);
            }

            out.close();
            in.close();
        } else {
            FileInputStream in = new FileInputStream(localFile);
            FileOutputStream out = new FileOutputStream(currentFile.getPath() + File.separator + remoteFileName);
            byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                int bytes = in.read(buffer);
                if (bytes < 0) {
                    break;
                }

                out.write(buffer, 0, bytes);
            }
            out.close();
            in.close();
        }
    }

    public void makeDirectory(String directoryName) throws IOException {
        if (useFTP) {
            ftpClient.executeCommand("mkdir ./" + directoryName);
        } else {
            File theFile = new File(currentFile.getPath() + File.separator + directoryName + File.separator);
            if (!theFile.exists()) {
                theFile.mkdir();
            }
        }
    }

    public void deleteFile(String fileName) throws IOException {
        if (useFTP) {
            ftpClient.executeCommand("delete ./" + fileName);
        } else {
            File theFile = new File(currentFile.getPath() + File.separator + fileName);
            if (theFile.exists()) {
                theFile.delete();
            }
        }
    }*/
}

/*class EMCFTPClient extends FtpClient {

    public EMCFTPClient(String host) throws IOException {
        super(host);
    }

    public void executeCommand(String command) throws IOException {
        this.issueCommandCheck(command);
    }
}*/
