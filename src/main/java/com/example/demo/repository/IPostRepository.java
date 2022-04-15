package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.example.demo.bean.Post;

@Repository
public interface IPostRepository extends JpaRepository<Post, Integer> {
	
	// JPQL Query to find posts using LIKE operator
	@Query(value = "SELECT * FROM post where title LIKE :searchStr", nativeQuery = true)
	public List<Post> getPostBySearchString(@Param("searchStr") String searchStr);
	
	// JPQL Query to get posts of Blogger
	@Query(value = " SELECT * FROM post p join blogger b on p.blogger_id=b.blogger_id where b.blogger_id=:blogger_id", nativeQuery = true)
	public List<Post> getPostsByBlogger(@Param("blogger_id") int bloggerId);
	
	// JPQL Query to get all posts with award id
	@Query(value = "select p.* from post p join post_awards pa on p.post_id=pa.post_id join award a on pa.award_id= a.award_id where a.award_id=:id", nativeQuery = true)
	public List<Post> getAllPostsByAwardId(@Param("id") int id);
	
	// JPQL Query to get all posts of the community
	@Query(value = "SELECT p.* FROM post p join community c on p.community_id = c.community_id where c.community_id = :communityId", nativeQuery = true)
	public List<Post> getAllPostsByCommunityId(@Param("communityId") int communityId);
	
	// JPQL Query to get all the upvoted posts of blogger
	@Query(value = " SELECT p.* from post p join blogger b on p.blogger_id = b.blogger_id where b.blogger_id = :bloggerId and p.vote_up = true", nativeQuery = true)
	public List<Post> getUpvotedPostsOfBlogger(@Param("bloggerId") int bloggerId);

}
