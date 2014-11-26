package com.may.sys;

import junit.framework.TestCase;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroTest extends TestCase {

	public static final Logger log = LoggerFactory.getLogger(ShiroTest.class);
	
	public void testHelloWord() {
		log.info("My First Apache Shiro Application");
		System.exit(0);
	}
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		
		this.testSecurityManager();
	}
	
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		
		this.testLogout();
	}

	public void testSecurityManager() {
		//1.装入INI配置
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

		//2.创建SecurityManager
		SecurityManager securityManager = factory.getInstance();

		//3.使其可访问
		SecurityUtils.setSecurityManager(securityManager);
	}
	
	public void testLogin() {
		Subject currentUser = SecurityUtils.getSubject();
		//登录当前用户然后对角色和权限进行检查
//		if (!currentUser.isAuthenticated()) {
		if (!currentUser.isRemembered()) {
			UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
			token.setRememberMe(true);
			try {
				currentUser.login(token);
				log.info("登录 " + currentUser.getPrincipal());
			} catch(UnknownAccountException aue) {
				log.error("用户不存在" + aue.getMessage());
			} catch(IncorrectCredentialsException ice) {
				log.error("密码不正确" + ice.getMessage());
			} catch(LockedAccountException lae) {
				log.error("用户被锁定" + lae.getMessage());
			} catch(AuthenticationException ae) {
				log.error(ae.getMessage());
			}
		}
	}
	
	public void testLogout() {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			log.info("注销 " + currentUser.getPrincipal());
			currentUser.logout();
//			subject.logout()后立即将终端用户重定向到一个新的视图或页面
		}
	}
	
	public void testCheckRole() {
		this.testLogin();
		
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.hasRole("schwartz")) {
			log.info("进入角色");
		} else {
			log.error("角色不匹配");
		}
	}
	
	public void testCheckAction() {
		this.testLogin();
		
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isPermittedAll("winnebago:drive:eagle5","lightsaber:*")) {
			log.info("拥有权限");
		} else {
			log.error("无权限");
		}
	}
	
	public void testRealm() {
		this.testLogin();
		
	}
}
