package com.nice.datafileanomalydetection;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.nice.datafileanomalydetection.message.ReloadMessageSource;

@Configuration
public class WebMvcConfig  {
		
	
	@Bean
    public MessageSource messageSource() {		
		ReloadMessageSource message = new ReloadMessageSource();
		
		message.setBasenames("classpath:com/nice/datafileanomalydetection/i18n/messages");
		message.setDefaultEncoding("UTF-8");
		return message;		
    }
	
	
	@Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.KOREA);
        return slr;
    }
	
	

}
