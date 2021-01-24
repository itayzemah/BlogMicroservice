package demo.data.dal;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import demo.data.PostEntity;

public interface BlogDataAccessRepository extends MongoRepository<PostEntity, String> {
	
	public List<PostEntity> findAllByUser_Email(@Param("email") String email, Pageable sort);
	
	public List<PostEntity> findAllByUser_EmailAndLanguage(@Param("email") String email, @Param("language") String language, Pageable sort);

	public List<PostEntity> findAllByUser_EmailAndProduct_Id(@Param("email") String email, @Param("productId") String productId, Pageable sort);
	
	public List<PostEntity> findAllByUser_EmailAndPostingTimestampGreaterThanEqual(@Param("email") String email, @Param("postingTimestamp") Date postingTimestamp, Pageable sort);
	
	public List<PostEntity> findAllByProduct_Id(@Param("id") String id, Pageable sort);
	
	public List<PostEntity> findAllByProduct_IdAndLanguage(@Param("id") String id, @Param("language") String language, Pageable sort);
	
	public List<PostEntity> findAllByProduct_IdAndPostingTimestampGreaterThanEqual(@Param("id") String id, @Param("postingTimestamp") Date postingTimestamp, Pageable sort);

	public List<PostEntity> findAllByPostingTimestampGreaterThanEqual(@Param("postingTimestamp") Date postingTimestamp, Pageable sort);
	

}