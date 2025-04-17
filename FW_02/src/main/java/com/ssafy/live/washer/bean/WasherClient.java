package com.ssafy.live.washer.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ssafy.live.washer.bean.WasherUser;
import com.ssafy.live.washer.bean.WasherConfig;
import com.ssafy.live.washer.bean.Washer;


public class WasherClient {
	
	public static void main(String[] args){
		ApplicationContext ctx = new AnnotationConfigApplicationContext(WasherConfig.class);
		
		WasherUser user = ctx.getBean(WasherUser.class);
		
		user.useWasher("양말");
		
		//동일 이름의 빈-이름으로 필터링
		Washer washer = ctx.getBean("sWasher", Washer.class);
		washer.wash("양말");
		
	}

}
