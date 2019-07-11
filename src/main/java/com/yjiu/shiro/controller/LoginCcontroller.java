package com.yjiu.shiro.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yjiu.shiro.pojo.SysUser;
import com.yjiu.shiro.tools.PTWResult;

@Controller
public class LoginCcontroller {
	/*@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginPage() {
		return "redirect:/login";
	}*/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginPage() {
		return "redirect:/login";
	}
	@RequestMapping(value = "/unauth", method = RequestMethod.GET)
	public String unauth() {
		return "unauth";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "index2";
	}
	/*@RequestMapping(value = "unauth", method = RequestMethod.GET)
	@ResponseBody
	public PTWResult unauth() {
		return PTWResult.unauth();
	}*/
/*	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}*/
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public PTWResult loginUp(SysUser user, HttpServletRequest req,
			HttpServletResponse resp) {
		String error = null;
		System.out.println(user);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(
				user.getUsername(), user.getPassword());
		try {
			subject.login(token);
			req.getSession().setAttribute("user", user);
			SecurityUtils.getSubject().getSession();
		} catch (UnknownAccountException e) {
			error = "用户名/密码错误";
		} catch (IncorrectCredentialsException e) {
			error = "用户名/密码错误";
		} catch (AuthenticationException e) {
			// 其他错误，比如锁定，如果想单独处理请单独 catch 处理
			error = "其他错误：" + e.getMessage();
			e.printStackTrace();
		}
		System.out.println("error:" + error);
		if (error != null) {// 出错了，返回登录页面
			req.setAttribute("error", error);
			return PTWResult.build(500, error);
		} else {// 登录成功
			return PTWResult.ok();
		}
	}
}