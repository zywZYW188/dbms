package com.example.dbms.shiro;
import com.example.dbms.domain.po.Permission;
import com.example.dbms.domain.po.Role;
import com.example.dbms.domain.po.Users;
import com.example.dbms.service.PermissionService;
import com.example.dbms.service.RoleService;
import com.example.dbms.service.UsersService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UsersService usersService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;


    /**
     * 授权模块，获取用户角色和权限
     *
     * @param principal principal
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal)
    {
        Users user = (Users) SecurityUtils.getSubject().getPrincipal();
        String userName = user.getUsername();
        System.out.println("用户" + userName + "获取权限-----ShiroRealm.doGetAuthorizationInfo");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 获取用户角色集

        List<Role> roleList =roleService.findRoleByUsername(userName);
        Set<String> roleSet = new HashSet<String>();
        for (Role r : roleList) {
            roleSet.add(r.getRole_name());
        }
        simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限集
        List<Permission> permissionList = permissionService.findUserPermissions(userName);
        Set<String> permissionSet = new HashSet<String>();
        for (Permission p : permissionList) {
            permissionSet.add(p.getPermission());
        }
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        // 获取用户输入的用户名和密码
        String userName = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        System.out.println("用户" + userName + "认证-----ShiroRealm.doGetAuthenticationInfo");

        // 通过用户名到数据库查询用户信息
        Users users = this.usersService.findByUsername(userName);
        if (users == null) {
            throw new UnknownAccountException("用户名或密码错误！");
        }
        if (!password.equals(users.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(users, password, getName());
        return info;
    }


}
