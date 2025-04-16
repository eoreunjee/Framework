package com.ssafy.live;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calculater {
	
	private final static Logger log = LoggerFactory.getLogger(Calculater.class);
	public int add(int a, int b) {
		int result = a+b;
		log.trace("a: {}, b: {}, result: {}", a, b, result);
		return a+b;
	}

}
