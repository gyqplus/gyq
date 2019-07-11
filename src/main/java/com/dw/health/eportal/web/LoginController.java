package com.dw.health.eportal.web;

import com.dw.health.framework.web.Result;
import com.dw.health.eportal.dao.UserMapper;
import com.dw.health.eportal.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**   
 * @ClassName:  LoginController   
 * @Description:登录控制层
 * @author: lyx 
 * @date:   2019年2月26日 上午10:28:08   
 */
@Controller
public class LoginController {
	private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserMapper userMapper;

	/**
	 * @Title: loginPage   
	 * @Description: GET方式跳转登录页面   
	 * @return String      
	 * @throws   
	 */
	@GetMapping("/loginPage")
	public String loginPage(){
		return "login";
	}
	/**   
	 * @Title: login   
	 * @Description: 登录验证，成功跳转主页  
	 * @param user
	 * @return String      
	 * @throws   
	 */
	@PostMapping("/login")
	@ResponseBody
	public Result<Object> login(User user) {
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
		String userId = (String) subject.getPrincipal();
		
		Session session = subject.getSession();
		
		session.setAttribute("currentUser",userMapper.selectByPrimaryKey(userId));
		
		return new Result<Object>(null);
	}	
	
	
}
