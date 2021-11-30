
document.addEventListener("DOMContentLoaded", () => {
	// いいね機能
	const listImages =document.querySelectorAll(".image-list");
	const times = document.querySelectorAll("#times");
	const post = document.querySelector("#post-id");
		
		listImages.forEach((image, index) => {
			image.addEventListener("click", async()=> {
				const result = await fetch(`send/Myfavourite?imageIndex=${index}&postId=${post.value}`);
				const data = await result.json();
				times[index].innerHTML = data.length;
			})
		})

	//コメントの文字数制限
	const commentInput = document.getElementById("comment");
	const btn = document.getElementById("send-comment");
	const commentAlert = document.getElementById("comment-alert");
	
	commentInput.addEventListener("keyup",(e) => {
		if (e.target.value.length > 200){
			btn.disabled = true;
			commentAlert.style.display = "inline";
		}
		if (e.target.value.length <= 200){
			btn.disabled = false;
			commentAlert.style.display = "none";
		}
	})
})