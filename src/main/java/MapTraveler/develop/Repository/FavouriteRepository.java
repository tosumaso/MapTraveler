package MapTraveler.develop.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import MapTraveler.develop.Entity.Favourite;
import MapTraveler.develop.Entity.Image;
import MapTraveler.develop.Entity.Post;
import MapTraveler.develop.Entity.User;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer>{
	
	@Query("select f from Favourite f left join fetch f.user left join fetch f.post left join fetch f.image where f.image =:image")
	List<Favourite> findByImage(Image image);
	

	Favourite findByImageAndPostAndUser(Image image, Post post, User user);
}
