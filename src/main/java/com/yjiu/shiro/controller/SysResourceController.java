package com.yjiu.shiro.controller;


import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yjiu.shiro.pojo.SysResource;
import com.yjiu.shiro.pojo.SysRole;
import com.yjiu.shiro.service.SysResourceService;
import com.yjiu.shiro.service.SysRoleService;
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
public class SysResourceController {
	@Autowired
	private SysResourceService thisService;
	@Autowired
	private SysRoleService roleService;
	@RequestMapping("/page/sysresource/list")
	public String toPage_ProjectList() {
		return "sysresource/list";
	}
	@RequestMapping("/page/sysresource/add")
	public String toPage_ProjectAdd(@RequestParam(value="id",required=false)String id,Model model) {
		if(StringUtils.isNotBlank(id)) {
			SysResource pro = thisService.selectOne(new EntityWrapper<SysResource>().eq("id", id));
			model.addAttribute("pro", pro);		 //如果是update，传过去
		}
		return "sysresource/add";
	}
	@RequestMapping("/sysresource/list/data")
	@ResponseBody
	public PTWResult data_ProjectList(@RequestParam(value="page",required=false,defaultValue="1") Integer page,
			@RequestParam(value="limit",required=false,defaultValue="10") Integer limit,
			@RequestParam(value="field",required=false,defaultValue="id") String field,
			@RequestParam(value="order",required=false,defaultValue="asc") String order,
			String name) {
		Map<String,String> map = new HashMap<>();
		map.put("name",name);
		return thisService.selectByPage(new Page<>(page,limit),field,order,map);
	}
	
	/***
	 * 保存
	 * @param project
	 * @return
	 */
	@RequestMapping("/sysresource/save")
	@ResponseBody
	public PTWResult save(SysResource project) {
		try {
			System.out.println(project);
			thisService.insert(project);
			return PTWResult.ok();
		}catch(Exception e) {
			e.printStackTrace();
			return PTWResult.build(500, e.getMessage().toString());
		}
	}
	
	@RequestMapping("/sysresource/update")
	@ResponseBody
	public PTWResult update(SysResource project) {
		try {
			thisService.update(project, new EntityWrapper<SysResource>().eq("id", project.getId()));
			return PTWResult.ok();
		}catch(Exception e) {
			return PTWResult.build(500, e.getMessage().toString());
		}
	}
	
	@RequestMapping("/sysresource/batchDel")
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
	@RequestMapping("/sysresource/get")
	@ResponseBody
	public PTWResult get(String id) {
		SysResource one = thisService.selectOne(new EntityWrapper<SysResource>().eq("id", id));
		return PTWResult.ok(one);
	}
	@RequestMapping("/sysresource/tree")
	@ResponseBody
	public PTWResult tree() {
		List<SysResource> one = thisService.selectList(new EntityWrapper<SysResource>().eq("available", 1));
		List<Object> result = this.getChildren(0,one);
		return PTWResult.ok(result);
	}
	@RequestMapping("/sysresource/tree/filter")
	@ResponseBody
	public PTWResult filter(String role) {
		List<Integer> result = new ArrayList<>();
		SysRole ro = roleService.selectOne(new EntityWrapper<SysRole>().eq("id", role));
		String[] arr = StringUtils.splitByWholeSeparator(ro.getResourceIds(), ",");
		for (String key : arr) {
			List<SysResource> lists = thisService.selectList(new EntityWrapper<SysResource>().eq("parent_id", key));
			if(lists !=null && lists.size()>0) {//有子节点
			}else {//无子节点
				result.add(Integer.parseInt(key));				
			}
		}
		return PTWResult.ok(result);
	}
	public List<Object> getChildren(Integer parentId,List<SysResource> depts){
        List<Object> list = new ArrayList<>();
            for (SysResource d : depts) {
                if(d.getParentId().equals(parentId)){
                    boolean flag;
                    if(StringUtils.equals(d.getAvailable(),"1")){
                        flag=false;
                    }else{
                        flag=true;
                    }
                JSONObject obj = new JSONObject();
                obj.put("id", d.getId());
                obj.put("title", d.getName());
                obj.put("disabled", flag);
                obj.put("spread", d.getOpen()!=null && d.getOpen()==1?true:false);
                obj.put("children", getChildren(d.getId(),depts));
                list.add(obj);
            }
        }
        return list;
    }
	public static void main(String[] args) {
		String[] arr = StringUtils.splitByWholeSeparator("1", ",");
		for (String string : arr) {
			System.out.println(string);
		}
	}
}

