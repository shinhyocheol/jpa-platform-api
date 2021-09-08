package kr.co.platform.api.posts.controller;


import kr.co.platform.api.posts.dto.RegistPostsDto;
import kr.co.platform.api.posts.dto.PostsResDto;
import kr.co.platform.api.posts.dto.RegistCommentDto;
import kr.co.platform.api.posts.dto.ModifyPostsDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import kr.co.platform.api.posts.service.PostsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = {"/posts"}, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PostsController {

	private final PostsService postsService;
	
	/**
	 * @method 설명 : 게시글 목록조회
	 * @param page
	 * @return
	 */
	@GetMapping(value = {""})
	public ResponseEntity<PageImpl<PostsResDto>> getPosts(
			@RequestParam Integer page) {
		
		PageRequest pageble = PageRequest.of(page - 1, 8, Sort.by("id").descending());
		
		return ResponseEntity.ok()
				 			 .body(postsService.getPosts(pageble));
	}

	/**
	 * @method 설명 : 게시글 등록
	 * @param regPosts
	 * @throws Exception
	 */
	@PostMapping(value = {""})
	public ResponseEntity<Long> regPosts(
			@Valid @RequestBody RegistPostsDto regPosts) throws Exception {

		return ResponseEntity.ok() 
							 .body(postsService.regPosts(regPosts));
	}
	
	/**
	 * @method 설명 : 게시글 상세조회
	 * @param id
	 * @return
	 */
	@GetMapping(value = {"/{id}"})
	public ResponseEntity<PostsResDto> getPostsDetail(
			@PathVariable Long id) {

		return ResponseEntity.ok()
							 .body(postsService.getPostsById(id));
	}
	
	/**
	 * @method 설명 : 게시글 수정
	 * @param id
	 * @param setPosts
	 * @throws Exception
	 */
	@PutMapping(value = {"/{id}"})
	public ResponseEntity<String> setPosts(
			@PathVariable Long id,
			@Valid @RequestBody ModifyPostsDto setPosts) throws Exception {

		postsService.setPosts(setPosts);

		return ResponseEntity.ok()
							 .body("UPDATE SUCCESS");
	}
	
	/**
	 * @method 설명 : 게시글 삭제
	 * @param id
	 * @throws Exception
	 */
	@DeleteMapping(value = {"/{id}"})
	public ResponseEntity<String> delPosts(
			@PathVariable Long id) throws Exception {

		postsService.delPosts(id);

		return ResponseEntity.ok()
							 .body("DELETE SUCCESS");
	}
	
	/**
	 * @method 설명 : 게시글 댓글 등록
	 * @param postsId
	 * @param regComment
	 * @throws Exception
	 */
	@PostMapping(value = {"/{postsId}/comment"})
	public void regCommentByPosts(
			@PathVariable Long postsId,
			@Valid @RequestBody RegistCommentDto regComment) throws Exception {
		
		postsService.regCommentByPosts(regComment);
	}
	
}
