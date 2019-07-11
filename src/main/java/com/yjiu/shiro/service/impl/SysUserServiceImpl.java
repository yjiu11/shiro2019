package com.yjiu.shiro.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yjiu.shiro.mapper.SysUserMapper;
import com.yjiu.shiro.pojo.SysResource;
import com.yjiu.shiro.pojo.SysRole;
import com.yjiu.shiro.pojo.SysUser;
import com.yjiu.shiro.service.SysResourceService;
import com.yjiu.shiro.service.SysRoleService;
import com.yjiu.shiro.service.SysUserService;
import com.yjiu.shiro.tools.Kit;
import com.yjiu.shiro.tools.PTWResult;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjiu123
 * @since 2019-03-30
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
	@Autowired
	private SysRoleService roleService;
	@Autowired
	private SysResourceService resService;
	@Override
	public SysUser login(SysUser user) {
		SysUser db_user = this.selectOne(new EntityWrapper<SysUser>().eq("username", user.getUsername()));
		if(db_user == null){
			throw new UnknownAccountException("用户名或密码错误");
		}else{
			String db_pass = db_user.getPassword();
			if(db_pass.equals(Kit.generatePass(user))){
				return db_user;
			}else{
				throw new IncorrectCredentialsException("用户名或密码错误");
			}
		}
	}

	@Override
	public Set<String> findRoleStringById(long user_id) {
		Set<String> roles= new HashSet<>();
		SysUser u = this.selectById(user_id);
		String roleIds = u.getRoleIds();
		if(roleIds.contains(",")){
			String[] rids = roleIds.split(",");
			for(String id:rids){
				SysRole role = roleService.selectById(id);
				roles.add(role.getId().toString());
			}
		}else{//只有一个角色
			SysRole role = roleService.selectById(roleIds);
			roles.add(role.getId().toString());
		}
		return roles;
	}

	@Override
	public Set<String> findResourceStringById(long user_id) {
		Set<String> result = new HashSet<String>();
		Set<SysResource> resources = findResourcesById(user_id);
		for (SysResource r : resources) {
			if(r !=null) {
				if(StringUtils.isNotBlank(r.getPermission())) {
					result.add(r.getPermission());
				}
			}
			/*String url = r.getUrl();
			if ("".equals(url)) {
				continue;
			} else {
				if (url.contains(",")) {
					String urls[] = url.split(",");
					for (String u : urls) {
						result.add(u);
					}
				} else
					result.add(url);
			}*/
		}
		return result;
	}

	@Override
	public Set<SysRole> findRolesById(long user_id) {
		Set<SysRole> roles= new HashSet<>();
		SysUser u = this.selectById(user_id);
		String roleIds = u.getRoleIds();
		if(roleIds.contains(",")){
			String[] rids = roleIds.split(",");
			for(String id:rids){
				SysRole role = roleService.selectById(id);
				roles.add(role);
			}
		}else{//只有一个角色
			SysRole role = roleService.selectById(roleIds);
			roles.add(role);
		}
		return roles;
	}

	@Override
	public Set<SysResource> findResourcesById(long user_id) {
		Set<SysResource> result = new HashSet<SysResource>();
		Set<SysRole> lists = findRolesById(user_id);
		for(SysRole a:lists){
			String resouceIds = a.getResourceIds();
			if(resouceIds.contains(",")){
				String[] rids = resouceIds.split(",");
				for(String id:rids){
					SysResource sysRe = resService.selectById(id);
					if(!result.contains(sysRe)){
						result.add(sysRe);
					}
				}
			}else{//只有一个资源
				SysResource sysRe = resService.selectById(resouceIds);
				if(!result.contains(sysRe)){
					result.add(sysRe);
				}
			}
		}
		return result;
	}

	@Override
	public PTWResult selectByPage(Page<SysUser> page, String field, String order,
			Map<String, String> searchFields) {
		boolean flag = StringUtils.equals(order, "desc")?false:true;
		EntityWrapper<SysUser> wrapper = new EntityWrapper<SysUser>();
		Set<String> keys = searchFields.keySet();
		for (String k : keys) {
			if(!StringUtils.isEmpty(searchFields.get(k))) {
				System.out.println(k+":"+searchFields.get(k));
				wrapper.like(k, searchFields.get(k));
			}
		}
		wrapper.orderBy(field, flag);
		Page<SysUser> selectPage = this.selectPage(page, wrapper);
		System.out.println(selectPage.getTotal());
		return PTWResult.ok(selectPage.getRecords(),selectPage.getTotal());
	}
	/*
	 * 获取菜单
	 */
	@Override
	public PTWResult getMenu() {
		// TODO Auto-generated method stub
		org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
		ArrayList<SysResource> list = new ArrayList<SysResource>();
		// 根据用户的ID查询对应的所有权限
		SysUser sysuser = (SysUser) currentUser.getPrincipal();
		Set<SysResource> resourceset = this.findResourcesById(sysuser.getId());
		//已重写equals和hashCode方法
		Iterator<SysResource> iterator = resourceset.iterator();
		while(iterator.hasNext()) {
			SysResource sysresource = iterator.next();
			if(sysresource!=null) {
				list.add(sysresource);
			}
		}
		return PTWResult.ok(list);
	}
	/* (non-Javadoc)
	 * 更改用户密码
	 */
	@Override
	public PTWResult updatepwd(String password) {
		// TODO Auto-generated method stub
		org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
		SysUser sysuser = (SysUser) currentUser.getPrincipal();
		sysuser.setPassword(password);
		String pass = Kit.generatePass(sysuser);
		sysuser.setPassword(pass);
		System.err.println("用户名:"+sysuser.getUsername()+"更改后密码:"+pass);
		EntityWrapper<SysUser> wrapper = new EntityWrapper<SysUser>();
		boolean update = this.update(sysuser, wrapper.eq("id", sysuser.getId()));
		if(update) {
			return PTWResult.ok();
		}
		return PTWResult.build(-1, "修改失败");
	}
}
