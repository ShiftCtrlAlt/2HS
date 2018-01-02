package com.bem.service;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bem.domain.LocalAuth;
import com.bem.domain.User;
import com.bem.domain.UserDto;
import com.bem.domain.UserRegDto;
import com.bem.domain.UserTwoDto;
import com.bem.repository.UserRepository;
import com.bem.utils.Constant;



@Service("userService")
public class UserService {
	@Resource
	private UserRepository userRepository;
	@Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	public LocalAuth findUserByAccount(String account) {
		return userRepository.findUserByAccount(account);
	}
	
	public void saveUser(UserRegDto Ruser) {
		User user = new User(Ruser.getEmail(), Constant.UN_AUTH_STATUS);
		userRepository.SaveUser(user);
		LocalAuth localAuth = new LocalAuth(Ruser.getAccountid(), Ruser.getEmail(), bCryptPasswordEncoder.encode(Ruser.getSetpw()), user);
		userRepository.SaveLocalAuth(localAuth);
	}
	
	public void updateUser(User user){
		userRepository.UpdateUser(user);
	}
	public void update(UserDto userDto,User user){
		BeanUtils.copyProperties(userDto, user,User.class);
		userRepository.UpdateUser(user);
	}
	public void update(UserTwoDto userDto,User user){
		BeanUtils.copyProperties(userDto, user,User.class);
		userRepository.UpdateUser(user);
	}
}
