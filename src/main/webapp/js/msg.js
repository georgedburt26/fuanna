function showmsg(errorCode, errorMsg) {
	$("#fuana-success-alert").remove();
	$("#fuanna-fail-alert").remove();
	if (errorCode == '0000' && errorMsg != '') {
		$("body").append("<div class='am-form-group'><div class='fuanna-alert fuanna-success' id='fuana-success-alert'>" +
		        "<p>" + errorMsg + "</p>" +
		        "</div></div>");
		$("#fuana-success-alert").fadeOut(5000, function() {
					$(this).remove();
	});	
	}
	if (errorCode == '9999') {
			$("body").append("<div class='am-form-group'><div class='fuanna-alert fuanna-fail' id='fuanna-fail-alert'>" +
			        "<p>" + errorMsg + "</p>" +
			        "</div></div>");
			$("#fuanna-fail-alert").fadeOut(5000, function() {
				$(this).remove();
	});
	}
}
(function(){
    var test;
    this.test = function(a,b){
    	alert(123);
    };
}).call(this);