document.addEventListener("DOMContentLoaded",()=> {
	let stompClient = null; 
/*
function setConnected(connected) {
	document.querySelector("#connect").disabled = connected;
	document.querySelector("#disconnect").disabled = !connected;

	const conversation = document.querySelector("#conversation");
	if (connected) {
		
	}
	else {
		conversation.style.display = "none";
	}
}*/

function connect() {
	console.log("hi")
	const socket = new SockJS('/endpoint'); 
	stompClient = Stomp.over(socket); 
	stompClient.connect({}, function(frame) {
		//setConnected(true);
		stompClient.subscribe('/big/greetings', function(message) {
			const record = JSON.parse(message.body);
			showGreeting(record.content);
		});
	});
}

/*function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}*/

function sendMessage() {
	const comment = document.querySelector("#comment");
	const postId = document.querySelector("#post-id");
	const userId = document.querySelector("#user-id");
	stompClient.send("/app/hello", {}, JSON.stringify(
		{ 'content': comment.value, 'postId': postId.value, 'userId': userId.value}));
	comment.value = "";
}

function showGreeting(message) {
	const tr = document.createElement("tr");
	const td = document.createElement("td");
	td.textContent = message;
	tr.appendChild(td);
	document.querySelector("#greetings").appendChild(tr); // <tr><td>message</td></tr>を描画
}

(function() { //一番最初に処理される即時関数
	document.querySelectorAll(".form-inline").forEach(target => {
		target.addEventListener("submit", (e) => {
			e.preventDefault();
		});
	});
	//document.querySelector("#connect").addEventListener("click", () => { connect(); });
	//document.querySelector("#disconnect").addEventListener("click", () => { disconnect(); });
	document.querySelector('#send-comment').addEventListener("click", () => { sendMessage(); });
}());

setTimeout(connect, 2000);
})

