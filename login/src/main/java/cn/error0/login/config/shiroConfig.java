package cn.error0.login.config;

import cn.error0.common.shiro.MyShiroRealm;
import cn.error0.common.shiro.RedisSessionDAO;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class shiroConfig {

    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager SecurityManager)
    {
        ShiroFilterFactoryBean ShiroFilterFactoryBean=new ShiroFilterFactoryBean();
        Map<String, String> filterChainDefinitionMap=new HashMap<>();
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        ShiroFilterFactoryBean.setLoginUrl("/login");
        ShiroFilterFactoryBean.setSecurityManager(SecurityManager);
        filterChainDefinitionMap.put("/login","authc");
        filterChainDefinitionMap.put("/logout","logout");
        ShiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        ShiroFilterFactoryBean.setFilters(filtersMap);
        return ShiroFilterFactoryBean;
    }
    @Bean
    public DefaultWebSessionManager sessionManager(SessionDAO sessionDAO)
    {
        DefaultWebSessionManager defaultWebSessionManager=new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDAO(sessionDAO);
        return defaultWebSessionManager;
    }

    @Bean
    public SessionDAO  sessionDAO()
    {
        return new RedisSessionDAO();
    }

    @Bean
    public Realm Realm()
    {
        return new MyShiroRealm();
    }

}
