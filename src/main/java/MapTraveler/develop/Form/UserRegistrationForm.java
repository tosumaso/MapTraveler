package MapTraveler.develop.Form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegistrationForm {

	@Size(min=4, max=10, message="ユーザー名を4文字から10文字へ変更してください")
	private String username;
	
	@Size(min=8, max=20, message="パスワードを8文字から20文字へ変更してください")
	@Pattern(regexp="^[a-zA-Z0-9]+$", message="パスワードを半角英数字へ変更してください")
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
