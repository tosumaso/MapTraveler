document.addEventListener("DOMContentLoaded",()=> {
	let stompClient = null;

function connect() {
	console.log("hi")
	const socket = new SockJS('/endpoint'); 
	stompClient = Stomp.over(socket); 
	stompClient.connect({}, function(frame) {
		stompClient.subscribe('/big/greetings', function(message) {
			const record = JSON.parse(message.body);
			console.log(record);
			showGreeting(record);
		});
	});
}

function sendMessage() {
	const comment = document.querySelector("#comment");
	const postId = document.querySelector("#post-id");
	const userId = document.querySelector("#user-id");
	stompClient.send("/app/hello", {}, JSON.stringify(
		{ 'content': comment.value, 'postId': postId.value, 'userId': userId.value}));
	comment.value = "";
}

function showGreeting(record) {
	const li = document.createElement("li");
	const combody = document.createElement("div");
	const cominfo = document.createElement("p");
	const ul = document.getElementById("greetings");
	const date = new Date(record.createdDate).toLocaleString(); //JavaのISO8601形式の日付をjsで'yyyy/MM/dd HH:mm:ss'の形に変換
	ul.appendChild(li);
	li.classList.add("compost");
	combody.classList.add("combody");
	cominfo.classList.add("cominfo");
	combody.innerHTML = `<p>${record.content}</p>`;
	cominfo.innerHTML = `<p>
		<span class="pe-3">${date}</span>
		<span>${record.user.username}</span>
	</p>`;
	li.appendChild(combody);
	li.appendChild(cominfo);
}

(function() { //一番最初に処理される即時関数
	document.querySelectorAll(".form-inline").forEach(target => {
		target.addEventListener("submit", (e) => {
			e.preventDefault();
		});
	});
	document.querySelector('#send-comment').addEventListener("click", () => { sendMessage(); });
}());

setTimeout(connect, 2000);
})

