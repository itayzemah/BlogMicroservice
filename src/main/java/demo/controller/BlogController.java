	package demo.controller;

import javax.validation.constraints.Email;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.boundary.PostBoundary;
import demo.data.SortOrder;
import demo.logic.BlogService;

@RestController
@RequestMapping(path="/blog")
public class BlogController {
	private BlogService blogService;
	
	
	public BlogController(BlogService blogService) {
		super();
		this.blogService = blogService;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public PostBoundary postBlog(@RequestBody PostBoundary postBoundary){
		return this.blogService.create(postBoundary);
	}
	
	@RequestMapping(path = "/byUser/{email}", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public PostBoundary[] getBlogsByUser(@PathVariable("email") @Email String email, 
			@RequestParam(name = "filterType", required = false, defaultValue = "") String filterType,
			@RequestParam(name = "filterValue", required = false, defaultValue = "") String filterValue,
			@RequestParam(name = "sortBy", required = false, defaultValue = "postingTimestamp") String sortAttribute,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") SortOrder sortOrder,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "1000") int size){
				
		return this.blogService.getPostsByUser(email, filterType, filterValue, sortAttribute, sortOrder.equals(SortOrder.ASC),page,size);
	}
	
	@RequestMapping(path = "/byProduct/{productId}", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public PostBoundary[] getBlogsByProductId(@PathVariable("productId") String productId, 
			@RequestParam(name = "filterType", required = false, defaultValue = "") String filterType,
			@RequestParam(name = "filterValue", required = false, defaultValue = "") String filterValue,
			@RequestParam(name = "sortBy", required = false, defaultValue = "postingTimestamp") String sortAttribute,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") SortOrder sortOrder,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "100") int size){
				
		return this.blogService.
				getPostsByProduct(productId, filterType, filterValue, sortAttribute,
						sortOrder.equals(SortOrder.ASC),page,size);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public PostBoundary[] getBlogs(
			@RequestParam(name = "filterType", required = false, defaultValue = "byCreation") String filterType,
			@RequestParam(name = "filterValue", required = false, defaultValue = "lastDay") String filterValue,
			@RequestParam(name = "sortBy", required = false, defaultValue = "postingTimestamp") String sortAttribute,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") SortOrder sortOrder,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "100") int size){
				
		return this.blogService.getAllPosts(filterType, filterValue, sortAttribute, sortOrder.equals(SortOrder.ASC),page,size);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void removeAll() {
		this.blogService.removeAll();
	}
}
