package demo.boundary;

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/*
{
  "user":{
    "email":"customerNumber1@shop.ping"
  }, 
  "product":{
    "id":"38996"
  },  
  "postingTimestamp":"2020-12-10T04:31:44.739+0000", 
  "language":"en", 
  "postContent":{
    "image":"http://image.im/product38996.jpg", 
    "message":"This product changed my life!", 
    "details":{
      "line1":"The fire consumed my apartment building",
      "line2":"I had to move to a shelter"
    }, 
    "references":[
      "https://newscase.org/firebrokeduetomulfunctioninproduct", 
      "http://www.cnn.com", 
      "http://demoservice.de.mo/story?stodyId=985645211596037"
    ]
  }
}
*/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostBoundary {
	private @NonNull UserBoundary user;
	private @NonNull ProductBoundary product;
	private @NonNull Date postingTimestamp;
	private @NonNull String language;
	private Map<String, Object> postContent;
}
