package com.nash.blog.services.impl;

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

import com.nash.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile multipartFile) throws IOException {
		
		String name = multipartFile.getOriginalFilename();
		
		String randomId = UUID.randomUUID().toString();
		String fileName = randomId.concat(name.substring(name.lastIndexOf('.')));
		
		String filePath = path + File.separator + fileName;
		
		File file = new File(path);
		if(!file.exists()) {
			file.mkdir();
		}
		
		Files.copy(multipartFile.getInputStream(), Paths.get(filePath));
		
		return fileName;
	}

	@Override
	public InputStream getResourec(String path, String fileName) throws FileNotFoundException {
		
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		
		return is;
	}

}
