package com.ssafy.live.washer.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WasherConfig {
	
	@Bean
	public SWasher sWasher() {
		
		return new SWasher();
		
	}
	
	@Bean
	public LWasher lWasher() {
		return new LWasher();
	}
	
	@Bean
	public WasherUser washerUser() {
		WasherUser user = new WasherUser(lWasher());
		
		return user;
	}

}
