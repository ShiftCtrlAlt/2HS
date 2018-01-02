package com.bem.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.bem.domain.LocalAuth;
import com.bem.domain.UserRegDto;
import com.bem.service.UserService;



@Controller
public class LoginController { 
	@Resource
	private UserService userService;
	@Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/login")
	public String login(){
		return "login";
	}
	
	@ResponseBody
	@RequestMapping("/registration")
	public Map<String, Object> createNewUser(@Valid UserRegDto user) {
		Map<String, Object> map = new HashMap<String, Object>();
		LocalAuth localAuth = userService.findUserByAccount(user.getAccountid());
		if (localAuth != null) {
			map.put("data", "userExists");
			return map;
		}
		System.err.println("--------------------------error");
		userService.saveUser(user);
		map.put("data", "success");
		return map;
	}
}
