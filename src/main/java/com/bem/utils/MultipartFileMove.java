package com.bem.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class MultipartFileMove {
	//文件存储地址
	private final String FILE_ADDRESS = Class.class.getClass().getResource("/").getPath() + "static/images/";
	//文件大小
	private long FILE_SIZE_MAX =3 * 1024 *1024 ;
	//错误提示
	private String FILE_ERROR ="";
	
	
	public final String file_move(MultipartFile file){
		file_address_exist();
		String mFileName = new Date().getTime() + file.getOriginalFilename();	// 文件名
		File mImageFile = new File(FILE_ADDRESS+mFileName);
		file_size_decide(file.getSize());
		if(FILE_ERROR==null){
			try {
				file.transferTo(mImageFile);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.err.println(FILE_ERROR);
			return "false";
		}
		return "true";
	}
	
	private final void file_address_exist(){
		File file = new File(FILE_ADDRESS);
		if (!file.isDirectory()) {
			file.mkdir();
		}
	}
	
	private final void file_size_decide(long size){
		if(size>FILE_SIZE_MAX){FILE_ERROR="file size better big! ";}
	}
	
}
