let locations = [] //全てのマーカーの位置情報
let markers = [] //markerオブジェクトの配列
let results = []; //検索結果のmapオブジェクトを一時保存する

function initMap() {
	const mapEle = document.querySelector("#map");
	japan = new google.maps.LatLng(35.4010216, 137.9153554);

	const mapOpt = {
		zoom: 5.4,
		center: japan,
		keyboardShortcuts: false,
		mapTypeControl: false
	}

	const map = new google.maps.Map(mapEle, mapOpt);

	showCurrentMarkers(locations); //全てのマーカーを表示する

	map.addListener("click", (event) => {
		showModal(event.latLng);
	})

	function showModal(latLng) {
		const body = document.body;
		const modal = document.querySelector("#modal"); //モーダル画面の背景
		const closeBtn = document.querySelector("#closeBtn"); //モーダル画面を閉じるボタン

		modal.style.display = "block"; //モーダル表示ボタンが押されたら#modalのdivのstyle属性のdisplayプロパティにblockを追加
		document.getElementById("lat").value = latLng.lat();
		document.getElementById("lng").value = latLng.lng();
		
		body.classList.add("fixed");

		closeBtn.addEventListener("click", () => {
			modal.style.display = "none"; //閉じるボタンが押されたらモーダル背景のdivタグにdisplay:noneを設定して見えなくする
			body.classList.remove("fixed");
		})
		window.addEventListener('click', (e) => { //閉じるボタンの他にモーダル背景がクリックされたらモーダルを閉じる
			if (e.target === modal) { //クリックされた要素が#modalのdiv要素なら
				modal.style.display = 'none';
				body.classList.remove("fixed");
			}
		});
	}
	window.addEventListener("load", () => { //ホームページを取得して1秒後にマーカーを一覧表示する
		setTimeout(() => {
			fetch("get/markers").then(response => {
				if (response.ok) {
					return response.json();
				} else {
					throw new Error("Markers cannot be loaded");
				}
			}).then(data => {
				console.log(data);
				data.forEach(d => {
					locations.push(d);
				})
			}).then(() => showCurrentMarkers(locations)).catch(error => alert(error));
		}, 1000)
	});

	function showCurrentMarkers(places) { //全て or 検索結果のマーカーを表示(配列を引数にとり、表示対象に応じてマーカーを表示できる)
		let currentWindow;

		if (markers) { //マーカーがすでに立っていれば一度全てのマーカーを非表示にする
			for (let i = 0; i < markers.length; i++) {
				markers[i].setMap(null)
				results = [] //検索結果も空にしてリセットする
			}
		}

		places.map(m => { //その後、マーカーを複数表示し、infoWindowを紐づかせる
			const marker = new google.maps.Marker({
				position: { lat: m.lat, lng: m.lng },
				map: map
			})

			markers.push(marker) //作成したマーカーをグローバルスコープで管理

			marker.addListener("click", () => {
				currentWindow && currentWindow.close();
				const infoWindow = new google.maps.InfoWindow({ //Ajaxで受け取った配列に複数のレコードが格納されており、外部参照先の値もJSで参照できる
					content: `<a href="/getPostMap?id=${m.post.id}">${m.post.title}</a>` //JSファイル内なためthymeleafを使えない。
				});
				infoWindow.open({
					anchor: marker,
					map,
					shoudFocus: false
				});
				currentWindow = infoWindow;
			})
		})
	}

	const keyword = document.querySelector("#keyword");
	const submitKeyword = document.querySelector("#submit-keyword");

	submitKeyword.addEventListener("click", (e) => { //Postに含まれる単語でキーワード検索
		e.preventDefault();
		fetch(`search/?keyword=${keyword.value}`).then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error("エラー");
			}
		}).then(data => {
			console.log(data)
			data.forEach(d => {
				const result = locations.filter(location => location.id === d.map.id); //検索結果のmapを取得
				results.push(result[0])
			})
			showCurrentMarkers(results)
		}).catch(error => alert(error));
	})

}

document.addEventListener("DOMContentLoaded", () => {
	const stars = document.getElementsByClassName('star');

	// 星マークにマウスオーバーした時のイベント
	const starMouseover = (e) => {
		const index = Number(e.target.getAttribute('data-star')); //mouseoverされたHTMLClassList内の要素をe.targetで特定してその属性値を取得
		for (let j = 0; j < index; j++) {
			stars[j].textContent = '★';
		}
	}

	// 星マークからマウスが離れた時のイベント
	const starMouseout = (e) => {
		for (let j = 0; j < stars.length; j++) {
			stars[j].textContent = '☆';
		}
	}

	for (let i = 0; i < stars.length; i++) {
		stars[i].addEventListener('mouseover', starMouseover);
		stars[i].addEventListener('mouseout', starMouseout);

		// 星マークをクリックした時のイベント
		stars[i].addEventListener('click', e => {

			for (let j = 0; j < stars.length; j++) { //1:星をクリックしたらいったん全ての黒星が消える(黒星の数を減らしたいときに対応するため)
				stars[j].textContent = '☆';
			}
			const index = Number(e.target.getAttribute('data-star'));//2:その後クリックされた星の要素を特定し、属性値の数だけ星を黒星にする。
			for (let j = 0; j < index; j++) {
				stars[j].textContent = '★';
			}
			// マウスオーバーとマウスアウトのイベント解除
			for (let j = 0; j < stars.length; j++) {
				stars[j].removeEventListener('mouseover', starMouseover);
				stars[j].removeEventListener('mouseout', starMouseout);
			}

			document.getElementById("star-input").value = index;
		})
	}

	const multiplebox = document.getElementById("multiple-contents");
	const textarea = document.querySelector('.multiple-texts');
	const clientHeight = textarea.clientHeight;

	autoTextarea();
	closeContent();

	//画像選択したときのテキストボックス、画像追加ボックスを追加
	multiplebox.addEventListener('change', (e) => {
		if (e.target.tagName === "INPUT") {
			const eachContent =document.createElement("div");
			eachContent.setAttribute("class","each-content");
			multiplebox.appendChild(eachContent);
			eachContent.insertAdjacentHTML('beforeend', `<textarea  name="texts" class="multiple-texts"></textarea>`);
			eachContent.insertAdjacentHTML('beforeend', `<input type="file" name="files" class="multiple-images">`);
			eachContent.insertAdjacentHTML('beforeend', `<button type="button" class="btn-close close-each-content" aria-label="Close"></button>`)
		}

	})
	
	function closeContent(){
		multiplebox.addEventListener('click',(e) => {
			const ele = e.target;
			if (ele.className === "btn-close close-each-content"){
				const eachContent =ele.parentNode;
				multiplebox.removeChild(eachContent);
			}
		})
	}

	//textareaの高さを自動調整
	function autoTextarea() {
		multiplebox.addEventListener('input', (e) => {
			if (e.target.tagName === "TEXTAREA") {
				e.target.style.height = clientHeight + 'px';
				let scroll = e.target.scrollHeight;
				e.target.style.height = scroll + 'px';
			}
		})
	}



})