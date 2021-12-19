package MapTraveler.develop.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="post")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class Post{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="title", nullable=false)
	private String title;
	
	@Column(name="star", nullable=false)
	private Integer star;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", referencedColumnName="id", nullable=false)
	@JsonIgnore //PostがシリアライズしたときUserもシリアライズする(参照先のデータを持てる)
	private User user;
	
	//fetch=FetchType.LAZYにするとシリアライズ時にMapのレコードを参照できない: EAGERにすると別の処理でn+1が起きる!!
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="map_id", referencedColumnName="id", nullable=false)
	private Map map;
	
	@OneToMany(mappedBy="post", cascade=CascadeType.PERSIST)
	@JsonIgnore
	private List<Image> images = new ArrayList<Image>();
	
	@OneToMany(mappedBy="post", cascade=CascadeType.PERSIST)
	@JsonIgnore
	private List<Text> texts = new ArrayList<Text>();
	
	@OneToMany(mappedBy="post")
	@JsonBackReference
	private List<Favourite> likes = new ArrayList<Favourite>();

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

	public List<Favourite> getLikes() {
		return likes;
	}

	public void setLikes(List<Favourite> likes) {
		this.likes = likes;
	}	
	
}
