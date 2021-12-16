package MapTraveler.develop.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import MapTraveler.develop.Entity.Post;

public interface PostRepository extends JpaRepository<Post,Integer>{
	
	
	@Query("SELECT p FROM Post p "
			+ "left join fetch p.user "
			+ "left join fetch p.map WHERE p.title LIKE %:keyword%")
	List<Post> findByKeywordLike(@Param("keyword") String keyword);
	

	@Query("select distinct p from Post p left join fetch p.images where p.user.id = :user")
	List<Post> findByUser(Integer user);
	
	
}
