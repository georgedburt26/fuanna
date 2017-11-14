<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script src="js/jquery.min.js"></script>
</head>
<body>
<form action="upload.do" method="post" id="iconForm" enctype="multipart/form-data">
<input type="file" id="fileToUpload" name="file"/>
<input type="button" id="upload" value="上传">
<input type="submit" value="上传">
</form>
<script>
var formData = new FormData($("#fileToUpload")[0]);
$("#upload").click(function() {
	$.ajax({ 
		url : "upload.do", 
		type : 'POST', 
		data : formData, 
		// 告诉jQuery不要去处理发送的数据
		processData : false, 
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		beforeSend:function(){
		console.log("正在进行，请稍候");
		},
		success : function(responseStr) {
			alert(responseStr);
		if(responseStr.status===0){
		console.log("成功"+responseStr);
		}else{
		console.log("失败");
		}
		}, 
		error : function(responseStr) { 
		console.log("error");
		} 
		});
});

</script>
</body>
</html>