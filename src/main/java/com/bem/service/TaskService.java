package com.bem.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bem.domain.Classify;
import com.bem.domain.Task;
import com.bem.domain.TaskDto;
import com.bem.domain.User;
import com.bem.repository.ClassifyRepository;
import com.bem.repository.TaskRepository;
import com.bem.utils.MultipartFileMove;


@Component
public class TaskService {
	
	@Resource
	private TaskRepository taskRepository;
	
	@Resource
	private MultipartFileMove file_move;
	
	@Resource
	private ClassifyRepository classifyRepository;

	public void save(MultipartFile file,ArrayList<String> list) {
		String mFileName = new Date().getTime() + file.getOriginalFilename();	// 文件名
		System.out.println(mFileName);// 储存缓存文件地址
		String Path =Class.class.getClass().getResource("/").getPath() + "static/images/user/";;
		File mfile = new File(Path);// 创建文件夹
		if (!mfile.isDirectory()) {
			mfile.mkdir();
		}
		String imageFilePath = Path + mFileName;// 移动文件夹到指定目录
		File mImageFile = new File(imageFilePath);
		try {
			file.transferTo(mImageFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list.add(mFileName);
	}
	public void save(MultipartHttpServletRequest request,TaskDto taskDto,User user){
		Task task = new Task();
		ArrayList<String> list = new ArrayList();
		BeanUtils.copyProperties(taskDto,task);
		for (MultipartFile file : request.getFileMap().values()) {
			save(file,list);
		}
		task.setClassify(classifyRepository.findOneById(taskDto.getClassify()));
		task.setList_img(list);
		task.setUser_id(user.getId());
		taskRepository.save(task);
	}
	
	
	
	public List<Task> findAll(){
		return taskRepository.findAll();
	}
	
	public Task findById(String id){
		return taskRepository.findId(id);
	}
	
	public boolean delete(String id){
		return taskRepository.delete(id);
	}
	public void update(TaskDto taskCreateForm,String id,User user){
		Task task  = taskRepository.findId(id);
		BeanUtils.copyProperties(taskCreateForm, task, Task.class);
		task.setUser_id(user.getId());
		taskRepository.update(task);
	}
}
