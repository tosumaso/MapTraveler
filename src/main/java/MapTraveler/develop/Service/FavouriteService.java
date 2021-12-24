package MapTraveler.develop.Service;

import java.util.List;

import MapTraveler.develop.Auth.ApplicationUser;
import MapTraveler.develop.Entity.Favourite;

public interface FavouriteService {

	List<Favourite> saveOrDeleteFavourite(ApplicationUser principal, Integer imageIndex, Integer postId);
}
