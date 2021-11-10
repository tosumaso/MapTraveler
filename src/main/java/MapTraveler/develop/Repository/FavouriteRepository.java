package MapTraveler.develop.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import MapTraveler.develop.Entity.Favourite;
import MapTraveler.develop.Entity.Image;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer>{

	List<Favourite> findByImage(Image image);
}
