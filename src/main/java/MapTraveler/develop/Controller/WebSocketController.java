package MapTraveler.develop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import MapTraveler.develop.Entity.Comment;
import MapTraveler.develop.Form.WebsocketForm;
import MapTraveler.develop.Repository.CommentRepository;
import MapTraveler.develop.Repository.PostRepository;
import MapTraveler.develop.Repository.UserRepository;

@Controller
public class WebSocketController {

	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@MessageMapping("/hello")
	@SendTo("/big/greetings")
	public Comment greeting(WebsocketForm form) throws Exception{
		Thread.sleep(1000);
		Comment savedComment = new Comment();
		savedComment.setContent(form.getContent());
		savedComment.setUser(userRepository.findById(form.getUserId()).get());
		savedComment.setPost(postRepository.findById(form.getPostId()).get());
		commentRepository.save(savedComment);
		return savedComment; 
	}
}