package MapTraveler.develop.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//package MapTraveler.develop.Entity;
//
//import java.io.File;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(name="image")
////@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
//public class Image {
//
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private Integer id;
//	
//	@Column(name="file", nullable=false)
//	private File file;
//	
//	@ManyToOne
//	@JoinColumn(name="post_id", referencedColumnName="id")
//	private Post post;
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public File getFile() {
//		return file;
//	}
//
//	public void setFile(File file) {
//		this.file = file;
//	}
//
//	public Post getPost() {
//		return post;
//	}
//
//	public void setPost(Post post) {
//		this.post = post;
//	}
//	
//	
//	
//}
@Entity
@Table(name = "image")
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @Column(name="name")
  private String name;
  
  @Column(name="type")
  private String type;
  
  @Lob // ポイント2: @Lobとを以下のようにつける(@Lobはサイズが大きいデータのカラムにつけるみたい。
  @Column(name = "data")
  private byte[] data;
  
  @OneToOne
  @JoinColumn(name="post_id", referencedColumnName="id")
  private Post post;
  
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
  
  

}
