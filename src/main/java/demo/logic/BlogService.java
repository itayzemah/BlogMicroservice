package demo.logic;

import demo.boundary.PostBoundary;
import demo.data.SortOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BlogService {

	public Mono<PostBoundary> create(PostBoundary post);

	public Flux<PostBoundary> getPostsByUser(String email, String filterType, String filterValue, String sortAttribute,
			boolean asc);

	public Flux<PostBoundary> getPostsByProduct(String productId, String filterType, String filterValue,
			String sortAttribute, boolean asc);

	public Flux<PostBoundary> getAllPosts(String filterType, String filterValue, String sortAttribute, boolean asc);

	public Mono<Void> removeAll();

}
