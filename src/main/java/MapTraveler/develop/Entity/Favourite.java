package MapTraveler.develop.Entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="favourite")
public class Favourite {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", referencedColumnName="id")
	@JsonManagedReference
	private User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="post_id", referencedColumnName="id")
	@JsonManagedReference
	private Post post;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="image_id", referencedColumnName="id")
	@JsonManagedReference
	private Image image;

	public Favourite() {

	}
	
	public Favourite(User user, Image image, Post post) {
		super();
		this.user = user;
		this.image = image;
		this.post = post;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Image getImage() {
		return image;
	}

	public void setPost(Image image) {
		this.image = image;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
}
