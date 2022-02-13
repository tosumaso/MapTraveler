package MapTraveler.develop.Service;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import MapTraveler.develop.Entity.Comment;
import MapTraveler.develop.Repository.CommentRepository;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

	@Mock
	CommentRepository commentRepository;
	
	@InjectMocks
	CommentServiceImpl service;

	@Test
	void findAll() {
		List<Comment> comments = new ArrayList<>() {
			{
				add(new Comment());
				add(new Comment());
				add(new Comment());
			}
		};
		
		when(commentRepository.findAll()).thenReturn(comments);
		List<Comment> returnedComments = service.findAll();
		assertThat(returnedComments).hasSize(3);
		assertThat(returnedComments).isNotNull();
		verify(commentRepository).findAll();
	}

}
