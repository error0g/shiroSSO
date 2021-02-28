package cn.error0.common.shiro;


import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles =new HashSet<>();
        roles.add("sso");
        authorizationInfo.addRoles(roles);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if(authenticationToken==null)
            throw new UnknownAccountException();
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        if(token==null)
            throw new UnknownAccountException();
        if(token.getUsername().equals("admin")&&String.valueOf(token.getPassword()).equals("123456"))
        {
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    token,
                    token.getPassword(),
                    getName()
            );
            return authenticationInfo;
        }
        throw new UnknownAccountException();
    }
}
