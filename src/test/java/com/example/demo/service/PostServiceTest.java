package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.bean.Post;
import com.example.demo.bean.PostType;
import com.example.demo.dto.PostInputDto;
import com.example.demo.dto.PostOutputDto;

@SpringBootTest
public class PostServiceTest {

	@Autowired
	IPostService postServ;
	
	@Test
	@Disabled
	void addPostTest() {
		
		// Creating PostInputDto object
		PostInputDto newPost = new PostInputDto(); 
		
		// Setting the values
		newPost.setTitle("Post Test Case");
		newPost.setContent(PostType.LINK);
		newPost.setCreatedDateTime(LocalDateTime.now());
		newPost.setFlair("testcase1");
		newPost.setNotSafeForWork(false);
		newPost.setOriginalContent(true);
		newPost.setVotes(100);
		newPost.setVoteUp(true);
		newPost.setSpoiler(true);
		newPost.setFlag(false);
		
		// Adding the post
		Post post = postServ.addPost(newPost);
		
		// checking if the added post values are equal to the post or not
		assertEquals("Post Test Case", post.getTitle());
		assertEquals(PostType.LINK, post.getContent());
		assertEquals("testcase1", post.getFlair());
		assertEquals(100, post.getVotes());
		assertEquals(false, post.isNotSafeForWork());
		assertEquals(true, post.isOriginalContent());
		assertEquals(true, post.isSpoiler());
		assertEquals(true, post.isVoteUp());
	}
	
	
	@Test
	void updatePostTest() {
		// Creating PostInputDto object
		PostInputDto updatedPost = new PostInputDto(); 
		
		// Setting the values
		updatedPost.setPostId(2);
		updatedPost.setTitle("Updated Post 2");
		updatedPost.setContent(PostType.LINK);
		updatedPost.setCreatedDateTime(LocalDateTime.now());
		updatedPost.setFlair("updatedPost");
		updatedPost.setNotSafeForWork(false);
		updatedPost.setOriginalContent(true);
		updatedPost.setVotes(100);
		updatedPost.setVoteUp(true);
		updatedPost.setSpoiler(true);
		updatedPost.setFlag(false);
		
		// Updating the post
		Post post = postServ.updatePost(updatedPost);
		
		// checking if the added post values are equal to the post or not
		assertEquals(2, post.getPostId());
		assertEquals("Updated Post 2", post.getTitle());
		assertEquals(PostType.LINK, post.getContent());
		assertEquals("#updatedPost", post.getFlair());
		assertEquals(100, post.getVotes());
		assertEquals(false, post.isNotSafeForWork());
		assertEquals(true, post.isOriginalContent());
		assertEquals(true, post.isSpoiler());
		assertEquals(true, post.isVoteUp());
	}
	
	@Test
	void getPostByIdTest() {
		
		// Getting the post
		PostOutputDto post = postServ.getPostById(1);
		
		// comparing the values
		assertEquals(1, post.getPostId());
		assertEquals("Post Test 1", post.getTitle());
		assertEquals(PostType.TEXT, post.getContent());
		assertEquals("post1", post.getFlair());
		assertEquals(100, post.getVotes());
		assertEquals(true, post.isNotSafeForWork());
		assertEquals(true, post.isOriginalContent());
		assertEquals(true, post.isSpoiler());
		assertEquals(true, post.isVoteUp());
	}
	
	@Test
	void getPostsBySearchStringTest() {
		
		// Getting the posts
		List<PostOutputDto> posts = postServ.getPostBySearchString("ost");
		
		// checking the no of posts
		assertEquals(2, posts.size());
	}
	
	@Test
	void getAllPostsTest() {
		
		// Getting all Posts
		List<PostOutputDto> posts = postServ.getAllPosts();
		
		// checking the no of posts
		assertEquals(2, posts.size());
	}
	
	@Test
	void getAllPostsByCommunityTest() {
		
		// Getting all Posts
		List<PostOutputDto> posts = postServ.listPostsByCommunityId(102);
		
		// checking the no of posts
		assertEquals(2, posts.size());
	}
	
	@Test
	void getAllPostsByAwardTest() {
		
		// Getting all Posts
		List<PostOutputDto> posts = postServ.getPostByAwardId(103);
		
		// checking the no of posts
		assertEquals(2, posts.size());
	}
	
	@Test
	void getAllPostsOfBloggerTest() {
		
		// Getting all Posts
		List<PostOutputDto> posts = postServ.getPostsByBlogger(101);
		
		// checking the no of posts
		assertEquals(2, posts.size());
	}
	
	@Test
	void getAllUpvotedPostsOfBlogger() {
		
		// Getting all Posts
		List<PostOutputDto> posts = postServ.getUpvotedPostsOfBlogger(101);
		
		// checking the no of posts
		assertEquals(2, posts.size());
	}
}
