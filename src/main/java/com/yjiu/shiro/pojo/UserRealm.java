package com.yjiu.shiro.pojo;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yjiu.shiro.service.SysUserService;

public class UserRealm extends AuthorizingRealm {
	@Autowired
	private SysUserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
//		System.out.println("执行了：UserRealm类：doGetAuthorizationInfo()授权方法");
		/** 查询数据库、此处静态添加 */
		SysUser user = (SysUser) principals.getPrimaryPrincipal();
		user = userService.selectOne(new EntityWrapper<SysUser>().eq("username", user.getUsername()));
		Set<String> rolesString = userService.findRoleStringById(user.getId());
		Set<String> resourcesString = userService.findResourceStringById(user.getId());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(rolesString);
		info.addStringPermissions(resourcesString);
//		System.out.println("-----rolesString-----");
		System.out.println(rolesString);
//		System.out.println("-----resourcesString-----");
		System.out.println(resourcesString);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
//		System.out.println("执行了：UserRealm类：doGetAuthenticationInfo()认证方法");
		String username = token.getPrincipal().toString();
		String password = new String((char[]) token.getCredentials());
		SysUser u = new SysUser();
		u.setUsername(username);
		u.setPassword(password);
		SysUser user = userService.login(u);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,
				//user.getUsername(), 
				user.getPassword(), this.getName());
		info.setCredentialsSalt(ByteSource.Util.bytes(u.getUsername()));
		return info;
	}
	@Override
	protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("清除授权缓存");
		//以下几行并没有实际用处，只是显示所有缓存
		Cache c = this.getAuthorizationCache();
		Set<Object> keys = c.keys();
		for(Object o:keys){
			System.out.println("授权缓存"+o+"------"+c.get(o));
		}
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		/**认证无法清除，因为他传入的是用户名，不是对象*/
		System.out.println("清除认证缓存");
		SysUser user = (SysUser)principals.getPrimaryPrincipal();
		SimplePrincipalCollection spc = new SimplePrincipalCollection(user.getUsername(),getName());
		super.clearCachedAuthenticationInfo(spc);
	}
}
