package MapTraveler.develop.Entity;

import java.util.HashSet;
import java.util.Set;

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
	
	@OneToMany(mappedBy="post", cascade=CascadeType.PERSIST) //DBとのSessionが切れた後にEntityのJsonを返す。コレクションをSetにし、FetchType.EAGERで親エンティティ取得時に子の参照先Entityも読み込む
	private Set<Image> images = new HashSet<Image>();
	
	@OneToMany(mappedBy="post", cascade=CascadeType.PERSIST)
	private Set<Text> texts = new HashSet<Text>();

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
	
	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public Set<Text> getTexts() {
		return texts;
	}

	public void setTexts(Set<Text> texts) {
		this.texts = texts;
	}	
	
}
