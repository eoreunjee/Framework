package com.ssafy.live;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculaterUnitTest {
	
	//멤버변수
	private Calculater calc;
	
	//test 전에 calculator 변수를 만들자.
	@BeforeEach
	public void setup() {
		calc = new Calculater();
	}
	
	
	//Junit 단위테스트
	@Test
	@DisplayName("계산 결과 확인: ex) 10 + 20 = 30")
	public void 계산기의_결과가_잘_나오는지_검증하는_테스트() {
		
		int a=10;
		int b=20;
		
		int result=calc.add(a, b);
		Assertions.assertEquals(30, result);
		
		
		
	}
}
