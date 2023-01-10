package com.blogApp.services;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogApp.exceptions.ImageExtensionException;

@Service
public interface FileService {

	String uploadImage(String path, MultipartFile file) throws IOException, ImageExtensionException;
	InputStream getResource(String path, String fileName) throws FileNotFoundException;
}
