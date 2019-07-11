package com.yjiu.shiro.service;

import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yjiu.shiro.pojo.SysResource;
import com.yjiu.shiro.pojo.SysRole;
import com.yjiu.shiro.pojo.SysUser;
import com.yjiu.shiro.tools.PTWResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjiu123
 * @since 2019-03-30
 */
public interface SysUserService extends IService<SysUser> {
	//登录
	public SysUser login(SysUser user);
	//只有角色的名称
	public Set<String> findRoleStringById(long user_id);
	//有角色所有的信息
	public Set<SysRole> findRolesById(long user_id);
	//只有资源的名称
	public Set<String> findResourceStringById(long user_id);
	//有资源所有信息
	public Set<SysResource> findResourcesById(long user_id);
	//分页+排序+搜索
	PTWResult selectByPage(Page<SysUser> page,String field,String order, Map<String,String> searchFields);
	/**
	 * @return
	 * 获取所有的菜单树
	 */
	public PTWResult getMenu();
	/**
	 * @param password
	 * @return
	 * 更改用户密码
	 */
	public PTWResult updatepwd(String password);
}
