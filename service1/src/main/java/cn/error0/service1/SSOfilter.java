package cn.error0.service1;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class SSOfilter extends AuthorizationFilter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o)  {
    /**
     *   获取浏览器传来的cookie作为key去redis查询Session
     *   等到Session后获取保存在Session的验证信息再由shiro验证
     * */
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cookie[] cookies = request.getCookies();

        try {
            SimpleSession simpleSession = null;
            simpleSession = (SimpleSession) redisTemplate.opsForValue().get(cookies[0].getValue());
            UsernamePasswordToken token = (UsernamePasswordToken) simpleSession.getAttribute("token");
            SecurityUtils.getSubject().login(token);
        }catch (Exception e)
        {
            return false;
        }
        return true;
    }
}
