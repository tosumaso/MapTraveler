package MapTraveler.develop.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MapTraveler.develop.Entity.Comment;
import MapTraveler.develop.Repository.CommentRepository;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{

	@Autowired
	CommentRepository commentRepository;

	@Override
	public List<Comment> findAll() {
		List<Comment> comments = commentRepository.findAll();
		return comments;
	}
}
