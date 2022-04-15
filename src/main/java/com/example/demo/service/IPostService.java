package com.example.demo.service;

import java.util.List;

import com.example.demo.bean.Post;
import com.example.demo.dto.PostInputDto;
import com.example.demo.dto.PostOutputDto;

public interface IPostService {
	
	public Post addPost(PostInputDto post);
	public Post updatePost(PostInputDto post);
	public void deletePost(int id);
	public List<PostOutputDto> getPostBySearchString(String searchStr);
	public void upVote(int postId, boolean upVote);
	public Post addPostWithoutDto(Post post);
	public List<PostOutputDto> getAllPosts();
	public List<PostOutputDto> getPostByAwardId(int awardId);
	public List<PostOutputDto> listPostsByCommunityId(int communityId);
	public List<PostOutputDto> getPostsByBlogger(int bloggerId);
	public List<PostOutputDto> getUpvotedPostsOfBlogger(int bloggerId);
	public PostOutputDto getPostById(int postId);
}
