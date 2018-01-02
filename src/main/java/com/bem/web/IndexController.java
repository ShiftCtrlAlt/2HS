package com.bem.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.UploadContext;
import org.codehaus.groovy.util.ReleaseInfo;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.bem.domain.Classify;
import com.bem.domain.QuizDto;
import com.bem.domain.QuizDtoWrapper;
import com.bem.domain.Task;
import com.bem.domain.TaskDto;
import com.bem.domain.User;
import com.bem.domain.User.ROLE;
import com.bem.repository.TaskRepository;
import com.bem.service.ClassifyService;
import com.bem.service.QuizService;
import com.bem.service.TaskService;
import com.bem.service.UserService;
import com.bem.utils.MyPage;

@Controller
public class IndexController extends BaseController{

	@Resource
	private TaskService taskService;
	@Resource
	private TaskRepository taskRepository;
	@Resource
	private QuizService quizService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private ClassifyService classifyService;
	
	@RequestMapping("/")
	public String index(Model model){
		User user = getCurrentUser();
		MyPage<Task> Mypage = taskRepository.findByPageTask();
		List<Classify> clist = classifyService.findAllClassify();
		model.addAttribute("Mypage", Mypage);
		model.addAttribute("clist", clist);
		return "index2";
	}
	
	
	@GetMapping("/release")
	public String Release(Model model){
		List<Classify> mlist = classifyService.findAllClassify();
		model.addAttribute("mlist",mlist);
		return "release";
	}
	
	
	@GetMapping("/403")
    public String forbidden(){
        return "403";
    }
	
	@GetMapping("/newPage")
	public String newpage(){
		return "new";
	}
	


	
	@PostMapping("/upload")
	public String add(MultipartHttpServletRequest request,TaskDto taskDto) {
		taskService.save(request,taskDto,getCurrentUser());
		return "redirect:/";
	}
	

	
	@GetMapping("/deleteTask/{id}")
	public String deleteTask(@PathVariable String id){
		taskService.delete(id);
		return "index";
	}
	@PostMapping("/update/{id}")
	public String update(TaskDto taskCreateForm,@PathVariable String id) {
		taskService.update(taskCreateForm, id,getCurrentUser());
		return "index";
	}
	
	@PostMapping("/checkAnswer")
	@ResponseBody
	public String checkAnswer(@RequestBody QuizDtoWrapper quizDto){
		String answers = quizDto.getan();
		String[] answer = answers.split("\\|");
		Map status = new HashMap();
		for(QuizDto attribute : quizDto.getQuizDto()) {
			  System.out.println(attribute.getQuestion());
		}
		if (quizService.checkAnswers(answer,quizDto)){
			User user = getCurrentUser();
			user.setRole(ROLE.USER);
			userService.updateUser(user);
			List<SimpleGrantedAuthority> authList=new ArrayList<SimpleGrantedAuthority>();
			authList.add(new SimpleGrantedAuthority("USER"));
			SecurityContext context=SecurityContextHolder.getContext();
			UserDetails userDetails=(UserDetails) context.getAuthentication().getPrincipal();
			Authentication auth=new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),authList);
			context.setAuthentication(auth); 
			status.put("status", "success");
			
		}else{
			status.put("status", "fail");
		}
		String json = JSON.toJSONString(status);  
		return json;
	}
	
	
	//认证问题
	@PostMapping("/getQuizs")
	@ResponseBody
	public String getQuizs(){
		List<QuizDto> quizs = quizService.getRandom();
		String jsonQuizs = JSON.toJSONString(quizs);
		return jsonQuizs;
	}
}
