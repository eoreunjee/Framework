package com.ssafy.live.washer.bean;

public class LWasher implements Washer {

	@Override
	public void wash(String clothes) {
		System.out.println(clothes+"를 세탁합니다.(L)");
		
	}

}
