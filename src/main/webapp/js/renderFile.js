function renderFile(container, filepath, callback) {
	var html = "<div class='am-form-group am-form-file'> "
			+ "<button type='button' class='am-btn am-btn-sm' id='uploadButton'> "
			+ "<i class='am-icon-cloud-upload'></i> 选择要上传的文件 " + "</button> "
			+ "<input id='doc-form-file' type='file'><input id='fileurl' type='hidden'> " + "</div> ";
	container.html(html);
	$(".am-form-file").width("160.83px");
	var progress = $.AMUI.progress
			.configure({
				barSelector : '[role="nprogress-bar"]',
				parent : '.am-form-file',
				template : "<div class='am-progress am-progress-striped am-progress-sm am-active '>"
						+ "<div class='am-progress-bar am-progress-bar-secondary' role='nprogress-bar' style='width:30%'></div>"
						+ "</div>"
			});
	$("#doc-form-file").on("change", function() {
		$(this).attr("disabled", "disabled");
		progress.start();
		var formData = new FormData();
		formData.append('file', $(this)[0].files[0]);
		formData.append('filepath', filepath);
		$.ajax({
			type : "post",
			url : "tencentUpload.do",
			contentType : false,
			processData : false, 
			data : formData,
			success : function(data) {
				if(data.errorCode == "0000") {
					$("#fileurl").val(data.data);
					callback(data.data);
				}
				progress.done();
				$(this).removeAttr("disabled");
				showmsg(data.errorCode, data.errorMsg);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown, data) {
				progress.done();
				$(this).removeAttr("disabled");
				showmsg("9999", "上传失败");
			}
		});
	});
}