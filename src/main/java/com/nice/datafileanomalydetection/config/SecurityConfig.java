package com.nice.datafileanomalydetection.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nice.datafileanomalydetection.constant.LoginConstant;
import com.nice.datafileanomalydetection.handler.AuthFailurHandler;
import com.nice.datafileanomalydetection.handler.AuthSuccessHandler;
import com.nice.datafileanomalydetection.member.service.MemberService;
import com.nice.datafileanomalydetection.role.model.Role;
import com.nice.datafileanomalydetection.role.service.RoleService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private  RoleService roleService;
	@Autowired
	private  MemberService memberService;

	@Autowired
	private  AuthFailurHandler authFailurHandler;
	@Autowired
	private  AuthSuccessHandler authSuccessHandler;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
		web.ignoring().antMatchers("/schema/**","/css/**", "/js/**", "/img/**", "/lib/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
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
			.permitAll().and() 				
			.logout().logoutRequestMatcher(new AntPathRequestMatcher(LoginConstant.LOGOUT))
			.logoutSuccessUrl(LoginConstant.LOGIN).invalidateHttpSession(true).clearAuthentication(true).and()
			.exceptionHandling()
			.accessDeniedPage(LoginConstant.ACCESS_DENIED);
	}
	
	
	
	@Override
	  public void configure(AuthenticationManagerBuilder auth) throws Exception { // 9
	    auth.userDetailsService(memberService)
	    	// 해당 서비스(userService)에서는 UserDetailsService를 implements해서 
	        // loadUserByUsername() 구현해야함 (서비스 참고)
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
    
    protected void setAntMatchers(HttpSecurity http) throws Exception {
            	
    	List<Role> roleList = roleService.getRoleList();    	
    	roleList.forEach(role->{
    		String[] roles = role.getRoleType().split(LoginConstant.SEPARATE);
             // 권한 앞에 접두사(rolePrefix) 붙임
             for(int i = 0; i < roles.length; i++) {
               roles[i] = 
            		   (i == 0 ) ? 
            				   roles[i].toUpperCase() 
            				   :LoginConstant.ROLE_PRIFIX + roles[i].toUpperCase();
             }
             // DB에서 url을 읽어올 때 앞에 '/'이 없다면
             // 앞에 '/'를 넣어준다.
             String url = role.getUrl();
             if(LoginConstant.URL_SEPARATE != url.charAt(0)) {
               url = LoginConstant.URL_SEPARATE + url;
             }
             try {
				http.authorizeRequests()
				 .antMatchers(url)
				 .hasAnyAuthority(roles);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	});    			
    	 http.authorizeRequests()
         .antMatchers(LoginConstant.LOGIN,LoginConstant.H2CONSOLE).permitAll()  
         .anyRequest().authenticated()
         .and().csrf().ignoringAntMatchers(LoginConstant.H2CONSOLE)
         .and()
         .headers().frameOptions().sameOrigin(); 
       
      }
    
    // 비밀번호 생성 ex)
//    public static void main(String[] aa) {
//    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//       System.out.println(encoder.encode("1234"));
//    }
}
