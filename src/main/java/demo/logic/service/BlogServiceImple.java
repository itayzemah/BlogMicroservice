package demo.logic.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.Filter;
import org.springframework.stereotype.Service;

import demo.boundary.PostBoundary;
import demo.controller.BlogController;
import demo.data.PostEntity;
import demo.data.SortOrder;
import demo.data.dal.BlogDataAccessRepository;
import demo.logic.BlogService;
import demo.logic.PostConverter;
import demo.logic.TimeEnum;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BlogServiceImple implements BlogService {

	private BlogDataAccessRepository blogDal;
	private PostConverter converter;

	public BlogServiceImple(BlogDataAccessRepository blogDal, PostConverter converter) {
		super();
		this.blogDal = blogDal;
		this.converter = converter;
	}

	public Mono<PostBoundary> create(PostBoundary post) {
		return Mono.just(post) // Mono<PostBoundary>

				.map(boundary -> {
					boundary.setPostingTimestamp(new Date());
					return boundary;
				}) // Mono<PostBoundary>

				.map(boundary -> this.converter.postToEntity(boundary)) // Mono<PostBoundary>

				.flatMap(entity -> this.blogDal.save(entity)) // Mono<PostEntity>

				.map(this.converter::postToBoundary); // Mono<PostBoundary>

	}
//	 public Flux<PostBoundary> getPostsByUser(String userEmail, String sortAttribute, SortOrder order){
//			 return this.blogDal.findAllByUser_Email(userEmail, Sort.by(order == SortOrder.ASC ? Direction.ASC : Direction.DESC, sortAttribute))
//					 .map(this.converter::postToBoundary);
//	 }

	@Override
	public Flux<PostBoundary> getPostsByUser(String email, String filterType, String filterValue, String sortAttribute,
			boolean asc) {
		switch (filterType) {
		case "byLanguage":
			return this.blogDal.findAllByUser_EmailAndLanguage(email, filterValue, Sort.by(getSortOrderFromEnum(asc),sortAttribute))
//					.distinct(post ->(post) {
//						return 
//					})
					.map(this.converter::postToBoundary);
		case "byCreation":
			return this.blogDal.findAllByUser_EmailAndPostingTimestampGreaterThanEqual(email, getFilterDate(filterValue), Sort.by(getSortOrderFromEnum(asc),sortAttribute))
//					.distinct(post ->(post) {
//						return 
//					})
					.map(this.converter::postToBoundary);
		case "byProduct":
			return this.blogDal.findAllByUser_EmailAndProduct_Id(email, filterValue, Sort.by(getSortOrderFromEnum(asc),sortAttribute))
					.map(this.converter::postToBoundary);

			default:
		return this.blogDal.findAllByUser_Email(email, Sort.by(getSortOrderFromEnum(asc),sortAttribute))
//				.distinct(post ->(post) {
//					return 
//				})
				.map(this.converter::postToBoundary);
		}
	}


	@Override
	public Flux<PostBoundary> getPostsByProduct(String productId, String filterType, String filterValue,
			String sortAttribute, boolean asc) {
		Flux<PostEntity> rv = null;
		switch (filterType) {
		case "byLanguage":
			rv= this.blogDal.findAllByProduct_IdAndLanguage(productId, filterValue, Sort.by(getSortOrderFromEnum(asc),sortAttribute));
			break;
		case "byCreation":
			rv = this.blogDal.findAllByProduct_IdAndPostingTimestampGreaterThanEqual(productId,  getFilterDate(filterValue), Sort.by(getSortOrderFromEnum(asc),sortAttribute));
			break;
		default:
			rv = this.blogDal.findAllByProduct_Id(productId,Sort.by(getSortOrderFromEnum(asc),sortAttribute));
		}
		return rv.map(this.converter::postToBoundary);
	}

	@Override
	public Flux<PostBoundary> getAllPosts(String filterType, String filterValue, String sortAttribute, boolean asc) {
		Flux<PostEntity> rv = null;
		switch (filterType) {
			case "byCreation":
				rv = this.blogDal.findAllByPostingTimestampGreaterThanEqual(getFilterDate(filterValue), Sort.by(getSortOrderFromEnum(asc),sortAttribute));
				break;
			case "byCount":
				rv = this.blogDal.findAll(Sort.by(Direction.DESC))
				.limitRequest(Integer.parseInt(filterValue)).sort((o1, o2) -> o1.getPostingTimestamp().compareTo(o2.getPostingTimestamp()) );
				break;

		}

		return rv.map(this.converter::postToBoundary);
	}

	@Override
	public Mono<Void> removeAll() {
		return this.blogDal.deleteAll();
	}

	private Date getFilterDate(String filterValue) {
		return Date.from(LocalDate.now().minusDays(TimeEnum.fromString(filterValue).daysValue).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	private Direction getSortOrderFromEnum(boolean order) {
		return order == true ? Direction.ASC : Direction.DESC;
	}
}

/*
 * 
 * public Flux<PostBoundary> getPostsById(String Id,String filterType, String
 * filterValue, String sortAttribute, String order);
 * 
 * public Flux<PostBoundary> getPosts(String filterType, String filterValue,
 * String sortAttribute, String order);
 * 
 */
