package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.bean.Award;
import com.example.demo.bean.Blogger;
import com.example.demo.bean.Community;
import com.example.demo.bean.Post;
import com.example.demo.bean.Moderator;
import com.example.demo.dto.PostInputDto;
import com.example.demo.dto.PostOutputDto;
import com.example.demo.exception.IdNotFoundException;
import com.example.demo.repository.IPostRepository;
import com.example.demo.repository.IAwardRepository;
import com.example.demo.repository.IBloggerRepository;
import com.example.demo.exception.ModeratorApprovalException;
import com.example.demo.repository.ICommunityRepository;
@Service
public class PostServiceImpl implements IPostService {
	
	@Autowired
	IPostRepository postRepo;
	
	@Autowired
	ICommunityRepository commRepo;
	
	@Autowired
	IAwardRepository awardRepo;
	
	@Autowired
	IBloggerRepository blogRepo;
	
	// Creating Moderator object
	Moderator moderator = new Moderator();
	@Override
	public Post addPostWithoutDto(Post post) {
		return postRepo.save(post);
	}
	@Override
	public Post addPost(PostInputDto post) {
		
		// Checking for Moderator Approval
		if(!moderator.moderatesPostsAndComments(post.isFlag())) {
			throw new ModeratorApprovalException("Post is not approved");
		}
		
		// Creating post object
		Post newPost = new Post();
		
		// Setting post variables by postInputDto values
		newPost.setTitle(post.getTitle());
		newPost.setContent(post.getContent());
		newPost.setCreatedDateTime(post.getCreatedDateTime());
		newPost.setFlair('#' + post.getFlair());
		newPost.setNotSafeForWork(post.isNotSafeForWork());
		newPost.setOriginalContent(post.isOriginalContent());
		newPost.setVotes(post.getVotes());
		newPost.setVoteUp(post.isVoteUp());
		newPost.setSpoiler(post.isSpoiler());
		
		// Setting static blogger to post
		Optional<Blogger> blogger = blogRepo.findById(post.getBloggerId());
		if(!blogger.isPresent()) {
			throw new IdNotFoundException("No blogger found with ID: " + post.getBloggerId());
		}
		newPost.setBlogger(blogger.get());
		
		// Setting static community to post
		Optional<Community> community = commRepo.findById(post.getCommunityId());
		if(!community.isPresent()) {
			throw new IdNotFoundException("No community found with ID: " + post.getCommunityId());
		}
		newPost.setCommunity(community.get());
		
		// List of awards
		List<Award> awards = new ArrayList<>();
		for(Integer awardId : post.getAwardIds()) {
			Optional<Award> award = awardRepo.findById(awardId);
			if(!award.isPresent()) {
				throw new IdNotFoundException("No award found with ID: " + awardId);
			}
			awards.add(award.get());
		}
		
		// Setting static awards to post
		newPost.setAwards(awards);

		// Saving the post in database
		return postRepo.save(newPost);
	}

	@Override
	public Post updatePost(PostInputDto post) {
		
		// Checking for Moderator Approval
		if(!moderator.moderatesPostsAndComments(post.isFlag())) {
			throw new ModeratorApprovalException("Post is not approved");
		}
		
		// Finding the post by id
		Optional<Post> opt = postRepo.findById(post.getPostId());
		if(!opt.isPresent()) {
			 // If post is not present throw an error
			 throw new IdNotFoundException("No post with id: " + post.getPostId());
		}
		// If post is present update the oldPost with new Post
		Post oldPost = opt.get();
	
	    // Assigning values to oldPost
		oldPost.setTitle(post.getTitle());
		oldPost.setContent(post.getContent());
		oldPost.setCreatedDateTime(post.getCreatedDateTime());
		oldPost.setFlair('#' + post.getFlair());
		oldPost.setNotSafeForWork(post.isNotSafeForWork());
		oldPost.setOriginalContent(post.isOriginalContent());
		oldPost.setVotes(post.getVotes());
		oldPost.setVoteUp(post.isVoteUp());
		oldPost.setSpoiler(post.isSpoiler());
		
		// Setting static blogger to post
		Optional<Blogger> blogger = blogRepo.findById(post.getBloggerId());
		if(!blogger.isPresent()) {
			throw new IdNotFoundException("No blogger found with ID: " + post.getBloggerId());
		}
		oldPost.setBlogger(blogger.get());
		
		// Setting static community to post
		Optional<Community> community = commRepo.findById(post.getCommunityId());
		if(!community.isPresent()) {
			throw new IdNotFoundException("No community found with ID: " + post.getCommunityId());
		}
		oldPost.setCommunity(community.get());
		
		// List of awards
		List<Award> awards = new ArrayList<>();
		for(Integer awardId : post.getAwardIds()) {
			Optional<Award> award = awardRepo.findById(awardId);
			if(!award.isPresent()) {
				throw new IdNotFoundException("No award found with ID: " + awardId);
			}
			awards.add(award.get());
		}
		
		// Setting static awards to post
		oldPost.setAwards(awards);
		
		// Saving the post in database
		return postRepo.save(oldPost);
		
	}

	@Override
	public void deletePost(int id) {
		
		// Finding the post by id
		Optional<Post> opt = postRepo.findById(id);
		if(opt.isPresent()) {
			Post deletedPost = opt.get();
			// Calling delete function in postRepo
			postRepo.delete(deletedPost);
		}
		else {
			// If post is not present throw an error
			throw new IdNotFoundException("No post with id: " + id);
		}
		
	}

	@Override
	public List<PostOutputDto> getPostBySearchString(String searchStr) {
		
		// Concatenate % to the string to find all the titles with the including string
		String searchString = '%' + searchStr + '%';
		
		// Creating a list of PostOutputDto
		List<Post> allPosts = postRepo.getPostBySearchString(searchString);
		
		// Checking if posts are empty 
		if(allPosts.isEmpty()) {
			throw new IdNotFoundException("No post with search string: " + searchStr);
		}
		
		// Creating List of posts
		List<PostOutputDto> posts = new ArrayList<>();
		
		for(Post post : allPosts) {
			// Creating PostDto object
			PostOutputDto postDto = new PostOutputDto();
			
			// Setting values for postOutputDto
			postDto.setPostId(post.getPostId());
			postDto.setTitle(post.getTitle());
			postDto.setContent(post.getContent());
			postDto.setCreatedDateTime(post.getCreatedDateTime());
			postDto.setFlair(post.getFlair().substring(1));
			postDto.setNotSafeForWork(post.isNotSafeForWork());
			postDto.setOriginalContent(post.isOriginalContent());
			postDto.setVotes(post.getVotes());
			postDto.setVoteUp(post.isVoteUp());
			postDto.setSpoiler(post.isSpoiler());
			postDto.setAwards(post.getAwards());
			
			posts.add(postDto);
		}
		
		return posts;
	}

	@Override
	public void upVote(int postId, boolean upVote) {
		// Finding the post by id
		Optional<Post> opt = postRepo.findById(postId);
		if(!opt.isPresent()) {
			// If post is not present throw an error
			throw new IdNotFoundException("No post with id: " + postId);
		}
		Post post = opt.get();
		
		// Setting the voteUp variable 
		post.setVoteUp(upVote);
				
		postRepo.save(post);
		
	}
	
	@Override
	public List<PostOutputDto> getPostsByBlogger(int bloggerId) {
		
		List<PostOutputDto> allPosts = new ArrayList<>();
		
		List<Post> posts = postRepo.getPostsByBlogger(bloggerId);
		
		if(posts.isEmpty())
		{
			throw new IdNotFoundException("No post found for the blogger with id: "+ bloggerId);
		}
		
		for(Post post : posts) {
			
			// Creating PostOutputDto object
			PostOutputDto postOutputDto = new PostOutputDto();
			
			// Setting values for postOutputDto
			postOutputDto.setPostId(post.getPostId());
			postOutputDto.setTitle(post.getTitle());
			postOutputDto.setContent(post.getContent());
			postOutputDto.setCreatedDateTime(post.getCreatedDateTime());
			postOutputDto.setVotes(post.getVotes());
			postOutputDto.setVoteUp(post.isVoteUp());
			postOutputDto.setNotSafeForWork(post.isNotSafeForWork());
			postOutputDto.setSpoiler(post.isSpoiler());
			postOutputDto.setOriginalContent(post.isOriginalContent());
			postOutputDto.setFlair(post.getFlair());
			postOutputDto.setAwards(post.getAwards());
			
			allPosts.add(postOutputDto);
		}
		return allPosts;
	}
		
	@Override
	public List<PostOutputDto> getPostByAwardId(int awardId) {
		
		// Getting all the posts with award id
		List<Post> posts = postRepo.getAllPostsByAwardId(awardId);
		
		if(posts.isEmpty())
		{
			throw new IdNotFoundException("No post found for the award with id: "+ awardId);
		}
		
		// Creating a list of postOutputDto object
		List<PostOutputDto> allPosts = new ArrayList<>();
		
		for(Post p : posts) {
			PostOutputDto postOutputDto = new PostOutputDto();
			postOutputDto.setPostId(p.getPostId());
			postOutputDto.setTitle(p.getTitle());
			postOutputDto.setContent(p.getContent());
			postOutputDto.setCreatedDateTime(p.getCreatedDateTime());
			postOutputDto.setVotes(p.getVotes());
			postOutputDto.setVoteUp(p.isVoteUp());
			postOutputDto.setNotSafeForWork(p.isNotSafeForWork());
			postOutputDto.setSpoiler(p.isSpoiler());
			postOutputDto.setOriginalContent(p.isOriginalContent());
			postOutputDto.setFlair(p.getFlair());
			postOutputDto.setAwards(p.getAwards());
			
			allPosts.add(postOutputDto);
		}
		return allPosts;
	}

	public List<PostOutputDto> listPostsByCommunityId(int communityId) {
		
		List<Post> posts = postRepo.getAllPostsByCommunityId(communityId);
		
		//Find whether community has posts or not
		if(posts.isEmpty())
		{
			throw new IdNotFoundException("No post found for the community with id: "+ communityId);
		}
		
		// Creating a list of postOutputDto object
		List<PostOutputDto> allPosts = new ArrayList<>();
		
		for(Post p : posts) {
			
			PostOutputDto postOutputDto = new PostOutputDto();
			
			postOutputDto.setPostId(p.getPostId());
			postOutputDto.setTitle(p.getTitle());
			postOutputDto.setContent(p.getContent());
			postOutputDto.setCreatedDateTime(p.getCreatedDateTime());
			postOutputDto.setVotes(p.getVotes());
			postOutputDto.setVoteUp(p.isVoteUp());
			postOutputDto.setNotSafeForWork(p.isNotSafeForWork());
			postOutputDto.setSpoiler(p.isSpoiler());
			postOutputDto.setOriginalContent(p.isOriginalContent());
			postOutputDto.setFlair(p.getFlair());
			postOutputDto.setAwards(p.getAwards());
			
			allPosts.add(postOutputDto);
		}
		return allPosts;
	}
	
	@Override
	public List<PostOutputDto> getUpvotedPostsOfBlogger(int bloggerId) {

		// Creating a list of postOutputDto
		List<PostOutputDto> allPosts = new ArrayList<>();
		
		List<Post> posts = postRepo.getUpvotedPostsOfBlogger(bloggerId);
		
		if(posts.isEmpty())
		{
			throw new IdNotFoundException("No upvoted post found for the blogger with id: "+ bloggerId);
		}
		
		for(Post post : posts) {
			
			// Creating PostOutputDto object
			PostOutputDto postOutputDto = new PostOutputDto();
			
			// Setting values for postOutputDto
			postOutputDto.setPostId(post.getPostId());
			postOutputDto.setTitle(post.getTitle());
			postOutputDto.setContent(post.getContent());
			postOutputDto.setCreatedDateTime(post.getCreatedDateTime());
			postOutputDto.setFlair(post.getFlair().substring(1));
			postOutputDto.setNotSafeForWork(post.isNotSafeForWork());
			postOutputDto.setOriginalContent(post.isOriginalContent());
			postOutputDto.setVotes(post.getVotes());
			postOutputDto.setVoteUp(post.isVoteUp());
			postOutputDto.setSpoiler(post.isSpoiler());
			postOutputDto.setAwards(post.getAwards());
			
			allPosts.add(postOutputDto);
		}
		return allPosts;
	}
	
	@Override
	public List<PostOutputDto> getAllPosts() {
		
		List<PostOutputDto> allPosts = new ArrayList<>();
		
		for(Post post : postRepo.findAll()) {
			
			// Creating PostOutputDto object
			PostOutputDto postOutputDto = new PostOutputDto();
			
			// Setting values for postOutputDto
			postOutputDto.setPostId(post.getPostId());
			postOutputDto.setTitle(post.getTitle());
			postOutputDto.setContent(post.getContent());
			postOutputDto.setCreatedDateTime(post.getCreatedDateTime());
			postOutputDto.setFlair(post.getFlair().substring(1));
			postOutputDto.setNotSafeForWork(post.isNotSafeForWork());
			postOutputDto.setOriginalContent(post.isOriginalContent());
			postOutputDto.setVotes(post.getVotes());
			postOutputDto.setVoteUp(post.isVoteUp());
			postOutputDto.setSpoiler(post.isSpoiler());
			postOutputDto.setAwards(post.getAwards());
			
			allPosts.add(postOutputDto);
		}
		return allPosts;
	}
	@Override
	public PostOutputDto getPostById(int postId) {
		
		// Get post from Database
		Optional<Post> opt = postRepo.findById(postId);
		if(!opt.isPresent()) {
			// If post is not present throw an error
			throw new IdNotFoundException("No post with id: " + postId);
		}
		Post post = opt.get();
		
		// Creating PostOutputDto object
		PostOutputDto postOutputDto = new PostOutputDto();
		
		// Setting values for postOutputDto
		postOutputDto.setPostId(post.getPostId());
		postOutputDto.setTitle(post.getTitle());
		postOutputDto.setContent(post.getContent());
		postOutputDto.setCreatedDateTime(post.getCreatedDateTime());
		postOutputDto.setFlair(post.getFlair().substring(1));
		postOutputDto.setNotSafeForWork(post.isNotSafeForWork());
		postOutputDto.setOriginalContent(post.isOriginalContent());
		postOutputDto.setVotes(post.getVotes());
		postOutputDto.setVoteUp(post.isVoteUp());
		postOutputDto.setSpoiler(post.isSpoiler());
		postOutputDto.setAwards(post.getAwards());
		
		return postOutputDto;
	}
	

}
