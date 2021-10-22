package MapTraveler.develop.Form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegistrationForm {

	@NotNull(message="ユーザー名を入力してください")
	@Size(min=4, max=10, message="適切な字数で入力してください")
	private String username;
	
	@NotNull(message="パスワードを入力してください")
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
