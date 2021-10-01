package MapTraveler.develop.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import MapTraveler.develop.Auth.ApplicationUser;
import MapTraveler.develop.Entity.Map;
import MapTraveler.develop.Entity.Post;
import MapTraveler.develop.Form.PostForm;
import MapTraveler.develop.Repository.MapRepository;
import MapTraveler.develop.Repository.PostRepository;
import MapTraveler.develop.Repository.UserRepository;

@Controller
public class MapTravelerController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MapRepository mapRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@GetMapping("/index")
	public String getTest(@AuthenticationPrincipal ApplicationUser principal, Model model) {
		model.addAttribute("username", principal.getUsername()); //ユーザー名取得
		return "index";
	}
	
	@GetMapping("get/markers") //一覧画面を取得して1秒後にマーカー情報を取得してMapに表示するAjax処理
	@ResponseBody
	public List<Map> getMarkers(){
		List<Map> markers = mapRepository.findAll();
		return markers;
	}
	
	@PostMapping("/postMap") //マーカーを挿す座標とPostの内容を保存
	public String postGoogle(@AuthenticationPrincipal ApplicationUser principal, PostForm form) {
		Map map = new Map(form.getLat(), form.getLng());
		Post post = new Post(form.getTitle(), form.getContent(), form.getStar());
		map.setPost(post);
		post.setMap(map);
		post.setUser(userRepository.findById(principal.getId()).get());
		mapRepository.save(map);
		return "redirect:/index";
	}
	
	@GetMapping("/getPostMap") //マーカーに紐づいたPostのページを取得する
	public String getPostMap(int id, Model model) {
		Post post =postRepository.findById(id).get();
		model.addAttribute("post", post);
		return "/post";
	}
	
	@GetMapping("/search")
	@ResponseBody
	public List<Post> searchByKeyword(@RequestParam(name="keyword") String keyword) {
		String escapedKeyword = HtmlUtils.htmlEscape(keyword);
		List<Post> posts = postRepository.findByKeywordLike(escapedKeyword);
		return posts;
	}
	
}

//ログイン中のユーザー情報取得
//1つめ
//Authentication auth= SecurityContextHolder.getContext().getAuthentication();
//System.out.println(auth.getName());
//2つめ
//public String getTest(Principal principal) { Principalオブジェクトを注入、認証されているユーザー情報オブジェクト
//	String name = principal.getName(); プリンシパルに格納されているユーザー名を取得
//	System.out.println(name);
//	return "index";
//}
