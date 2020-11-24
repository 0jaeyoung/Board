$("document").ready(function(){
	$("#btnBack").click(function(){history.back()});
	var boardInfo = new Object();
	boardInfo.boardId = location.search.substr(1);
	$.ajax({
		type:"POST",
		url:"/Board/Board2",
		data:JSON.stringify(boardInfo),
		success:function(res){
			setBoardDetail(res);
		}
	})
	var setBoardDetail = function(data){
		$("#boardTitle").text(data.boardTitle);
		$("#createId").text(data.userName);
		$("#createDate").text(data.createDate);
		$("#boardContent").text(data.boardContent);
	}
})