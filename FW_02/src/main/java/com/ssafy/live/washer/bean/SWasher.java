package com.ssafy.live.washer.bean;

public class SWasher implements Washer{

	@Override
	public void wash(String clothes) {

		System.out.println(clothes+"를 세탁합니다.(S)");
		
	}
	
}
