package com.ssafy.live.washer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ssafy.live.washer.bean.WasherUser;
import com.ssafy.live.washer.bean.WasherConfig;
import com.ssafy.live.washer.bean.Washer;


public class WasherTest {
	
	ApplicationContext ctx;
	
	@BeforeEach
	public void setup() {
		ctx = new AnnotationConfigApplicationContext(WasherConfig.class);
	}
	
	@Test
	public void usertest(){
		
		WasherUser user = ctx.getBean(WasherUser.class);
		user.useWasher("양말");
		
		//동일 이름의 빈-이름으로 필터링
		Washer washer = ctx.getBean("sWasher", Washer.class);
		washer.wash("양말");
		
		Assertions.assertNotNull(user);
	}

}