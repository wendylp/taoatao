package com.taotao.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @ClassName: PictureService
 * @Description: 图片上传服务
 * @author LiuPeng
 * @date 2017年12月4日 上午11:35:57
 *
 */
public interface PictureService {
	
	Map<String, Object> pictureUpload(MultipartFile uploadFile);

}
