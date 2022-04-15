package com.example.demo.bean;

public class Moderator{
	
	public boolean moderatesPostsAndComments(boolean flag) {
		if(flag) {
			// the flag is true so comment or post cannot be added
			return false;
		}
		else {
			// the flag is false so comment or post can be added
			return true;
		}
	}
}
