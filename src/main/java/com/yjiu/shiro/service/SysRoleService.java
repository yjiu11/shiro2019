package com.yjiu.shiro.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yjiu.shiro.pojo.SysRole;
import com.yjiu.shiro.tools.PTWResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjiu123
 * @since 2019-03-30
 */
public interface SysRoleService extends IService<SysRole> {
	//分页+排序+搜索
	PTWResult selectByPage(Page<SysRole> page,String field,String order, Map<String,String> searchFields);
}
