// いいね機能
document.addEventListener("DOMContentLoaded", () => {
	const listImages =document.querySelectorAll(".image-list");
	const times = document.querySelectorAll("#times");
	const post = document.querySelector("#post-id");
		/*listImages.forEach(image => {
			image.addEventListener("click", async ()=> {
				const result = await fetch(`send/Myfavourite?imageId=${post.value}`); //ajaxでuser_idを送る
				const data = await result.json();
				document.querySelector("#times").innerHTML = data.length;
			})
		})*/
		
		listImages.forEach((image, index) => {
			image.addEventListener("click", async()=> {
				const result = await fetch(`send/Myfavourite?imageIndex=${index}&postId=${post.value}`);
				const data = await result.json();
				times[index].innerHTML = data.length;
			})
		})

})