package com.yjiu.shiro.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yjiu123
 * @since 2019-03-30
 */
@Controller
public class SysUserController {
	@RequiresPermissions("user:view")
	@RequestMapping("/user/list")
	public String pageList() {
		return "user/list";
	}
	
	@RequestMapping("/user/list2")
	public String pageList2() {
		return "user/list";
	}
}

