package MapTraveler.develop.Auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import MapTraveler.develop.Entity.User;

public class ApplicationUser implements UserDetails{

	private Integer id;
	private String password;
	private String username;
	
	
	
	public ApplicationUser() {
		
	}

	public ApplicationUser(User user) {
		this.id = user.getId();
		this.password = user.getPassword();
		this.username = user.getUsername();
	}
	
	public static ApplicationUser of (User user) {
		ApplicationUser userDetails = new ApplicationUser();
	    if (null != user) {
	        userDetails.username = user.getUsername();
	        userDetails.password = user.getPassword();
	        userDetails.id = user.getId();
	    }
	    return userDetails;
	  }

	public Integer getId() { //@AuthenticationPrincipalのユーザー情報からentityのIDを取得するためのgetter
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return null;
	}
	@Override
	public String getPassword() {
		
		return password;
	}
	@Override
	public String getUsername() {
		
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}
	@Override
	public boolean isEnabled() {
		
		return true;
	}
	
	
}
