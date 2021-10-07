package MapTraveler.develop.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import MapTraveler.develop.Entity.Image;
import MapTraveler.develop.Entity.Post;

public interface ImageRepository extends JpaRepository<Image,Integer>{

	List<Image> findByPost(Post post);
}
