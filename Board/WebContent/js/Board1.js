$("document").ready(function(){
	$.ajax({
		type:"POST",
		url:"/Board/Board1",
		success:function(res){
			if(res.result=="true"){
				setBoardList(res.boardList);
			}else{
				alert("게시판 불러오기 실패");
				console.log(res);
			}
		}
	})
	
	var setBoardList = function(data){
		var totalCnt = data.length;
		$("#brdCnt").text(totalCnt);
		for(var i=0; i<totalCnt; i++){
			var brd = data[i];
			var listTmp = $(".lists:first").clone();
			listTmp.find(".title").text(brd.boardTitle);
			listTmp.find(".regDate").text(brd.createDate);
			listTmp.find(".regName").text(brd.userName);
			listTmp.attr("boardId", brd.boardId);
			$("#brdList").append(listTmp);
		}
		$(".lists:first").addClass("hide");
	}
	
	$("#brdList").on("click", ".lists", function(){
		console.log($(this).attr("boardId"));
		//location.href="html/Board2.html";
		window.open("Board2.html?"+$(this).attr("boardId"), "_self");
	})
})