package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.bean.Post;
import com.example.demo.bean.PostType;
import com.example.demo.dto.PostInputDto;
import com.example.demo.dto.PostOutputDto;
import com.example.demo.repository.IPostRepository;

@ExtendWith(SpringExtension.class)
public class PostServiceMockitoTest {

//	// @InjectMocks - Creates instance of a class and injects mocks that are created with @Mock
	@InjectMocks
	PostServiceImpl postServ;
	
	// @MockBean - Creates Mock of a class or interface
	@MockBean
	IPostRepository postRepo;
	
	// Initialization of mock objects
	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void addPostTest() {
		Post newPost = new Post();
		// Setting the values
		newPost.setPostId(100);
		newPost.setTitle("Post Demo");
		newPost.setContent(PostType.VIDEO_IMAGE);
		newPost.setCreatedDateTime(LocalDateTime.now());
		newPost.setFlair("post");
		newPost.setNotSafeForWork(false);
		newPost.setOriginalContent(true);
		newPost.setVotes(10000);
		newPost.setVoteUp(false);
		newPost.setSpoiler(true);
		
		// Sending post object when save function is called
		Mockito.when(postRepo.save(newPost)).thenReturn(newPost);
		
		Post addedPost = postServ.addPostWithoutDto(newPost);	

		// checking if the added post values are equal to the post or not
		assertEquals(100, addedPost.getPostId());
		assertEquals("Post Demo", addedPost.getTitle());
		assertEquals(PostType.VIDEO_IMAGE, addedPost.getContent());
		assertEquals("post", addedPost.getFlair());
		assertEquals(10000, addedPost.getVotes());
		assertEquals(false, addedPost.isNotSafeForWork());
		assertEquals(true, addedPost.isOriginalContent());
		assertEquals(true, addedPost.isSpoiler());
		assertEquals(false, addedPost.isVoteUp());
	}
	
	@Test
	void updatePostTest() {

		// Creating PostInputDto object
		PostInputDto updatedPost = new PostInputDto(); 
		
		// Setting the values
		updatedPost.setPostId(59);
		updatedPost.setTitle("Updated Post");
		updatedPost.setContent(PostType.LINK);
		updatedPost.setCreatedDateTime(LocalDateTime.now());
		updatedPost.setFlair("updatedpost");
		updatedPost.setNotSafeForWork(false);
		updatedPost.setOriginalContent(true);
		updatedPost.setVotes(234578);
		updatedPost.setVoteUp(true);
		updatedPost.setSpoiler(true);
		updatedPost.setFlag(false);
		
		// Creating post object
		Post post = new Post();
		
		// Setting the post values
		post.setPostId(updatedPost.getPostId());
		post.setTitle(updatedPost.getTitle());
		post.setContent(updatedPost.getContent());
		post.setCreatedDateTime(updatedPost.getCreatedDateTime());
		post.setFlair(updatedPost.getFlair());
		post.setNotSafeForWork(updatedPost.isNotSafeForWork());
		post.setOriginalContent(updatedPost.isOriginalContent());
		post.setVotes(updatedPost.getVotes());
		post.setVoteUp(updatedPost.isVoteUp());
		post.setSpoiler(updatedPost.isSpoiler());

		// Sending the post object when the following functions are called instead of using database
		Mockito.when(postRepo.findById(59)).thenReturn(Optional.of(post));
		Mockito.when(postRepo.save(post)).thenReturn(post);
		
		Post updatedPostOutput = postServ.updatePost(updatedPost);
		
		// checking if the updated post values are equal to the post or not
		assertEquals(59, updatedPostOutput.getPostId());
		assertEquals("Updated Post", updatedPostOutput.getTitle());
		assertEquals(PostType.LINK, updatedPostOutput.getContent());
		assertEquals("#updatedpost", updatedPostOutput.getFlair());
		assertEquals(234578, updatedPostOutput.getVotes());
		assertEquals(false, updatedPostOutput.isNotSafeForWork());
		assertEquals(true, updatedPostOutput.isOriginalContent());
		assertEquals(true, updatedPostOutput.isSpoiler());
		assertEquals(true, updatedPostOutput.isVoteUp());
	}
	
	@Test
	void getPostById() {
		
		// Creating Post Object
		Post newPost = new Post();
		
		// Setting the values
		newPost.setPostId(100);
		newPost.setTitle("Post Demo");
		newPost.setContent(PostType.VIDEO_IMAGE);
		newPost.setCreatedDateTime(LocalDateTime.now());
		newPost.setFlair("post");
		newPost.setNotSafeForWork(false);
		newPost.setOriginalContent(true);
		newPost.setVotes(10000);
		newPost.setVoteUp(false);
		newPost.setSpoiler(true);
		
		// Sending post object when save function is called
		Mockito.when(postRepo.findById(100)).thenReturn(Optional.of(newPost));
		
		PostOutputDto addedPost = postServ.getPostById(100);	

		// checking if the added post values are equal to the post or not
		assertEquals(100, addedPost.getPostId());
		assertEquals("Post Demo", addedPost.getTitle());
		assertEquals(PostType.VIDEO_IMAGE, addedPost.getContent());
		assertEquals("ost", addedPost.getFlair());
		assertEquals(10000, addedPost.getVotes());
		assertEquals(false, addedPost.isNotSafeForWork());
		assertEquals(true, addedPost.isOriginalContent());
		assertEquals(true, addedPost.isSpoiler());
		assertEquals(false, addedPost.isVoteUp());
	}
	
	@Test
	void getAllPostsTest() {
		
		//Creating List of posts
		List<Post> posts = new ArrayList<>();
		
		// Creating Post objects
		Post post1 = new Post();
		
		// Setting the post values
		post1.setPostId(1);
		post1.setTitle("Post Test 1");
		post1.setContent(PostType.POLL);
		post1.setCreatedDateTime(LocalDateTime.now());
		post1.setFlair("post1");
		post1.setNotSafeForWork(false);
		post1.setOriginalContent(true);
		post1.setVotes(10);
		post1.setVoteUp(true);
		post1.setSpoiler(false);
		
		Post post2 = new Post();
		
		// Setting the post values
		post2.setPostId(2);
		post2.setTitle("Post Test 2");
		post2.setContent(PostType.TEXT);
		post2.setCreatedDateTime(LocalDateTime.now());
		post2.setFlair("post2");
		post2.setNotSafeForWork(false);
		post2.setOriginalContent(true);
		post2.setVotes(100);
		post2.setVoteUp(true);
		post2.setSpoiler(false);
		
		// Adding Post1 and Post2 to the list
		posts.add(post1);
		posts.add(post2);
		
		// Sending posts when findAll() is called
		Mockito.when(postRepo.findAll()).thenReturn(posts);
		
		// Calling the postServ function
		List<PostOutputDto> allPosts = postServ.getAllPosts();
		
		//Comparing the number of posts
		assertEquals(2, allPosts.size());
		
	}
	
	@Test
	void getAllPostsBySearchStringTest() {
		
		//Creating List of posts
		List<Post> posts = new ArrayList<>();
		
		// Creating Post objects
		Post post1 = new Post();
		
		// Setting the post values
		post1.setPostId(1);
		post1.setTitle("Post Test 1");
		post1.setContent(PostType.POLL);
		post1.setCreatedDateTime(LocalDateTime.now());
		post1.setFlair("post1");
		post1.setNotSafeForWork(false);
		post1.setOriginalContent(true);
		post1.setVotes(10);
		post1.setVoteUp(true);
		post1.setSpoiler(false);
		
		Post post2 = new Post();
		
		// Setting the post values
		post2.setPostId(2);
		post2.setTitle("Post Test 2");
		post2.setContent(PostType.TEXT);
		post2.setCreatedDateTime(LocalDateTime.now());
		post2.setFlair("post2");
		post2.setNotSafeForWork(false);
		post2.setOriginalContent(true);
		post2.setVotes(100);
		post2.setVoteUp(true);
		post2.setSpoiler(false);
		
		// Adding Post1 and Post2 to the list
		posts.add(post1);
		posts.add(post2);
		
		// Sending posts when getPostsBySearchString() is called
		Mockito.when(postRepo.getPostBySearchString("%Test%")).thenReturn(posts);
		
		// Calling the postServ function
		List<PostOutputDto> allPosts = postServ.getPostBySearchString("Test");
		
		//Comparing the number of posts
		assertEquals(2, allPosts.size());
		
	}
	
	@Test
	void getAllPostsOfAwardTest() {
		
		//Creating List of posts
		List<Post> posts = new ArrayList<>();
		
		// Creating Post objects
		Post post1 = new Post();
		
		// Setting the post values
		post1.setPostId(1);
		post1.setTitle("Post Test 1");
		post1.setContent(PostType.POLL);
		post1.setCreatedDateTime(LocalDateTime.now());
		post1.setFlair("post1");
		post1.setNotSafeForWork(false);
		post1.setOriginalContent(true);
		post1.setVotes(10);
		post1.setVoteUp(true);
		post1.setSpoiler(false);
		
		Post post2 = new Post();
		
		// Setting the post values
		post2.setPostId(2);
		post2.setTitle("Post Test 2");
		post2.setContent(PostType.TEXT);
		post2.setCreatedDateTime(LocalDateTime.now());
		post2.setFlair("post2");
		post2.setNotSafeForWork(false);
		post2.setOriginalContent(true);
		post2.setVotes(100);
		post2.setVoteUp(true);
		post2.setSpoiler(false);
		
		// Adding Post1 and Post2 to the list
		posts.add(post1);
		posts.add(post2);
		
		// Sending posts when getPostsBySearchString() is called
		Mockito.when(postRepo.getAllPostsByAwardId(103)).thenReturn(posts);
		
		// Calling the postServ function
		List<PostOutputDto> allPosts = postServ.getPostByAwardId(103);
		
		//Comparing the number of posts
		assertEquals(2, allPosts.size());
		
	}
	
	@Test
	void getPostsByBloggerTest() {
		
		//Creating List of posts
		List<Post> posts = new ArrayList<>();
		
		// Creating Post objects
		Post post1 = new Post();
		
		// Setting the post values
		post1.setPostId(1);
		post1.setTitle("Post Test 1");
		post1.setContent(PostType.POLL);
		post1.setCreatedDateTime(LocalDateTime.now());
		post1.setFlair("post1");
		post1.setNotSafeForWork(false);
		post1.setOriginalContent(true);
		post1.setVotes(10);
		post1.setVoteUp(true);
		post1.setSpoiler(false);
		
		Post post2 = new Post();
		
		// Setting the post values
		post2.setPostId(2);
		post2.setTitle("Post Test 2");
		post2.setContent(PostType.TEXT);
		post2.setCreatedDateTime(LocalDateTime.now());
		post2.setFlair("post2");
		post2.setNotSafeForWork(false);
		post2.setOriginalContent(true);
		post2.setVotes(100);
		post2.setVoteUp(true);
		post2.setSpoiler(false);
		
		// Adding Post1 and Post2 to the list
		posts.add(post1);
		posts.add(post2);
		
		// Sending posts when getPostsBySearchString() is called
		Mockito.when(postRepo.getPostsByBlogger(101)).thenReturn(posts);
		
		// Calling the postServ function
		List<PostOutputDto> allPosts = postServ.getPostsByBlogger(101);
		
		//Comparing the number of posts
		assertEquals(2, allPosts.size());
	}
	
	@Test
	void getPostsByCommunityTest() {
		
		//Creating List of posts
		List<Post> posts = new ArrayList<>();
		
		// Creating Post objects
		Post post1 = new Post();
		
		// Setting the post values
		post1.setPostId(1);
		post1.setTitle("Post Test 1");
		post1.setContent(PostType.POLL);
		post1.setCreatedDateTime(LocalDateTime.now());
		post1.setFlair("post1");
		post1.setNotSafeForWork(false);
		post1.setOriginalContent(true);
		post1.setVotes(10);
		post1.setVoteUp(true);
		post1.setSpoiler(false);
		
		Post post2 = new Post();
		
		// Setting the post values
		post2.setPostId(2);
		post2.setTitle("Post Test 2");
		post2.setContent(PostType.TEXT);
		post2.setCreatedDateTime(LocalDateTime.now());
		post2.setFlair("post2");
		post2.setNotSafeForWork(false);
		post2.setOriginalContent(true);
		post2.setVotes(100);
		post2.setVoteUp(true);
		post2.setSpoiler(false);
		
		// Adding Post1 and Post2 to the list
		posts.add(post1);
		posts.add(post2);
		
		// Sending posts when getPostsBySearchString() is called
		Mockito.when(postRepo.getAllPostsByCommunityId(102)).thenReturn(posts);
		
		// Calling the postServ function
		List<PostOutputDto> allPosts = postServ.listPostsByCommunityId(102);
		
		//Comparing the number of posts
		assertEquals(2, allPosts.size());
	}
	
	@Test
	void getAllUpvotedPostsOfBlogger() {
		
		//Creating List of posts
		List<Post> posts = new ArrayList<>();
		
		// Creating Post objects
		Post post1 = new Post();
		
		// Setting the post values
		post1.setPostId(1);
		post1.setTitle("Post Test 1");
		post1.setContent(PostType.POLL);
		post1.setCreatedDateTime(LocalDateTime.now());
		post1.setFlair("post1");
		post1.setNotSafeForWork(false);
		post1.setOriginalContent(true);
		post1.setVotes(10);
		post1.setVoteUp(true);
		post1.setSpoiler(false);
		
		Post post2 = new Post();
		
		// Setting the post values
		post2.setPostId(2);
		post2.setTitle("Post Test 2");
		post2.setContent(PostType.TEXT);
		post2.setCreatedDateTime(LocalDateTime.now());
		post2.setFlair("post2");
		post2.setNotSafeForWork(false);
		post2.setOriginalContent(true);
		post2.setVotes(100);
		post2.setVoteUp(true);
		post2.setSpoiler(false);
		
		// Adding Post1 and Post2 to the list
		posts.add(post1);
		posts.add(post2);
		
		// Sending posts when getPostsBySearchString() is called
		Mockito.when(postRepo.getUpvotedPostsOfBlogger(101)).thenReturn(posts);
		
		// Calling the postServ function
		List<PostOutputDto> allPosts = postServ.getUpvotedPostsOfBlogger(101);
		
		//Comparing the number of posts
		assertEquals(2, allPosts.size());
	}

}
