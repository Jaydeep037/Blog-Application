package com.blogApp.services.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogApp.config.AppConstants;
import com.blogApp.exceptions.ImageExtensionException;
import com.blogApp.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException, ImageExtensionException {
		
//		Get original file name
		String name = file.getOriginalFilename();
		String extentsion = name.substring(name.lastIndexOf("."));
		if(".jpg".equals(extentsion)) {
			 return AppConstants.INVALID_IMAGE_FORMAT;
		}
//		Generate random file name
		
		String randomID = UUID.randomUUID().toString();
		
		String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));
		
//		 full path
		String filePath = path + File.separator + fileName;
		
//		Create folder if not created
		File f = new File(filePath);
		if(!f.exists()) {
			f.mkdir();
		}
		
//		File copy
		
		Files.copy(file.getInputStream(), Paths.get(fileName));
		
		
		return fileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath= path + File.separator + fileName;
		
		InputStream is = new FileInputStream(fullPath);
		
		return is;
	}

}
