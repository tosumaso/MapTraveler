package MapTraveler.develop.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "image")
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @Column(name="name", nullable=false)
  private String name;
  
  @Column(name="type", nullable=false)
  private String type;
  
  @Lob // ポイント2: @Lobを以下のようにつける(@Lobはサイズが大きいデータのカラムにつけるみたい。
  @Column(name = "data", nullable=false)
  private byte[] data;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="post_id", referencedColumnName="id", nullable=false)
  private Post post;
  
  @OneToMany(mappedBy="image")
  @JsonBackReference
  private List<Favourite> favourites = new ArrayList<Favourite>();
  
  public Image(String name, String type, byte[] data) {
    this.name = name;
    this.type = type;
    this.data = data;
  }

  public Image() {
	  
  }
  
public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public byte[] getData() {
	return data;
}

public void setData(byte[] data) {
	this.data = data;
}

public Post getPost() {
	return post;
}

public void setPost(Post post) {
	this.post = post;
}

public List<Favourite> getFavourites() {
	return favourites;
}

public void setFavourites(List<Favourite> favourites) {
	this.favourites = favourites;
}

}
