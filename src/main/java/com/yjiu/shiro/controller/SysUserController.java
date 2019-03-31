package com.yjiu.shiro.controller;


import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yjiu.shiro.pojo.SysUser;
import com.yjiu.shiro.service.SysUserService;
import com.yjiu.shiro.tools.PTWResult;

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
	@Autowired
	private SysUserService userService;
	@RequiresPermissions("user:view")
	@RequestMapping("/user/list")
	public String pageList() {
		return "user/list";
	}
	
	@RequestMapping("/page/user/add")
	public String toPage_ProjectAdd(@RequestParam(value="id",required=false)String id,Model model) {
		if(StringUtils.isNotBlank(id)) {
			SysUser pro = userService.selectOne(new EntityWrapper<SysUser>().eq("id", id));
			model.addAttribute("pro", pro);		 //如果是update，传过去
		}
		return "user/edit";
	}
	
	@RequestMapping("/user/list/data")
	@ResponseBody
	public PTWResult data(@RequestParam(value="page",required=false,defaultValue="1") Integer page,
			@RequestParam(value="limit",required=false,defaultValue="10") Integer limit,
			@RequestParam(value="field",required=false,defaultValue="id") String field,
			@RequestParam(value="order",required=false,defaultValue="asc") String order,
			String realname,String username) {
		PTWResult result = userService.selectByPage(new Page<>(page,limit),field,order,realname,username);
		return result;
	}
	
	@RequestMapping("/user/batchDel")
	@ResponseBody
	public PTWResult batchDel(String ids) {
		try {
			if(ids.contains(",")) {
				String[] idss = ids.split(",");
				userService.deleteBatchIds(Arrays.asList(idss));
			}else {
				userService.deleteById(Integer.parseInt(ids));
			}
			return PTWResult.ok();
		}catch(Exception e) {
			e.printStackTrace();
			return PTWResult.build(500, e.getMessage().toString());
		}
	}
}

