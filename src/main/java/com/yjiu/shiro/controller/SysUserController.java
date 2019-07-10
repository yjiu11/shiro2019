package com.yjiu.shiro.controller;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yjiu.shiro.pojo.SysRole;
import com.yjiu.shiro.pojo.SysUser;
import com.yjiu.shiro.service.SysRoleService;
import com.yjiu.shiro.service.SysUserService;
import com.yjiu.shiro.tools.Kit;
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
	private SysUserService thisService;
	@Autowired
	private SysRoleService roleService;
	@RequestMapping("/page/sysuser/list")
	public String toPage_ProjectList() {
		return "sysuser/list";
	}
	@RequestMapping("/page/sysuser/add")
	public String toPage_ProjectAdd(@RequestParam(value="id",required=false)String id,Model model) {
		if(StringUtils.isNotBlank(id)) {
			SysUser pro = thisService.selectOne(new EntityWrapper<SysUser>().eq("id", id));
			model.addAttribute("pro", pro);		 //如果是update，传过去
		}
		List<SysRole> lists = roleService.selectList(new EntityWrapper<SysRole>().eq("available", "1"));//查找所有可用角色
	    model.addAttribute("roles",lists);
		return "sysuser/add";
	}
	@RequestMapping("/sysuser/list/data")
	@ResponseBody
	public PTWResult data_ProjectList(@RequestParam(value="page",required=false,defaultValue="1") Integer page,
			@RequestParam(value="limit",required=false,defaultValue="10") Integer limit,
			@RequestParam(value="field",required=false,defaultValue="id") String field,
			@RequestParam(value="order",required=false,defaultValue="asc") String order,
			String username,String realname,String email,String phone) {
		Map<String,String> map = new HashMap<>();
		map.put("username",username);map.put("realname",realname);map.put("email",email);map.put("phone",phone);
		return thisService.selectByPage(new Page<>(page,limit),field,order,map);
	}
	
	/***
	 * 保存
	 * @param project
	 * @return
	 */
	@RequestMapping("/sysuser/save")
	@ResponseBody
	public PTWResult save(SysUser project) {
		try {
			System.out.println(project);
			String pass = Kit.generatePass(project);
			project.setPassword(pass);
			thisService.insert(project);
			return PTWResult.ok();
		}catch(Exception e) {
			e.printStackTrace();
			return PTWResult.build(500, e.getMessage().toString());
		}
	}
	
	@RequestMapping("/sysuser/update")
	@ResponseBody
	public PTWResult update(SysUser project) {
		try {
			SysUser one = thisService.selectOne(new EntityWrapper<SysUser>().eq("id", project.getId()));
			project.setPassword(one.getPassword());
			thisService.update(project, new EntityWrapper<SysUser>().eq("id", project.getId()));
			return PTWResult.ok();
		}catch(Exception e) {
			return PTWResult.build(500, e.getMessage().toString());
		}
	}
	
	@RequestMapping("/sysuser/batchDel")
	@ResponseBody
	public PTWResult batchDel(String ids) {
		try {
			if(ids.contains(",")) {
				String[] idss = ids.split(",");
				thisService.deleteBatchIds(Arrays.asList(idss));
			}else {
				thisService.deleteById(Integer.parseInt(ids));
			}
			return PTWResult.ok();
		}catch(Exception e) {
			e.printStackTrace();
			return PTWResult.build(500, e.getMessage().toString());
		}
	}
	@RequestMapping("/sysuser/get")
	@ResponseBody
	public PTWResult get(String id) {
		SysUser one = thisService.selectOne(new EntityWrapper<SysUser>().eq("id", id));
		return PTWResult.ok(one);
	}
}

