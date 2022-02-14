package MapTraveler.develop.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import MapTraveler.develop.Auth.ApplicationUser;
import MapTraveler.develop.Entity.Favourite;
import MapTraveler.develop.Entity.Map;
import MapTraveler.develop.Entity.Post;
import MapTraveler.develop.Form.DeleteFavouriteForm;
import MapTraveler.develop.Form.PostForm;
import MapTraveler.develop.Service.CommentService;
import MapTraveler.develop.Service.FavouriteService;
import MapTraveler.develop.Service.GetPostInfo;
import MapTraveler.develop.Service.MapService;
import MapTraveler.develop.Service.MyPageInfo;
import MapTraveler.develop.Service.PostService;

@Controller
public class MapTravelerController {

	@Autowired
	MapService mapService;

	@Autowired
	PostService postService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	FavouriteService favouriteService;
	
	@GetMapping("/index")
	public String getInde(@AuthenticationPrincipal ApplicationUser principal, Model model, PostForm postForm) {
		model.addAttribute("username", principal.getUsername()); //ユーザー名取得
		return "/indexs";
	}

	@GetMapping("/get/markers") //一覧画面を取得して1秒後にマーカー情報を取得してMapに表示するAjax処理
	@ResponseBody
	public List<Map> getMarkers() {
		List<Map> markers = mapService.findAll();
		return markers;
	}

	@PostMapping("/postMap") //マーカーを挿す座標とPostの内容を保存(複数の画像を保存する場合)
	public String postGoogle(@AuthenticationPrincipal ApplicationUser principal, @Valid PostForm postForm,
			BindingResult result,
			Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			//RedirectAttributes.addFlashAttribute("変数",値); リダイレクト先からモデルの変数を参照できる
			attributes.addFlashAttribute("modalError", "全ての空白を埋めてください");
			return "redirect:/index";
		}
		try {
			Map map = postService.saveMapPost(principal, postForm);
			return "redirect:/index";
		} catch (Exception e) {
			System.out.println(e);
			return "redirect:/index";
		}

	}

	@GetMapping("/getPostMap") //マーカーに紐づいたPostのページを取得する(画像が複数ある場合)
	public String getPostMap(Integer id, Model model, @AuthenticationPrincipal ApplicationUser principal) {
		GetPostInfo info = postService.getPost(principal, id);
		model.addAttribute("username", principal.getUsername());
		model.addAttribute("userId", principal.getId());
		model.addAttribute("post", info.getPost());
		model.addAttribute("images", info.getImages());
		model.addAttribute("base64List", info.getBase64List());
		model.addAttribute("favouriteFlag", info.getFavouriteFlag());
		model.addAttribute("comments", commentService.findAll());
		return "/post";
	}

	@GetMapping("/send/Myfavourite")
	@ResponseBody
	public List<Favourite> countUpFavourite(@AuthenticationPrincipal ApplicationUser principal,
			@RequestParam(name = "imageIndex") Integer imageIndex, @RequestParam(name = "postId") Integer postId) {
		List<Favourite> favourites = favouriteService.saveOrDeleteFavourite(principal, imageIndex, postId);
		return favourites;
	}

	@PostMapping("/delete/Myfavourite")
	@ResponseBody
	public List<Favourite> deleteFavourite(@RequestBody DeleteFavouriteForm form,
			@AuthenticationPrincipal ApplicationUser principal) { //@RequestBody HttpRequestのbodyに含まれたJsonデータをFormオブジェクトと紐づける
		List<Favourite> favourites = favouriteService.saveOrDeleteFavourite(principal, form.getImageIndex(), form.getPostId());
		return favourites;
	}

	@GetMapping("/search")
	@ResponseBody
	public List<Post> searchByKeyword(@RequestParam(name = "keyword") String keyword) {
		List<Post> posts = postService.findByKeyword(keyword);
		return posts;
	}

	@GetMapping("/mypage")
	public String getMypage(@AuthenticationPrincipal ApplicationUser principal, Model model) {
		MyPageInfo info = postService.getMyPage(principal);
		model.addAttribute("user", info.getUser());
		model.addAttribute("images",info.getImages()); //ログインユーザーが投稿した画像
		model.addAttribute("posts", info.getPosts()); //いいねに紐づいたpost
		model.addAttribute("favouriteImages",info.getFavouriteImages()); //いいねに紐づいた画像が表示されるか確認する
		model.addAttribute("likes",info.getUser().getLikes());
		return "/mypages";
		
	}

}
