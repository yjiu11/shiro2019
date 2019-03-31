package com.yjiu.shiro.tools;

import org.apache.shiro.crypto.hash.Md5Hash;

import com.yjiu.shiro.pojo.SysUser;


public class Kit {
	public static String generatePass(SysUser user){
		return new Md5Hash(user.getPassword(),user.getUsername()).toString();
	}
	public static void main(String[] args) {
		SysUser user = new SysUser();
		user.setUsername("admin");
		user.setPassword("1");
		System.out.println(generatePass(user));
	}
}
