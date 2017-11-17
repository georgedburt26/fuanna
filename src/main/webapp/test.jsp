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
<a class="weui-btn weui-btn_primary submit-btn" id="scanQRCode" type="button">扫一扫</a>
<script>
var formData = new FormData($("#fileToUpload"));
$("#upload").click(function() {
	$.ajax({ 
		url : "upload.do", 
		type : 'POST', 
		data : formData, 
        async: false,  
        cache: false,  
        contentType: false,  
        processData: false,  
        success: function (returndata) {  
            alert(returndata.msg);  
        },  
        error: function (returndata) {  
            alert(returndata);  
        }  
		});
});

</script>
</body>
</html>