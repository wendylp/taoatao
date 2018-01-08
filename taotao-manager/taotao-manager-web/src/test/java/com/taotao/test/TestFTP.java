package com.taotao.test;


import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.utils.FtpUtil;

class TestFTP {

	@Test
	void testFTP() throws Exception {
		//创建FTP对象
		FTPClient ftpClient = new FTPClient();
		//创建连接
		ftpClient.connect("192.168.112.130", 21);
		//ftp用户名密码
		ftpClient.login("ftpuser", "ftpuser");
		
		FileInputStream inputStream = new FileInputStream(new File("F:\\pictures\\test04.jpg"));
		
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		//设置上传的文件路径
		ftpClient.changeWorkingDirectory("/home/ftpuser");
		ftpClient.storeFile("321.jpg", inputStream);
		
		inputStream.close();
		
		ftpClient.logout();
		
	}
	
	
	@Test
	public void testFtpUtils() throws Exception{
		
		FileInputStream inputStream = new FileInputStream(new File("F:\\pictures\\test04.jpg"));
		
		FtpUtil.uploadFile("192.168.112.130", 21, "ftpuser", "ftpuser", "/home/ftpuser", "2017/12/04", "111.jpg", inputStream);
		
	}
	
	

	
}
