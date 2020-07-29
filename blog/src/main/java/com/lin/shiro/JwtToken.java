package com.lin.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author: Joe
 * @created: 2020/7/1 11:42
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String jwt){
        this.token=jwt;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
