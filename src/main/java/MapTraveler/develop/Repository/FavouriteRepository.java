package MapTraveler.develop.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import MapTraveler.develop.Entity.Favourite;
import MapTraveler.develop.Entity.Image;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer>{
	
	@Query("select f from Favourite f left join fetch f.user left join fetch f.post left join fetch f.image where f.image =:image")
	List<Favourite> findByImage(Image image);
	

}
