package com.bem.web;


import java.util.List;

import javax.annotation.Resource;
import javax.xml.soap.Detail;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bem.domain.Review;
import com.bem.domain.Task;
import com.bem.domain.User;
import com.bem.service.ReviewService;
import com.bem.service.TaskService;

@Controller
public class TaskController extends BaseController{
	@Resource
	private ReviewService reviewService;
	
	@Resource
	private TaskService taskService;
	
	
//	@RequestMapping("/task_detail/{id}")
//	public String index(Model model,@PathVariable String id){
//		Task task= taskService.findById(id);
//		List<Review> list = reviewService.findAll(id);
//		User user = getCurrentUser();
//		model.addAttribute("user",user);
//		model.addAttribute("list",list);
//		model.addAttribute("task",task);
//		return "detail";
//	}

	@RequestMapping("/task_detail/{id}")
	public String index(Model model,@PathVariable String id){
		Task task= taskService.findById(id);
		model.addAttribute("task",task);
		return "detail";
	}
	
	/*@ResponseBody
	@PostMapping("/review/remove/{id}")
	public String delete(@PathVariable String id){
		reviewService.delete(id);
		return "true";
	}
	
	
	@ResponseBody
	@PostMapping("/review/add")
	public String add(Review review){
		review.setComment_id(getCurrentUser().getId());
		review.setUser_name(getCurrentUsername());
		reviewService.add(review);
		return "true";
	}*/
	
	
	
}
