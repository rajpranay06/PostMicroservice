package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.bean.Award;
import com.example.demo.bean.PostType;

import lombok.Data;

@Data
public class PostOutputDto {
	
	private int postId;
	private String title;
	private PostType content;
	private LocalDateTime createdDateTime;
	private int votes;
	private boolean voteUp;
    private boolean notSafeForWork;
    private boolean spoiler;
    private boolean originalContent;
    private String flair;
    private List<Award> awards;
}
