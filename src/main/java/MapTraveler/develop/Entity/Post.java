package MapTraveler.develop.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="post")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class Post {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="title", nullable=false)
	private String title;
	
	@Column(name="star", nullable=false)
	private Integer star;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="id", nullable=false)
	@JsonManagedReference //PostがシリアライズしたときUserもシリアライズする(参照先のデータを持てる)
	private User user;
	
	@OneToOne
	@JoinColumn(name="map_id", referencedColumnName="id", nullable=false)
	private Map map;
	
	@OneToMany(mappedBy="post", cascade=CascadeType.PERSIST)
	private List<Image> images = new ArrayList<Image>();
	
	@OneToMany(mappedBy="post", cascade=CascadeType.PERSIST)
	private List<Text> texts = new ArrayList<Text>();

	public Post() {
		
	}

	public Post(String title, Integer star) {
		super();
		this.title = title;
		this.star = star;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
	
	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<Text> getTexts() {
		return texts;
	}

	public void setTexts(List<Text> texts) {
		this.texts = texts;
	}
	
}
