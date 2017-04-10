package com.wasu.ptyw.galaxycrm.web.utile;

import java.io.IOException;
import java.io.InputStream;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import com.mysql.jdbc.log.Log;
import com.wasu.ptyw.galaxycrm.web.controller.BaseController;



/**
 * @author wenguang
 * @date 2014��9��12��
 */
public class FastdfsUtil extends BaseController{
	static {
		try {
//			ClientGlobal.init("/root/fdfs_client.conf");
			ClientGlobal.init("C:/Users/kale/fdfs_client.conf");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Upload File to DFS.
	 * 
	 * @param fileBuff
	 *            , file to be uploaded.
	 * @param uploadFileName
	 *            , the name of the file.
	 * @param fileLength
	 *            , the length of the file.
	 * @return the file ID in DFS.
	 * @throws IOException
	 */
	public  String uploadFile(InputStream inStream, String uploadFileName, long fileLength) throws IOException {
		 byte[] fileBuff = getFileBuffer(inStream, fileLength);
		String fileId = "";
		String fileExtName = "";
		if (uploadFileName.contains(".")) {
			fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
		} else {
			return fileId;
		}
		// ��������
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		StorageClient1 client = new StorageClient1(trackerServer, storageServer);
		// ����Ԫ��Ϣ
		NameValuePair[] metaList = new NameValuePair[3];
		metaList[0] = new NameValuePair("fileName", uploadFileName);
		metaList[1] = new NameValuePair("fileExtName", fileExtName);
		metaList[2] = new NameValuePair("fileLength", String.valueOf(fileLength));

		// �ϴ��ļ�
		try {
			 fileId = client.upload_file1(fileBuff, fileExtName, metaList);
			 System.out.println(fileBuff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		trackerServer.close();
		return fileId;
	}
	
	
	/** 
     * Transfer java.io.InpuStream to byte array. 
     * @param inStream, input stream of the uploaded file. 
     * @param fileLength, the length of the file. 
     * @return the byte array transferred from java.io.Inputstream. 
     * @throws IOException occurred by the method read(byte[]) of java.io.InputStream. 
     */  
	private static byte[] getFileBuffer(InputStream inStream, long fileLength) throws IOException {  
          
        byte[] buffer = new byte[256 * 1024];  
        byte[] fileBuffer = new byte[(int) fileLength];  
      
        int count = 0;  
        int length = 0;  
      
        while((length = inStream.read(buffer)) != -1){  
            for (int i = 0; i < length; ++i)  
            {  
                fileBuffer[count + i] = buffer[i];  
            }  
            count += length;  
        }  
        return fileBuffer;  
    }  

	public static String uploadFile(String filePath) throws Exception {
		String fileId = "";
		String fileExtName = "";
		if (filePath.contains(".")) {
			fileExtName = filePath.substring(filePath.lastIndexOf(".") + 1);
		} else {
			return fileId;
		}

		// ��������
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		StorageClient1 client = new StorageClient1(trackerServer, storageServer);

		// �ϴ��ļ�
		try {
			fileId = client.upload_file1(filePath, fileExtName, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			trackerServer.close();
		}
		return fileId;
	}

	public static String uploadSlaveFile(String masterFileId, String prefixName, String slaveFilePath) throws Exception {
		String slaveFileId = "";
		String slaveFileExtName = "";
		if (slaveFilePath.contains(".")) {
			slaveFileExtName = slaveFilePath.substring(slaveFilePath.lastIndexOf(".") + 1);
		} else {
			return slaveFileId;
		}

		// ��������
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		StorageClient1 client = new StorageClient1(trackerServer, storageServer);

		// �ϴ��ļ�
		try {
			slaveFileId = client.upload_file1(masterFileId, prefixName, slaveFilePath, slaveFileExtName, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			trackerServer.close();
		}

		return slaveFileId;
	}

	public static int download(String fileId, String localFile) throws Exception {
		int result = 0;
		// ��������
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		StorageClient1 client = new StorageClient1(trackerServer, storageServer);

		// �����ļ�
		try {
			result = client.download_file1(fileId, localFile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			trackerServer.close();
		}

		return result;
	}

}
