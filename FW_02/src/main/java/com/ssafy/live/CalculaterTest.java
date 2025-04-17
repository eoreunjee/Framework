package com.ssafy.live;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalculaterTest {
	
	private static final Logger log = LoggerFactory.getLogger(CalculaterTest.class);
	
	public static void main(String [] args) {
		
		//기본 동작은 하지만, 커스터마이징이 필요.
//		log.trace("leve:{}", "trace level");
//		log.debug("leve:{}", "debug level");
//		log.info("leve:{}", "info level");
//		log.warn("leve:{}", "warn level");
//		log.error("leve:{}", "error level");
		
		Calculater c = new Calculater();
		int result = c.add(10, 20);
		System.out.println(result);
	}

}
