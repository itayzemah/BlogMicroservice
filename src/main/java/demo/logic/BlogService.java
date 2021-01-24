package demo.logic;

import demo.boundary.PostBoundary;

public interface BlogService {

	public PostBoundary create(PostBoundary post);

	public PostBoundary[] getPostsByUser(String email, String filterType, String filterValue, String sortAttribute,
			boolean asc, int page, int size);

	public PostBoundary[] getPostsByProduct(String productId, String filterType, String filterValue,
			String sortAttribute, boolean asc, int page, int size);

	public PostBoundary[] getAllPosts(String filterType, String filterValue, String sortAttribute, boolean asc,
			int page, int size);

	public void removeAll();

}
