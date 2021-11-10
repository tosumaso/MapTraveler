package MapTraveler.develop.Form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRegistrationForm {

	@NotBlank(message="ユーザー名を入力してください")
	@Size(min=4, max=10, message="適切な字数で入力してください")
	private String username;
	
	@NotBlank(message="パスワードを入力してください")
	@Size(min=8, max=20, message="適切な字数で入力してください")
	private String password;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
