

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.plugins.Page;
import com.yjiu.shiro.App;
import com.yjiu.shiro.mapper.SysUserMapper;
import com.yjiu.shiro.service.SysUserService;
import com.yjiu.shiro.tools.PTWResult;

@RunWith(SpringJUnit4ClassRunner.class) // 让junit与spring环境进行整合
@SpringBootTest(classes = { App.class }) // 自动加载spring相关的配置文件
public class TestMain {
	@Autowired
	private SysUserService userService;
	@Autowired
	private SysUserMapper userMapper;
	@Test
	public void t1() {
	}
}
