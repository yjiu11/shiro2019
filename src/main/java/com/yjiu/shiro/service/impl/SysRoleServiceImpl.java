package com.yjiu.shiro.service.impl;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yjiu.shiro.mapper.SysRoleMapper;
import com.yjiu.shiro.pojo.SysRole;
import com.yjiu.shiro.service.SysRoleService;
import com.yjiu.shiro.tools.PTWResult;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjiu123
 * @since 2019-04-03
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
	@Override
	public PTWResult selectByPage(Page<SysRole> page, String field, String order,
			Map<String, String> searchFields) {
		boolean flag = StringUtils.equals(order, "desc")?false:true;
		EntityWrapper<SysRole> wrapper = new EntityWrapper<SysRole>();
		Set<String> keys = searchFields.keySet();
		for (String k : keys) {
			if(!StringUtils.isEmpty(searchFields.get(k))) {
				System.out.println(k+":"+searchFields.get(k));
				wrapper.like(k, searchFields.get(k));
			}
		}
		wrapper.orderBy(field, flag);
		Page<SysRole> selectPage = this.selectPage(page, wrapper);
		System.out.println(selectPage.getTotal());
		return PTWResult.ok(selectPage.getRecords(),selectPage.getTotal());
	}

}
