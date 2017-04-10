// JavaScript Document
$(document).ready(function() {
			// 回车事件
			document.onkeydown = function(e) {
				var ev = document.all ? window.event : e;
				if (ev.keyCode == 13) {
					login();
				}
			}

			$("#submit-form").click(function() {
						login();
					})

			function login() {
				var username = $("#account").val();
				var password = $("#password").val();

				if (username == "") {
					alert("用户名不能为空");
					return;
				}

				if (password == "") {
					alert("密码不能为空");
					return;
				}

				$.ajax({
							url : basePath + "/user/ajaxLogin",
							type : "post",
							dataType : "json",
							cache : false,
							async : false,
							traditional : true,
							data : {
								"username" : username,
								"password" : password
							},

							success : function(json) {
								if (json.success) {
									window.location = redirectURL;
								} else {
									//$("#account").val("");
									$("#password").val("");
									alert("账户名或密码错误，请重试！");
								}
							},
							error : function() {
								// alert("error");
							}
						});
			}
		});
