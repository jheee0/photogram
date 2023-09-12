/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */


// (1) 스토리 로드하기

let page = 0;

function storyLoad() {

	$.ajax({
		data: "get",
		url: `/api/image`,
		dataType: "json",

	}).done(res => {
		console.log(res);

		res.data.content.forEach((image) => {
			let storyItem = getStoryItem(image);
			$("#storyList").append(storyItem);
		});
	});
}

storyLoad();

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {

	//console.log("윈도우 scrollTop", $(window).scrollTop());
	//console.log("문서의 높이", $(document).height);
	//console.log("윈도우 높이", $(window).height());

	let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());

	// 근사치 계산
	if (checkNum < 1 && checkNum > -1) {
		page++;
		storyLoad();
	}
});

function getStoryItem(image) {
	let item = `<!--전체 리스트 아이템-->
<div class="story-list__item">
	<!--리스트 아이템 헤더영역-->
	<div class="sl__item__header">
	<div><img class="profile-image" src="/upload/${image.user.profileImageUrl}" alt=""  onerror="this.src='/images/man.png'"/></div>
		<div>${image.user.username}</div>
	</div>
	<!--헤더영역 end-->
	
	<!--게시물이미지 영역-->
	<div class="sl__item__img">
		<img src="/upload/${image.postImageUrl}" alt="" />
	</div>
	
	<!--게시물 내용 + 댓글 영역-->	
	<div class="sl__item__contents">
	<div class="sl__item__contents__icon">
	
	<button>`;

	if (image.likeState) {
		item += `<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i`;
	} else {

		item += `<i class="far fa-heart" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i`;
	}

	item += `
	</button>
	</div>
	
	<span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount}</b>likes</span>
	
	<div class="sl__item__contents__content">
	
	<p>${image.caption}</p>
	</div>
	<div id="storyCommentList-${image.id}">
	<div class="sl__item__contents__comment" id="storyCommentItem-1"">
	<p>
	<b>Lovely :</b> 부럽습니다
	</p>
	<button>
	<i class="fas fa-times"></i>
	</button>
	</div>
	</div>
	<div class="sl__item__input">
	<input type="text" placeholder="댓글 달기" id="storyCommentInput-${image.id}" />
	<button type="button" onClick="addComment(${image.id})">등록</button>
	</div>
	</div>
	</div>`;

	return item;
}




// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`); // 좋아요를 하겠다

	if (likeIcon.hasClass("far")) {
		$.ajax({
			type: "post",
			url: `api/image/${imageId}/likes`,
			dataType: "json"
		}).done((res => {

			let likeCountStr = $(`#storyLikeCount - ${imageId}`).text();


			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		})).fail(error => {
			console.log("오류");
		});

	} else { // 좋아요 취소

		$.ajax({
			type: "delete",
			url: `api/image/${imageId}/likes`,
			dataType: "json"
		}).done((res => {
			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		})).fail(error => {
			console.log("오류");
		});
	}
}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		imageId: imageId,
		content: commentInput.val()
	}


	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	$.ajax({
		type: "post",
		url: "/api/comment",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		dataType: "json"
	}).done(res => {
		console.log("comment 성공", res)
	}).fail(error => {
		console.log("comment 오류", error);
	});

	let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-2""> 
			    <p>
			      <b>GilDong :</b>
			      댓글 샘플입니다.
			    </p>
			    <button><i class="fas fa-times"></i></button>
			  </div>
	`;
	commentList.prepend(content);
	commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment() {

}







