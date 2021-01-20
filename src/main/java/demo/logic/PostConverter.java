package demo.logic;

import org.springframework.stereotype.Component;

import demo.boundary.PostBoundary;
import demo.boundary.ProductBoundary;
import demo.boundary.UserBoundary;
import demo.data.PostEntity;
import demo.data.ProductEntity;
import demo.data.UserEntity;
@Component
public class PostConverter {
	public PostBoundary postToBoundary(PostEntity entity) {
		
		return new PostBoundary(
				userToBoudary(entity.getUser()),
				productToBoudary(entity.getProduct()),
				entity.getPostingTimestamp(),
				entity.getLanguage(),
				entity.getPostContent());
	}
	
	public PostEntity postToEntity(PostBoundary boundary) {
		return new PostEntity(
				null,
				userToEntity(boundary.getUser()),
				productToEntity(boundary.getProduct()),
				boundary.getPostingTimestamp(),
				boundary.getLanguage(),
				boundary.getPostContent());
	}

	public ProductEntity productToEntity(ProductBoundary pb) {
		return new ProductEntity(pb.getId());
	}
	
	public ProductBoundary productToBoudary(ProductEntity pe) {
		return new ProductBoundary(pe.getId());
	}
	
	public UserEntity userToEntity(UserBoundary ub) {
		return new UserEntity(ub.getEmail());
	}
	
	public UserBoundary userToBoudary(UserEntity ue) {
		return new UserBoundary(ue.getEmail());
	}
}
