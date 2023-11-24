package com.blog.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.config.AppConstants;
import com.blog.app.payloads.ApiResponse;
import com.blog.app.payloads.PostDto;
import com.blog.app.payloads.PostResponse;
import com.blog.app.services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	//Create Post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, 
							@PathVariable Integer userId, 
							@PathVariable Integer categoryId){
		
		PostDto createdPost = postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	
	}
	
	//Update Post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
		PostDto updatedPost = postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
	
	//Delete Post
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully !!", true), HttpStatus.OK);
	}
	
	//Get All Post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
													@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
													@RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection){
		
		PostResponse postResponse = postService.getAllPosts(pageNumber, sortBy, sortDirection);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
		
	}
	
	//Get Post by PostId
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto fetchedPost = postService.getPostById(postId);
		return new ResponseEntity<PostDto>(fetchedPost, HttpStatus.OK);
	}
	
	//Get Posts by Category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
															@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
															@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
															@RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection){
		PostResponse listOfFetchedPost = postService.getPostsByCategory(categoryId, pageNumber, sortBy, sortDirection);
		return new ResponseEntity<PostResponse>(listOfFetchedPost, HttpStatus.OK);
	}
	
	//Get Posts by User
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
														@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
														@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
														@RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection){
		PostResponse listOfFetchedPost = postService.getPostsByUser(userId, pageNumber, sortBy, sortDirection);
		return new ResponseEntity<PostResponse>(listOfFetchedPost, HttpStatus.OK);
	}
	
	//Get Posts by Searching keyword
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<PostResponse> getPostsBySearch(@PathVariable String keyword,
														@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
														@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
														@RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection){
		PostResponse listOfFetchedPost = postService.getPostsBySearch(keyword, pageNumber, sortBy, sortDirection);
		return new ResponseEntity<PostResponse>(listOfFetchedPost, HttpStatus.OK);
	} 
}
