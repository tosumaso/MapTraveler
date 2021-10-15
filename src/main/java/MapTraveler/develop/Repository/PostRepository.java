package MapTraveler.develop.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import MapTraveler.develop.Entity.Post;

public interface PostRepository extends JpaRepository<Post,Integer>{
	
	
	@Query(value="SELECT p FROM Post p WHERE p.title LIKE %:keyword%")
	List<Post> findByKeywordLike(@Param("keyword") String keyword);
	
	@EntityGraph(
			type=EntityGraphType.FETCH,
			attributePaths = {
			"images", "texts", "user", "map"
			}
			)
	Optional<Post> findById(Integer id);
}
