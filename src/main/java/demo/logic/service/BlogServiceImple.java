package demo.logic.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import demo.boundary.PostBoundary;
import demo.data.PostEntity;
import demo.data.dal.BlogDataAccessRepository;
import demo.logic.BlogService;
import demo.logic.PostConverter;
import demo.logic.TimeEnum;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BlogServiceImple implements BlogService {

	private BlogDataAccessRepository blogDal;
	private PostConverter converter;
	
	public PostBoundary create(PostBoundary post) {
		post.setPostingTimestamp(new Date());

		PostEntity entity = this.converter.postToEntity(post);

		entity = this.blogDal.save(entity);

		return this.converter.postToBoundary(entity);

	}

	@Override
	public PostBoundary[] getPostsByUser(String email, String filterType, String filterValue, String sortAttribute,
			boolean asc, int page, int size) {
		PostBoundary[] rv = null;
		List<PostEntity> entity = null;
		switch (filterType) {
		case "byLanguage":
			entity = this.blogDal.findAllByUser_EmailAndLanguage(email, filterValue,
					PageRequest.of(page, size, Sort.by(getSortOrderFromEnum(asc), sortAttribute)));
			break;
		case "byCreation":
			entity = this.blogDal.findAllByUser_EmailAndPostingTimestampGreaterThanEqual(email,
					getFilterDate(filterValue),
					PageRequest.of(page, size, Sort.by(getSortOrderFromEnum(asc), sortAttribute)));
			break;
		case "byProduct":
			entity = this.blogDal.findAllByUser_EmailAndProduct_Id(email, filterValue,
					PageRequest.of(page, size, Sort.by(getSortOrderFromEnum(asc), sortAttribute)));
			break;
		default:
			entity = this.blogDal.findAllByUser_Email(email,
					PageRequest.of(page, size, Sort.by(getSortOrderFromEnum(asc), sortAttribute)));
			break;
		}
		rv = streamToPostBoundaryArray(entity);
		return rv;
	}

	@Override
	public PostBoundary[] getPostsByProduct(String productId, String filterType, String filterValue,
			String sortAttribute, boolean asc, int page, int size) {
		List<PostEntity> entity = null;

		switch (filterType) {
		case "byLanguage":
			entity = this.blogDal.findAllByProduct_IdAndLanguage(productId, filterValue,
					PageRequest.of(page, size, Sort.by(getSortOrderFromEnum(asc), sortAttribute)));
			break;
		case "byCreation":
			entity = this.blogDal.findAllByProduct_IdAndPostingTimestampGreaterThanEqual(productId,
					getFilterDate(filterValue), PageRequest.of(page, size, Sort.by(getSortOrderFromEnum(asc), sortAttribute)));
			break;
		default:
			entity = this.blogDal.findAllByProduct_Id(productId, PageRequest.of(page, size, Sort.by(getSortOrderFromEnum(asc), sortAttribute)));
		}
		return streamToPostBoundaryArray(entity);
	}

	private PostBoundary[] streamToPostBoundaryArray(List<PostEntity> entity) {
		return entity.stream().map(this.converter::postToBoundary).collect(Collectors.toList())
				.toArray(new PostBoundary[0]);
	}

	@Override
	public PostBoundary[] getAllPosts(String filterType, String filterValue, String sortAttribute, boolean asc, int page, int size) {
		List<PostEntity> rv = null;

		switch (filterType) {
		case "byCreation":
			rv = this.blogDal.findAllByPostingTimestampGreaterThanEqual(getFilterDate(filterValue),
					PageRequest.of(page, size, Sort.by(getSortOrderFromEnum(asc), sortAttribute)));
			break;
		case "byCount":
			rv = this.blogDal.findAllByPostingTimestampLessThanEqual(new Date(), PageRequest.of(0, Integer.parseInt(filterValue), Sort.by(Direction.DESC , "postingTimestamp")));
			break;

		}

		return streamToPostBoundaryArray(rv);
	}

	@Override
	public void removeAll() {
		this.blogDal.deleteAll();
	}

	private Date getFilterDate(String filterValue) {
		return Date.from(LocalDate.now().minusDays(TimeEnum.fromString(filterValue).daysValue).atStartOfDay()
				.atZone(ZoneId.systemDefault()).toInstant());
	}

	private Direction getSortOrderFromEnum(boolean order) {
		return order == true ? Direction.ASC : Direction.DESC;
	}
}
