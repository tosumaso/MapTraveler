package MapTraveler.develop.Auth;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import MapTraveler.develop.Entity.User;
import MapTraveler.develop.Form.UserRegistrationForm;

@Controller
public class SecurityController {
	
	@Autowired
	ApplicationUserService appUserService;

	@GetMapping("/login")
	public String getLogin(@RequestParam(required= false) String error,  Model model, HttpSession session) {
		model.addAttribute("showErrorFlag", false);
		if (error != null) {
			if (session != null) {
				AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
				if (ex != null) {
					model.addAttribute("showErrorFlag", true);
					model.addAttribute("errorMsg", ex.getMessage());
				}
			}
		} 
		return "login";
	}
	
	@GetMapping("newUser")
	public String getNewUserPage(UserRegistrationForm form, Model model) {
		return "newUser"; 
	}
	
	@PostMapping("/newUser")
	public String createNewUser(@Valid UserRegistrationForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("userRegistrationForm", form);
			return "newUser";
		}
		User user = new User();
		user.setUsername(form.getUsername());
		appUserService.createUser(user, form.getPassword());
		return "login";
	}
	
	
}
