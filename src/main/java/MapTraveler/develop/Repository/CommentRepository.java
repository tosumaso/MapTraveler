package MapTraveler.develop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import MapTraveler.develop.Entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer>{

}
