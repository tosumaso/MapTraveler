package MapTraveler.develop.Controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import MapTraveler.develop.Auth.ApplicationUser;
import MapTraveler.develop.Entity.Image;
import MapTraveler.develop.Entity.Map;
import MapTraveler.develop.Entity.Post;
import MapTraveler.develop.Entity.Text;
import MapTraveler.develop.Form.PostForm;
import MapTraveler.develop.Repository.CommentRepository;
import MapTraveler.develop.Repository.ImageRepository;
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
	
	@Autowired
	ImageRepository imageRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
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
	
	@PostMapping("/postMap") //マーカーを挿す座標とPostの内容を保存(複数の画像を保存する場合)
	public String postGoogle(@AuthenticationPrincipal ApplicationUser principal, PostForm form, Model model) {
		try {
			Map map = new Map(form.getLat(), form.getLng());
			Post post = new Post(form.getTitle(), form.getStar());
			List<MultipartFile> files = form.getFiles();
			List<String> images = new ArrayList<String>();
			for (MultipartFile file : files) { //複数の画像を保存、画像のバイナリデータをListにしてthymeleafで表示
				String fileName = StringUtils.cleanPath(file.getOriginalFilename()); //画像のパスを正規化
				Image savedFile = new Image(fileName, file.getContentType(), file.getBytes());
				savedFile.setPost(post);
				post.getImages().add(savedFile); //postエンティティの参照にImageエンティティを追加
				byte[] bytes = savedFile.getData(); //保存したImageエンティティのバイナリデータを取得
			    images.add(Base64.getEncoder().encodeToString(bytes)); //バイナリデータを文字列にする
			}
			List<String> contents = form.getTexts();
			for (String content : contents) {
				Text text = new Text(content);
				text.setPost(post);
				post.getTexts().add(text);
			}
		    model.addAttribute("images", images);
		    model.addAttribute("texts", contents);
			map.setPost(post);
			post.setMap(map);
			post.setUser(userRepository.findById(principal.getId()).get());
			mapRepository.save(map); //mapエンティティを保存、cascadeType.Persistでpostエンティティも保存、cascadeType.Persistでimageエンティティも保存される
		    return "redirect:/index";
		} catch (Exception e) {
			System.out.println("エラー");
			return "redirect:/index";
		}
		
	}
	
	@GetMapping("/getPostMap") //マーカーに紐づいたPostのページを取得する(画像が複数ある場合)
	public String getPostMap(int id, Model model) {
		Post post =postRepository.findById(id).get();
		model.addAttribute("post", post);
		List<Image> images = imageRepository.findByPost(post);
		List<String> files= images.stream().map(image ->  Base64.getEncoder().encodeToString(image.getData())).collect(Collectors.toList());
		model.addAttribute("images", files);
		Set<Text> texts = post.getTexts();
		model.addAttribute("texts", texts);
		model.addAttribute("comments", commentRepository.findAll()); //一覧画面取得時、メッセージの一覧を取得してhtmlに描画する
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
