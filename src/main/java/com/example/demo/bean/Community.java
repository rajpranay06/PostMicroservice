package com.example.demo.bean;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Community {
	
	@Id
	@GeneratedValue
	private int communityId;
	//Community Description
	private String communityDescription;
	//Total members in Community
	private int totalMembers;
	//Total online members in community
	private int onlineMembers;
	
	//Date when community is created
	private LocalDateTime createdOn;

}

