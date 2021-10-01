package MapTraveler.develop.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import MapTraveler.develop.Entity.User;
import MapTraveler.develop.Form.UserRegistrationForm;

@Controller
public class SecurityController {
	
	@Autowired
	ApplicationUserService appUserService;

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
	@GetMapping("newUser")
	public String getNewUserPage() {
		return "newUser"; 
	}
	
	@PostMapping("/newUser")
	public String createNewUser(UserRegistrationForm form) {
		User user = new User();
		user.setUsername(form.getUserName());
		appUserService.createUser(user, form.getPassword());
		return "redirect:/login";
	}
	
	
}
