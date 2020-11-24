$("document").ready(function(){
	$("#btnBack").click(function(){history.back()});
	var board = new Object;
	
	$("#btnRegister").click(function(){
		board.boardTitle = $("#boardTitle").val();
		board.boardContent = $("#boardContent").val();
		board.userName = "jykim";
		console.log(board)
		$.ajax({
			type:"POST",
			url:"/Board/Board3",
			data:JSON.stringify(board),
			success:function(res){
				if(res) {
					location.href = document.referrer; // 뒤로가기, 새로고침
				} else {
					alert("게시글 등록 실패");
				}
			}
		})
	})
	
})