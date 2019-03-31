package com.yjiu.shiro.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yjiu.shiro.mapper.SysUserMapper;
import com.yjiu.shiro.pojo.SysResource;
import com.yjiu.shiro.pojo.SysRole;
import com.yjiu.shiro.pojo.SysUser;
import com.yjiu.shiro.service.SysResourceService;
import com.yjiu.shiro.service.SysRoleService;
import com.yjiu.shiro.service.SysUserService;
import com.yjiu.shiro.tools.Kit;

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
			if(StringUtils.isNotBlank(r.getPermission())) {
				result.add(r.getPermission());
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

}
