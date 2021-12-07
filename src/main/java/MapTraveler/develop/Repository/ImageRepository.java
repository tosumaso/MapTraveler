package MapTraveler.develop.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import MapTraveler.develop.Entity.Image;
import MapTraveler.develop.Entity.Post;

public interface ImageRepository extends JpaRepository<Image,Integer>{
	
	@Query("select distinct i from Image i left join fetch i.post left join fetch i.favourites where i.post=:post order by i.id asc")
	List<Image> findByPost(Post post);
}
