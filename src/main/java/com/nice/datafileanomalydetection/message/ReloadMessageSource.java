package com.nice.datafileanomalydetection.message;

import java.util.Locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class ReloadMessageSource extends ReloadableResourceBundleMessageSource implements Message {
	
	private Locale locale = null;
	public ReloadMessageSource(){
		locale = Locale.getDefault();
	}
	
	public ReloadMessageSource(Locale locale){
		this.locale = locale;
	}
	
	
	public String getMessage(String code){
		return this.getMessage(code, null, locale);
	}
	
	
	public String getMessage(String code, Object[] args){
		return this.getMessage(code, args, locale);
	}
	
	
	@Override
	public String getMessage(String code, String locale) {
		return super.getMessage(code, null, new Locale(locale));
	}
	
	

}
