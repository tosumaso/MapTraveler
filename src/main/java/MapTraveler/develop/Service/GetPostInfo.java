package MapTraveler.develop.Service;

import java.util.List;

import MapTraveler.develop.Entity.Image;
import MapTraveler.develop.Entity.Post;

public class GetPostInfo {

	List<Image> images;
	List<String> base64List;
	List<Boolean> favouriteFlag;
	Post post;
	
	public GetPostInfo(List<Image> images, List<String> base64List, List<Boolean> favouriteFlag) {
		super();
		this.images = images;
		this.base64List = base64List;
		this.favouriteFlag = favouriteFlag;
	}

	public Post getPost() {
		return post;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<String> getBase64List() {
		return base64List;
	}

	public void setBase64List(List<String> base64List) {
		this.base64List = base64List;
	}

	public List<Boolean> getFavouriteFlag() {
		return favouriteFlag;
	}

	public void setFavouriteFlag(List<Boolean> favouriteFlag) {
		this.favouriteFlag = favouriteFlag;
	}

	public void setPost(Post post) {
		this.post = post;
	}
		
}
