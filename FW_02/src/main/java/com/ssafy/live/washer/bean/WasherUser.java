package com.ssafy.live.washer.bean;


public class WasherUser {
	
	private Washer washer;
	
	public WasherUser(Washer washer) {
		this.washer = washer;
	}
	
	public void useWasher(String clothes) {
		washer.wash(clothes);
	}

}
