package com.lin.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.lin.entity.User;
import com.lin.service.UserService;
import com.lin.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: Joe
 * @created: 2020/7/25 17:50
 */

@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        JwtToken jwtToken = (JwtToken) token;

        String userId = jwtUtils.getClaimByToken((String)jwtToken.getPrincipal()).getSubject();
        User user = userService.getById(Long.valueOf(userId));

        if(user ==null){
            throw new UnknownAccountException("该用户不存在！");
        }
        if(user.getStatus()==-1){
            throw new LockedAccountException("该用户已被锁定！");
        }

        AccoutProfile profile = new AccoutProfile();
        BeanUtil.copyProperties(user,profile);

        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());
    }
}

