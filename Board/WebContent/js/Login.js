$(document).ready(function(){
	$("#btnLogin").click(function(){
		var uInfo = new Object();
		uInfo.uID = $("#userId").val();
		uInfo.uPW = $("#passwd").val();
		if (!uInfo.uID || !uInfo.uPW) {
			alert("회원 정보를 모두 입력하세요.");
		} else {
			loginRequest(uInfo);
			
		}
	})
	var loginRequest = function(userInfo){
		$.ajax({
			type:"POST",
			url:"/Board/Login",
			data:JSON.stringify(userInfo),
			success:function(res){
				if(res.result=="true"){
					window.localStorage.setItem("userName", res.userName);
					if($("#saveId").prop("checked")){
						console.log("아이디저장")
						// TODO : 아이디 저장 로직
					}
					if($("#savePw").prop("checked")){
						console.log("자동로그인")
						// TODO : 자동로그인 로직
					}
					alert(res.userName+"님 환영합니다!");
					window.open("html/Board1.html", "_self")
					//location.href="html/Board1.html";
					//location.replace("html/Board1.html")
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
