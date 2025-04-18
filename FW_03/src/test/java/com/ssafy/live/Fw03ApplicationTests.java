package com.ssafy.live;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.live.model.service.MemberService;

@SpringBootTest
class Fw03ApplicationTests {
	
	private final Logger log = LoggerFactory.getLogger(Fw03ApplicationTests.class);
	
	@Value("${service.manager.name}")
	private String managerName;
	
	@Value("${service.manager.age}")
	private int managerAge;
	
	@Value("${service.roles}")
	private List<String> roles;

	@Test
	void contextLoads() {
		Assertions.assertEquals(managerAge, 30);
		Assertions.assertEquals(managerName, "Eor");
		log.debug("debug:{}", log);
	}
	
	
	@Autowired
	MemberService mService;
	
	@Test
	public void mServiceTest() {
		Assertions.assertNotNull(mService);
	}

}
