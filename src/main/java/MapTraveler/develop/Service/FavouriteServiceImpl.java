package MapTraveler.develop.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MapTraveler.develop.Auth.ApplicationUser;
import MapTraveler.develop.Entity.Favourite;
import MapTraveler.develop.Entity.Image;
import MapTraveler.develop.Entity.Post;
import MapTraveler.develop.Entity.User;
import MapTraveler.develop.Repository.FavouriteRepository;
import MapTraveler.develop.Repository.PostRepository;
import MapTraveler.develop.Repository.UserRepository;

@Service
@Transactional
public class FavouriteServiceImpl implements FavouriteService {

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	FavouriteRepository favouriteRepository;

	@Override
	public List<Favourite> saveOrDeleteFavourite(ApplicationUser principal, Integer imageIndex, Integer postId) {
		Post post = postRepository.findById(postId).get();
		Image image = post.getImages().get(imageIndex);
		User user = userRepository.findById(principal.getId()).get();
		
		favouriteRepository.findByImageAndPostAndUser(image, post, user).ifPresentOrElse(
				f -> favouriteRepository.deleteById(f.getId()),
				() -> favouriteRepository.save(new Favourite(user, image, post)));
		
		List<Favourite> favourites = favouriteRepository.findByImage(image);
		return favourites;
	}

}
