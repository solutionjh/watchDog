package com.nice.datafileanomalydetection.config;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import com.nice.datafileanomalydetection.constant.LoginConstant;
import com.nice.datafileanomalydetection.handler.AuthFailurHandler;
import com.nice.datafileanomalydetection.handler.AuthSuccessHandler;
import com.nice.datafileanomalydetection.member.service.MemberService;
import com.nice.datafileanomalydetection.role.model.Role;
import com.nice.datafileanomalydetection.role.service.RoleService;


@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();
	
	@Autowired
	private  RoleService roleService;
	@Autowired
	private  MemberService memberService;

	@Autowired
	private  AuthFailurHandler authFailurHandler;
	@Autowired
	private  AuthSuccessHandler authSuccessHandler;
	
	
	 @Autowired
	 DataSource dataSource;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(LoginConstant.SCHEMA,LoginConstant.ASSETS,LoginConstant.WEBJARS);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//http.csrf().disable(); //임시
		
		setAntMatchers(http);		 
		
		http.sessionManagement()
		    .maximumSessions(1) ////최대 허용 가능 세션 수, (-1: 무제한)
		    .and()
		    .invalidSessionUrl(LoginConstant.LOGIN);
		
		http.formLogin()
			.loginPage(LoginConstant.LOGIN)
			.loginProcessingUrl(LoginConstant.LOGIN_PROCESS)				
			.failureHandler(authFailurHandler)
			.successHandler(authSuccessHandler)
			.usernameParameter(LoginConstant.USERNAME)
	        .passwordParameter(LoginConstant.PASSWORD)
			.permitAll()
			.and() 				
			.logout().logoutRequestMatcher(new AntPathRequestMatcher(LoginConstant.LOGOUT)).deleteCookies("JSESSIONID")
			.logoutSuccessUrl(LoginConstant.LOGIN).invalidateHttpSession(true).clearAuthentication(true)
			.and()
			.exceptionHandling()
			.accessDeniedPage(LoginConstant.ACCESS_DENIED);
		
		http.rememberMe()
		  .userDetailsService(memberService)
		  .authenticationSuccessHandler(authSuccessHandler)
		  .key(LoginConstant.REMEMBER_KEY) 
		  .tokenRepository(persistentTokenRepository()) 
		  .tokenValiditySeconds(LoginConstant.REMEMBER_SECOND); 
	}
	
	@Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
	
	
	@Override
	  public void configure(AuthenticationManagerBuilder auth) throws Exception { // 9
	    auth.userDetailsService(memberService)
	    	.passwordEncoder(new BCryptPasswordEncoder()); 
	   }	
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    } 
    
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource); 
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }    
    
    protected void setAntMatchers(HttpSecurity http) throws Exception {
            	
    	List<Role> roleList = roleService.getRoleList();    	
    	roleList.forEach(role->{
    		if(!StringUtils.isEmpty(role.getUrl())) {    			
    			String[] roles = role.getRoleType().split(LoginConstant.SEPARATE);    
    			String[] urls = role.getUrl().split(LoginConstant.SEPARATE);  
    			try {
    				http.authorizeRequests()
    				.antMatchers(urls)
    				.hasAnyAuthority(roles);
    			} catch (Exception e) {
    				logger.warn("onAuthenticationSuccess ::::: Login Session or Access Fail!!" );
    			}
    		}
    	});   
    	
    	 http.authorizeRequests()  
         .antMatchers(LoginConstant.LOGIN,LoginConstant.H2CONSOLE,LoginConstant.LOGIN_PROCESS).permitAll()  
         .anyRequest().authenticated()
         .and().csrf().ignoringAntMatchers(LoginConstant.H2CONSOLE)
         .and()
			.exceptionHandling()
			.accessDeniedPage(LoginConstant.ACCESS_DENIED)
         .and()
         .headers().frameOptions().sameOrigin();   
    	 
    	
      }
}
