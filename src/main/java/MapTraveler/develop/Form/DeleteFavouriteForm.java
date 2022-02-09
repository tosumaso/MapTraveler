package MapTraveler.develop.Form;

public class DeleteFavouriteForm {

	private Integer imageIndex;
	private Integer postId;
	
	public Integer getImageIndex() {
		return imageIndex;
	}
	public void setImageIndex(Integer imageIndex) {
		this.imageIndex = imageIndex;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public DeleteFavouriteForm() {
		super();
	}
	public DeleteFavouriteForm(Integer imageIndex, Integer postId) {
		super();
		this.imageIndex = imageIndex;
		this.postId = postId;
	}
	
}
