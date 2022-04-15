package com.example.demo.bean;

public enum Coin {

	SILVER(200),GOLD(500),PLATINUM(700),REGULAR(100);
	private int value;
	 
	// Value to the coin
	Coin(int i) {
		this.value=i;	
	}
	
}
