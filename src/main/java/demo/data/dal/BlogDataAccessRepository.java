package demo.data.dal;

import java.util.Date;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import demo.data.PostEntity;
import reactor.core.publisher.Flux;

public interface BlogDataAccessRepository extends ReactiveSortingRepository<PostEntity, String> {
	
	public Flux<PostEntity> findAllByUser_Email(@Param("email") String email, Sort sort);
	
	public Flux<PostEntity> findAllByUser_EmailAndLanguage(@Param("email") String email, @Param("language") String language, Sort sort);

	public Flux<PostEntity> findAllByUser_EmailAndProduct_Id(@Param("email") String email, @Param("productId") String productId, Sort sort);
	
	public Flux<PostEntity> findAllByUser_EmailAndPostingTimestampGreaterThanEqual(@Param("email") String email, @Param("postingTimestamp") Date postingTimestamp, Sort sort);
	
	public Flux<PostEntity> findAllByProduct_Id(@Param("id") String id, Sort sort);
	
	public Flux<PostEntity> findAllByProduct_IdAndLanguage(@Param("id") String id, @Param("language") String language, Sort sort);
	
	public Flux<PostEntity> findAllByProduct_IdAndPostingTimestampGreaterThanEqual(@Param("id") String id, @Param("postingTimestamp") Date postingTimestamp, Sort sort);

	public Flux<PostEntity> findAllByPostingTimestampGreaterThanEqual(@Param("postingTimestamp") Date postingTimestamp, Sort sort);
	

}