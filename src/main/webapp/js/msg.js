function showmsg(errorCode, errorMsg) {
	$("#fuana-success-alert").remove();
	$("#fuanna-fail-alert").remove();
	if (errorCode == '0000') {
		if (errorMsg != '') {
			$("body")
					.append(
							"<div class='am-form-group'><div class='fuanna-alert fuanna-success' id='fuana-success-alert'>"
									+ "<p>"
									+ errorMsg
									+ "</p>"
									+ "</div></div>");
			$("#fuana-success-alert").fadeOut(5000, function() {
				$(this).remove();
			});
		}
	} else {
		if (errorMsg != '') {
			$("body")
					.append(
							"<div class='am-form-group'><div class='fuanna-alert fuanna-fail' id='fuanna-fail-alert'>"
									+ "<p>"
									+ errorMsg
									+ "</p>"
									+ "</div></div>");
			$("#fuanna-fail-alert").fadeOut(5000, function() {
				$(this).remove();
			});
		}
	}
}
$('body').on(
		'click',
		"#fuana-success-alert",
		function() {
			$(this).remove();
		});
$('body').on(
		'click',
		"#fuanna-fail-alert",
		function() {
			$(this).remove();
		});