/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ygsoft.security.shiro.ShiroDbRealm.java
 * Class:			ShiroDbRealm
 * Date:			2012-8-2
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package shiro;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.wasu.ptyw.galaxycrm.core.cache.CrmOrganizationCache;
import com.wasu.ptyw.galaxycrm.core.cache.CrmRoleCache;
import com.wasu.ptyw.galaxycrm.core.cache.CrmRolePermissionCache;
import com.wasu.ptyw.galaxycrm.core.cache.CrmUserRoleCache;
import com.wasu.ptyw.galaxycrm.core.manager.CrmRoleManager;
import com.wasu.ptyw.galaxycrm.core.manager.CrmRolePermissionManager;
import com.wasu.ptyw.galaxycrm.core.manager.CrmUserManager;
import com.wasu.ptyw.galaxycrm.core.manager.CrmUserRoleManager;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmRoleDO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmUserDO;
import com.wasu.ptyw.galaxycrm.dal.dataobject.CrmUserRoleDO;
import com.wasu.ptyw.galaxycrm.dal.query.CrmUserQuery;

/**
 * 
 * @author wenguang
 * @date 2015年8月7日
 */
@Slf4j
public class ShiroDbRealm extends AuthorizingRealm {
	private static final int INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	private static final String ALGORITHM = "SHA-1";

	@Resource
	CrmUserManager crmUserManager;
	@Resource
	CrmUserRoleManager crmUserRoleManager;
	@Resource
	CrmRoleManager crmRoleManager;
	@Resource
	CrmRolePermissionManager crmRolePermissionManager;

	@Resource
	CrmOrganizationCache crmOrganizationCache;
	@Resource
	CrmUserRoleCache crmUserRoleCache;
	@Resource
	CrmRoleCache crmRoleCache;
	@Resource
	CrmRolePermissionCache crmRolePermissionCache;

	/**
	 * 给ShiroDbRealm提供编码信息，用于密码密码比对 描述
	 */
	public ShiroDbRealm() {
		super();
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ALGORITHM);
		matcher.setHashIterations(INTERATIONS);

		setCredentialsMatcher(matcher);
	}

	/**
	 * 认证回调函数, 登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		/**
		 * CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken)
		 * authcToken;
		 * 
		 * String parm = token.getCaptcha(); String c =
		 * (String)SecurityUtils.getSubject().getSession()
		 * .getAttribute(SimpleCaptchaServlet.CAPTCHA_KEY); // 忽略大小写 if
		 * (!parm.equalsIgnoreCase(c)) { throw new
		 * IncorrectCaptchaException("验证码错误！"); }
		 **/
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		CrmUserDO user = getByUserName(token.getUsername());
		if (user != null) {
			if (user.getStatus() == -1) {
				throw new DisabledAccountException();
			}

			byte[] salt = Encodes.decodeHex(user.getSalt());

			ShiroUser shiroUser = new ShiroUser(user.getId(), user.getUsername(), user);
			return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}

	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.fromRealm(getName()).iterator().next();
		List<CrmUserRoleDO> userRoles = shiroUser.getUser().getUserRoles();

		if (!userRoles.isEmpty()) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			for (CrmUserRoleDO userRole : userRoles) {
				// 基于Permission的权限信息
				info.addStringPermissions(userRole.getRole().getPermissionList());
			}
			return info;
		} else {
			return null;
		}
	}

	public static class HashPassword {
		public String salt;
		public String password;
	}

	public HashPassword encrypt(String plainText) {
		HashPassword result = new HashPassword();
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		result.salt = Encodes.encodeHex(salt);

		byte[] hashPassword = Digests.sha1(plainText.getBytes(), salt, INTERATIONS);
		result.password = Encodes.encodeHex(hashPassword);
		return result;

	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {

		private static final long serialVersionUID = -1748602382963711884L;
		@Getter
		private Long id;
		@Getter
		private String loginName;
		@Getter
		private CrmUserDO user;
		@Getter
		@Setter
		private String regionCode;

		public ShiroUser() {

		}

		public ShiroUser(Long id, String loginName, CrmUserDO user) {
			this.id = id;
			this.loginName = loginName;
			this.user = user;
		}

		@Override
		public String toString() {
			return loginName;
		}
	}

	private CrmUserDO getByUserName(String username) {
		try {
			if (StringUtils.isEmpty(username)) {
				return null;
			}
			CrmUserQuery query = new CrmUserQuery();
			query.setUsername(username);
			CrmUserDO obj = crmUserManager.queryFirst(query);
			if (obj == null)
				return null;
			List<CrmUserRoleDO> userRoles = crmUserRoleCache.get(obj.getId());
			setRolesAndPermissions(userRoles);
			obj.setUserRoles(userRoles);
			obj.setOrganization(crmOrganizationCache.get(obj.getOrgId()));
			return obj;
		} catch (Exception e) {
			log.error("getByUserName error,username=" + username, e);
		}
		return null;
	}

	private void setRolesAndPermissions(List<CrmUserRoleDO> userRoles) {
		try {
			if (CollectionUtils.isEmpty(userRoles)) {
				return;
			}
			for (CrmUserRoleDO userRole : userRoles) {
				CrmRoleDO role = crmRoleCache.get(userRole.getRoleId());
				if (role == null)
					continue;
				role.setPermissionList(crmRolePermissionCache.get(userRole.getRoleId()));
				userRole.setRole(role);
			}
		} catch (Exception e) {
			log.error("setRolesAndPermissions error,uid=" + userRoles, e);
		}
	}
}
