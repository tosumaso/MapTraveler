package MapTraveler.develop.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="username", nullable=false)
	private String username;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@OneToMany(mappedBy="user")
	@JsonBackReference //UserがSerializeされたときPostはシリアライズしない(外部参照先のデータを持たない)
	private List<Post> posts = new ArrayList<Post>();
	
	@OneToMany(mappedBy="user")
	@JsonBackReference
	private List<Comment> comments = new ArrayList<Comment>();
	
	@OneToMany(mappedBy="user")
	@JsonBackReference
	private List<Favourite> likes = new ArrayList<Favourite>();
	
	public User() {

	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(Integer id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Favourite> getLikes() {
		return likes;
	}

	public void setLikes(List<Favourite> likes) {
		this.likes = likes;
	}
	
}
