package cn.error0.service1.config;


import cn.error0.common.shiro.MyShiroRealm;
import cn.error0.service1.SSOfilter;
import cn.error0.common.shiro.RedisSessionDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class shiroConfig {

    @Bean
    SSOfilter SSOfilter()
    {
        return new SSOfilter();
    }

    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager SecurityManager,SSOfilter SSOfilter)
    {
        ShiroFilterFactoryBean ShiroFilterFactoryBean=new ShiroFilterFactoryBean();
        Map<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
        Map<String, Filter> filtersMap = new LinkedHashMap <String, Filter>();
        ShiroFilterFactoryBean.setLoginUrl("http://127.0.0.1:8081/login");
        ShiroFilterFactoryBean.setSecurityManager(SecurityManager);
        filterChainDefinitionMap.put("/","authc");
        filterChainDefinitionMap.put("/**","sso");
        ShiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        filtersMap.put("sso",SSOfilter);
        ShiroFilterFactoryBean.setFilters(filtersMap);
        return ShiroFilterFactoryBean;
    }
    @Bean
    public DefaultWebSessionManager sessionManager(SessionDAO sessionDAO)
    {
        DefaultWebSessionManager defaultWebSessionManager=new DefaultWebSessionManager();
        defaultWebSessionManager.setCacheManager(null);
        defaultWebSessionManager.setSessionDAO(sessionDAO);
        return defaultWebSessionManager;
    }

    @Bean
    public SessionDAO  sessionDAO()
    {
        return new RedisSessionDAO();
    }

    @Bean
    public Realm realm()
    {
        return new MyShiroRealm();
    }

}
