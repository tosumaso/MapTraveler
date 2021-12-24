package MapTraveler.develop.Service;

import java.io.IOException;
import java.util.List;

import MapTraveler.develop.Auth.ApplicationUser;
import MapTraveler.develop.Entity.Map;
import MapTraveler.develop.Entity.Post;
import MapTraveler.develop.Form.PostForm;

public interface PostService {

	public Map saveMapPost(ApplicationUser principal, PostForm form) throws IOException;
	
	public GetPostInfo getPost(ApplicationUser principal, Integer id);
	
	public List<Post> findByKeyword(String keyword);
	
	public MyPageInfo getMyPage(ApplicationUser principal);
}
