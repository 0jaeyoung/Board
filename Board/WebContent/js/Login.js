$(document).ready(function(){
	$("#btnLogin").click(function(){
		var uInfo = new Object();
		uInfo.uID = $("#userId").val();
		uInfo.uPW = $("#passwd").val();
		if (!uInfo.uID || !uInfo.uPW) {
			alert("회원 정보를 모두 입력하세요.");
		} else {
			$.ajax({
				type:"POST",
				url:"/Board/Login",
				data:JSON.stringify(uInfo),
				success:function(res){
					if(res.result=="true"){
						alert(res.userName+"님 환영합니다!");
						window.open("html/Board1.html", "_self")
						//location.href="html/Board1.html";
						//location.replace("html/Board1.html")
						console.log(res);
					}else{
						alert("회원정보 불일치");
					}
				},
				error:function(e){
					alert("로그인 시도 중 오류 발생");
					console.log(e);
				}
			})
		}
	})
})
