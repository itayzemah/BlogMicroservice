package demo.data;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "Posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostEntity {
	private @Id String id;
	private UserEntity user;
	private ProductEntity product;
	private @CreatedDate Date postingTimestamp;
	private String language;
	private Map<String, Object> postContent;
}
