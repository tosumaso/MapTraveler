package MapTraveler.develop.Service;

import java.util.List;

import MapTraveler.develop.Entity.Post;
import MapTraveler.develop.Entity.User;

public class MyPageInfo {

	private User user;
	private List<String> images;
	private List<Post> posts;
	private List<String> favouriteImages;
	
	public MyPageInfo(User user, List<String> images, List<Post> posts, List<String> favouriteImages) {
		super();
		this.user = user;
		this.images = images;
		this.posts = posts;
		this.favouriteImages = favouriteImages;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<String> getFavouriteImages() {
		return favouriteImages;
	}

	public void setFavouriteImages(List<String> favouriteImages) {
		this.favouriteImages = favouriteImages;
	}
	
	
}
