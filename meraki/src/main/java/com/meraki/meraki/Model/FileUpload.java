package com.meraki.meraki.Model;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.file.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

public class FileUpload {

    private String filePath;
    private String name;

    public FileUpload(){}

    public String getFilePath() {
        return filePath;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public static final String storageConnectionString =
            "DefaultEndpointsProtocol=https;" +
                    "AccountName=personimagedata;" +
                    "AccountKey=RNlmOs2+a0l0Vb2Ogxi3fSMA/LKsHgutsgvQE6p/Nu5OrXhmma8XNfnT3qFobkpGqYTecz75MXrmkoTThScJ+w==";

    public String uploadPic ( FileUpload file ) {
        String done = "0";
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            CloudFileClient fileClient = storageAccount.createCloudFileClient();
            CloudFileShare share = fileClient.getShareReference("myshare");
            CloudFileDirectory rootDir = share.getRootDirectoryReference();
            final String filePath2 = file.getFilePath();
            CloudFile cloudFile = rootDir.getFileReference(file.getName());
            cloudFile.uploadFromFile(filePath2);
            done = "1";
        } catch (InvalidKeyException invalidKey) {
            done = invalidKey.toString();
        } catch (StorageException e) {
            done = e.toString();
        } catch (URISyntaxException e) {
            done = e.toString();
        } catch (IOException e) {
            done = e.toString();
        }
        return  done;
    };


}
