package com.taotao.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.service.PictureService;
import com.taotao.utils.FtpUtil;
import com.taotao.utils.IDUtils;

@Service
public class PictureServiceImpl implements PictureService {
	
	//读取配置文件里的内容
	@Value("${FTP_HOST}")
	private String FTP_HOST;
	
	@Value("${FTP_PORT}")
	private int FTP_PORT;
	
	@Value("${FTP_USER_NAME}")
	private String FTP_USER_NAME;
	
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	
	@Value("${IMAGE_URL}")
	private String IMAGE_URL;

	@Override
	public Map<String, Object> pictureUpload(MultipartFile uploadFile) {
		//准备返回的Map
		Map<String, Object> resultMap = new HashMap<>();
		//重新生成图片名称
		String originalFileName = uploadFile.getOriginalFilename();
		
		String newFileName = IDUtils.genImageName(); 
		//拼接新的图片名称
		newFileName = newFileName + originalFileName.substring(originalFileName.lastIndexOf("."));
		
		DateTime dateTime = new DateTime();
		String filePath = dateTime.toString("/yyyy/MM/dd");
		
		InputStream inputStream = null;
		try {
			inputStream = uploadFile.getInputStream();
			boolean uploadResult = FtpUtil.uploadFile(FTP_HOST, FTP_PORT, FTP_USER_NAME, FTP_PASSWORD, FTP_BASE_PATH, filePath, newFileName, inputStream);
			if(uploadResult) {
				resultMap.put("error", 0); //成功返回0
				resultMap.put("url", IMAGE_URL + filePath + "/" + newFileName);
			}else {
				resultMap.put("error", 1); //失败返回1
				resultMap.put("message", "文件上传失败！");
			}
			
			return resultMap;
			
		} catch (IOException e) {
			resultMap.put("error", 1); //失败返回1
			resultMap.put("message", "文件上传发生异常！");
			e.printStackTrace();
			return resultMap;
		}
	}

}
