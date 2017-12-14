function renderFile(container, options) {
	var url = "";
	var html = "<div class='am-form-group am-form-file'> "
			+ "<button type='button' class='am-btn am-btn-sm' id='uploadButton'> "
			+ "<i class='am-icon-cloud-upload'></i> 选择要上传的文件 " + "</button> "
			+ "<input id='doc-form-file' type='file'><input id='fileurl' type='hidden' value='img/admin/headImg.jpg'> " + "</div> ";
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
	if (options.source == '1') {
		url = "localUpload.do";
	}
	if (options.source == '2') {
		url = "qiNiuUpload.do";
	}
	if (options.source == '3') {
		url = "tencentUpload.do";
	}
	$("#doc-form-file").on("change", function() {
		var file = $(this)[0].files[0];
		var filetype = file.name.substring(file.name.lastIndexOf(".") + 1).toLowerCase();
		if (options.filetype.indexOf(filetype) == -1) {
			showmsg("9999", "请上传文件类型为" + options.filetype + "的文件.");
			return;
		}
		$(this).attr("disabled", "disabled");
		progress.start();
		var formData = new FormData();
		formData.append('file', file);
		formData.append('filepath', options.filepath);
		$.ajax({
			type : "post",
			url : url,
			contentType : false,
			processData : false, 
			data : formData,
			success : function(data) {
				if(data.errorCode == "0000") {
					$("#fileurl").val(data.data);
					if (options.callback != null) {
						options.callback(data.data);	
					}
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