package com.example.demo.bean;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity       
@Data                        // Used to create all getter setter constructors and to string methods                           // Used to create all getter setter constructors and tostring methods
public class Post {
	
	@Id                  // Making postId as primary key
	@GeneratedValue      // Auto generation of values
	private int postId;
	@Size(min = 3, max = 50, message = "Title should be of 3 to 50 characters")
	private String title;
	
	@Enumerated(EnumType.ORDINAL)
	private PostType content;
	private LocalDateTime createdDateTime;
	private int votes;
	private boolean voteUp;
	private boolean notSafeForWork;
	private boolean spoiler;
	private boolean originalContent;
	private String flair;

	//ManytoOne Relationship with blogger
	//One blogger can have many posts
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "blogger_id")
	private Blogger blogger;
	
	//OneToMany-One Community can have many posts, One post Belongs to one Community
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="CommunityId")
	private Community community;
	
	// ManyToMany Relationship with awards
	// Multiple posts can have multiple awards
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(
		name = "post_awards",
		joinColumns = {@JoinColumn(name = "post_id") },
		inverseJoinColumns = { @JoinColumn(name = "award_id") }
	)
	private List<Award> awards;
    
}
