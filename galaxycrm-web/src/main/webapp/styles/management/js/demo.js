function curl() {
	if ($('input[name=chId]').val()) {
		this.href = '${basePath}/management/chanel/recommend/replay/'
				+ $('input[name=chId]').val()
	} else {
		this.href = '${basePath}/management/chanel/recommend/replay/null'
	}
}

function buttonsubmit_onclick(id) {
	var amenuname = '#amenuname' + id;
	var amenudate = '#amenudate' + id;
	var amenutime = '#amenutime' + id;
	var amenualiasname = '#amenualiasname' + id;
	var aliasName = '#aliasName' + id;
	var bmenuname = '#bmenuname' + id;
	var amenualiasname = '#amenualiasname' + id;

	$("input[name='aAliasName']").attr("value", $(amenualiasname).val());
	$("input[name='aliasName']").attr("value", $(aliasName).val());
	$("input[name='bAliasName']").attr("value", $(bmenuname).val());
	$("input[name='aName']").attr("value", $(amenuname).val());
	$("input[name='aDate']").attr("value", $(amenudate).val());
	$("input[name='aTime']").attr("value", $(amenutime).val());

	if (id == 1) {
		//这里写你要执行的语句 
		$("input[name='bStatus']").attr("value",
				$("select[name='bStatus1']").val());
		$("input[name='aStatus']").attr("value",
				$("select[name='aStatus1']").val());
		$("input[name='chId']").attr("value",
				$("input[name='group1.chId']").val());
		$("input[name='chName']").attr("value",
				$("input[name='group1.chName']").val());
		$("input[name='name']").attr("value",
				$("input[name='vod1.name']").val());
		$("input[name='startDate']").attr("value",
				$("input[name='vod1.startDate']").val());
		$("input[name='startTime']").attr("value",
				$("input[name='vod1.startTime']").val());
		$("input[name='stopDate']").attr("value",
				$("input[name='vod1.stopDate']").val());
		$("input[name='stopTime']").attr("value",
				$("input[name='vod1.stopTime']").val());
		$("input[name='bName']").attr("value",
				$("input[name='bmenu1.name']").val());
		$("input[name='bDate']").attr("value",
				$("input[name='bmenu1.date']").val());
		$("input[name='bTime']").attr("value",
				$("input[name='bmenu1.time']").val());
		$("input[name='bFolder']").attr("value",
				$("input[name='bmenu1.folder']").val());
		$("input[name='bAssertid']").attr("value",
				$("input[name='bmenu1.assetId']").val());
		$("input[name='aFolder']").attr("value",
				$("input[name='amenu1.folder']").val());
		$("input[name='aAssertid']").attr("value",
				$("input[name='amenu1.assetId']").val());
	} else if (id == 2) {
		//这里写你要执行的语句 
		$("input[name='bStatus']").attr("value",
				$("select[name='bStatus2']").val());
		$("input[name='aStatus']").attr("value",
				$("select[name='aStatus2']").val());
		$("input[name='chId']").attr("value",
				$("input[name='group2.chId']").val());
		$("input[name='chName']").attr("value",
				$("input[name='group2.chName']").val());
		$("input[name='name']").attr("value",
				$("input[name='vod2.name']").val());
		$("input[name='startDate']").attr("value",
				$("input[name='vod2.startDate']").val());
		$("input[name='startTime']").attr("value",
				$("input[name='vod2.startTime']").val());
		$("input[name='stopDate']").attr("value",
				$("input[name='vod2.stopDate']").val());
		$("input[name='stopTime']").attr("value",
				$("input[name='vod2.stopTime']").val());
		$("input[name='bName']").attr("value",
				$("input[name='bmenu2.name']").val());
		$("input[name='bDate']").attr("value",
				$("input[name='bmenu2.date']").val());
		$("input[name='bTime']").attr("value",
				$("input[name='bmenu2.time']").val());
		$("input[name='bFolder']").attr("value",
				$("input[name='bmenu2folder']").val());
		$("input[name='bAssertid']").attr("value",
				$("input[name='bmenu2.assetId']").val());
		$("input[name='aFolder']").attr("value",
				$("input[name='amenu2.folder']").val());
		$("input[name='aAssertid']").attr("value",
				$("input[name='amenu2.assetId']").val());
	} else if (id == 3) {
		//这里写你要执行的语句 
		$("input[name='bStatus']").attr("value",
				$("select[name='bStatus3']").val());
		$("input[name='aStatus']").attr("value",
				$("select[name='aStatus3']").val());
		$("input[name='chId']").attr("value",
				$("input[name='group3.chId']").val());
		$("input[name='chName']").attr("value",
				$("input[name='group3.chName']").val());
		$("input[name='name']").attr("value",
				$("input[name='vod3.name']").val());
		$("input[name='startDate']").attr("value",
				$("input[name='vod3.startDate']").val());
		$("input[name='startTime']").attr("value",
				$("input[name='vod3.startTime']").val());
		$("input[name='stopDate']").attr("value",
				$("input[name='vod3.stopDate']").val());
		$("input[name='stopTime']").attr("value",
				$("input[name='vod3.stopTime']").val());
		$("input[name='bName']").attr("value",
				$("input[name='bmenu3.name']").val());
		$("input[name='bDate']").attr("value",
				$("input[name='bmenu3.date']").val());
		$("input[name='bTime']").attr("value",
				$("input[name='bmenu3.time']").val());
		$("input[name='bFolder']").attr("value",
				$("input[name='bmenu3.folder']").val());
		$("input[name='bAssertid']").attr("value",
				$("input[name='bmenu3.assetId']").val());
		$("input[name='aFolder']").attr("value",
				$("input[name='amenu3.folder']").val());
		$("input[name='aAssertid']").attr("value",
				$("input[name='amenu3.assetId']").val());
	} else if (id == 4) {
		//这里写你要执行的语句 
		$("input[name='bStatus']").attr("value",
				$("select[name='bStatus4']").val());
		$("input[name='aStatus']").attr("value",
				$("select[name='aStatus4']").val());
		$("input[name='chId']").attr("value",
				$("input[name='group4.chId']").val());
		$("input[name='chName']").attr("value",
				$("input[name='group4.chName']").val());
		$("input[name='name']").attr("value",
				$("input[name='vod4.name']").val());
		$("input[name='startDate']").attr("value",
				$("input[name='vod4.startDate']").val());
		$("input[name='startTime']").attr("value",
				$("input[name='vod4.startTime']").val());
		$("input[name='stopDate']").attr("value",
				$("input[name='vod4.stopDate']").val());
		$("input[name='stopTime']").attr("value",
				$("input[name='vod4.stopTime']").val());
		$("input[name='bName']").attr("value",
				$("input[name='bmenu4.name']").val());
		$("input[name='bDate']").attr("value",
				$("input[name='bmenu4.date']").val());
		$("input[name='bTime']").attr("value",
				$("input[name='bmenu4.time']").val());
		$("input[name='bFolder']").attr("value",
				$("input[name='bmenu4.folder']").val());
		$("input[name='bAssertid']").attr("value",
				$("input[name='bmenu4.assetId']").val());
		$("input[name='aFolder']").attr("value",
				$("input[name='amenu4.folder']").val());
		$("input[name='aAssertid']").attr("value",
				$("input[name='amenu4.assetId']").val());
	} else if (id == 5) {
		//这里写你要执行的语句 
		$("input[name='bStatus']").attr("value",
				$("select[name='bStatus5']").val());
		$("input[name='aStatus']").attr("value",
				$("select[name='aStatus5']").val());
		$("input[name='chId']").attr("value",
				$("input[name='group5.chId']").val());
		$("input[name='chName']").attr("value",
				$("input[name='group5.chName']").val());
		$("input[name='name']").attr("value",
				$("input[name='vod5.name']").val());
		$("input[name='startDate']").attr("value",
				$("input[name='vod5.startDate']").val());
		$("input[name='startTime']").attr("value",
				$("input[name='vod5.startTime']").val());
		$("input[name='stopDate']").attr("value",
				$("input[name='vod5.stopDate']").val());
		$("input[name='stopTime']").attr("value",
				$("input[name='vod5.stopTime']").val());
		$("input[name='bName']").attr("value",
				$("input[name='bmenu5.name']").val());
		$("input[name='bDate']").attr("value",
				$("input[name='bmenu5.date']").val());
		$("input[name='bTime']").attr("value",
				$("input[name='bmenu5.time']").val());
		$("input[name='bFolder']").attr("value",
				$("input[name='bmenu5.folder']").val());
		$("input[name='bAssertid']").attr("value",
				$("input[name='bmenu5.assetId']").val());
		$("input[name='aFolder']").attr("value",
				$("input[name='amenu5.folder']").val());
		$("input[name='aAssertid']").attr("value",
				$("input[name='amenu5.assetId']").val());
	}

}
function buttongroup_onclick(head, id) {
	//这里写你要执行的语句
	var com = '.combox' + id;
	var group = '#group' + id;
	var url = head + $(com).find("option:selected").val();
	$(group).attr("href", url);
}
function buttonvod_onclick(head, id) {
	//这里写你要执行的语句
	var id2 = '#groupid' + id;
	var vod2 = '#vod' + id;
	var url = head + $(id2).val();
	$(vod2).attr("href", url);
}
function buttonbmenu_onclick(head, id) {
	//这里写你要执行的语句
	var id2 = '#groupid' + id;
	var menu2 = '#bmenu' + id;
	var url = head + $(id2).val();
	$(menu2).attr("href", url);
}

function buttonamenu_onclick(head, id) {
	//这里写你要执行的语句
	var id2 = '#groupid' + id;
	var menu2 = '#amenu' + id;
	var url = head + $(id2).val() + "/" + id;
	$(menu2).attr("href", url);
}
function comboxb_onclick(id) {
	//这里写你要执行的语句
	var flag = ".bStatus" + id;
	var play = ".bplay" + id;
	var playback = ".bplayback" + id;

	var playname = ".bplayname" + id;
	var playurl = ".bplayurl" + id;
	var menuname = ".bmenuname" + id;
	if ($(flag).find("option:selected").val() == 2) {
		$(play).attr("style", "display:none");
		$(playback).attr("style", "display:true");

		$(playname).removeClass("required");
		$(playurl).removeClass("required");
		$(menuname).addClass("required");

	} else {
		$(play).attr("style", "display:true");
		$(playback).attr("style", "display:none");

		$(playname).addClass("required");
		$(playurl).addClass("required");
		$(menuname).removeClass("required");
	}

}
function comboxa_onclick(id) {
	//这里写你要执行的语句
	
	var flag = ".aStatus" + id;
	var play = ".aplay" + id;
	var playback = ".aplayback" + id;

	var playname = ".aplayname" + id;
	var playurl = ".aplayurl" + id;
	var aplayback1 = ".aplayback1" + id;
	var aplayback2 = ".aplayback2" + id;
	var aplayback3 = ".aplayback3" + id;
	
	if ($(flag).find("option:selected").val() == 2) {
		$(play).attr("style", "display:none");
		$(playback).attr("style", "display:true");

		$(playname).removeClass("required");
		$(playurl).removeClass("required");
		$(aplayback1).addClass("required");
		$(aplayback2).addClass("required");
		$(aplayback3).addClass("required");
	} else {
		$(play).attr("style", "display:true");
		$(playback).attr("style", "display:none");

		$(playname).addClass("required");
		$(playurl).addClass("required");
		$(aplayback1).removeClass("required");
		$(aplayback2).removeClass("required");
		$(aplayback3).removeClass("required");
	}

}
var flag = 0;
function label_onclick() {
	//这里写你要执行的语句
	if (flag) {
		$('#picturereview').show();
		flag = 0;
	} else {
		$('#picturereview').hide();
		flag = 1;
	}
}

function team_onclick() {
	//这里写你要执行的语句

	if (flag) {
		$('#picturereview').hide();
		flag = 0;
	} else {
		$('#showimg').attr("src",
				$('#item>.selected input[type=checkbox]').val());
		$('#picturereview').show();
		flag = 1;
	}
}

function item_onclick() {
	//这里写你要执行的语句

	if (flag) {
		$('#picturereviewitem').hide();
		flag = 0;
	} else {
		$('#showimgitem').attr("src",
				$('#item>.selected input[type=checkbox]').val());
		$('#picturereviewitem').show();
		flag = 1;
	}
}
function initem_onclick(id) {
	//这里写你要执行的语句
	var id="#checkbox"+id;

	if (flag) {
		$('#picturereviewinitem').hide();
		flag = 0;
	} else {
		$('#showimginitem').attr("src",$(id).val());
		$('#picturereviewinitem').show();
		flag = 1;
	}
}

function additemteam_onclick() {
	//这里写你要执行的语句

	var parentBody = $(window.parent.document.body);

	var imgUrl = $('#additemteam>.selected input[type=hidden]').val();

	var showImg = parentBody.find("#showImg");
	if (showImg.length == 0) {
		showImg = $("<img>").attr("id", "showImg").attr("src", imgUrl).css({
			"position" : "absolute",
			"right" : "30px",
			"top" : "50px",
			"z-index" : "999"
		}).click(function() {
			$(this).hide();
		})
		parentBody.append(showImg)
	} else {
		showImg.attr("src", imgUrl).show();
	}
	//    	
	//        if(flag){
	//        	$('#picturereviewadditemteam').hide();
	//        	flag=0;
	//        }
	//        else{
	//        	$('#showimgadditemteam').attr("src",$('#additemteam>.selected input[type=checkbox]').val());
	//        	$('#picturereviewadditemteam').show();
	//        	flag=1;
	//        	}
}
function datechange(id) {
	if (id != null) {
		var amenuname = '#amenuname' + id;
		var amenudate = '#amenudate' + id;
		var amenutime = '#amenutime' + id;
		var amenualiasname = '#amenualiasname' + id;
		var obj = eval("(" + $(".sortDrag>.selected input[type=radio]").val()
				+ ")")
		$(amenuname).attr("value", obj.name);
		$(amenudate).attr("value", obj.date);
		$(amenutime).attr("value", obj.time);
		$(amenualiasname).attr("value", obj.name);
	}
}
var flag1 = 0;
function image_onclick() {
	var obj = eval("(" + $(".image>.selected input[type=radio]").val() + ")")
	$('#imageshow').attr("src", obj.image);
	if (!flag_image) {
		$('#imagereview').show();
		flag_image = true;
	} else {
		$('#imagereview').hide();
		flag_image = false;
	}
}
function image1_onclick() {
	if (!flag_image1) {
		$('#imagereview1').show();
		flag_image1 = true;
	} else {
		$('#imagereview1').hide();
		flag_image1 = false;
	}
}
var flag2 = 0;
var flag_background = false;
var flag_poster = false;
var flag_image = false;
var flag_background1 = false;
var flag_poster1 = false;
var flag_image1 = false;
function poster_onclick() {
	var obj = eval("(" + $(".image>.selected input[type=radio]").val() + ")")
	$('#postershow').attr("src", obj.poster);
	if (!flag_poster) {
		$('#posterreview').show();
		flag_poster = true;
	} else {
		$('#posterreview').hide();
		flag_poster = false;
	}
}
function poster1_onclick() {
	if (!flag_poster1) {
		$('#posterreview1').show();
		flag_poster1 = true;
	} else {
		$('#posterreview1').hide();
		flag_poster1 = false;
	}
}
function background_onclick() {
	var obj = eval("(" + $(".image>.selected input[type=radio]").val() + ")")
	$('#backgroundshow').attr("src", obj.poster);
	if (!flag_background) {
		$('#backgroundreview').show();
		flag_background = true;
	} else {
		$('#backgroundreview').hide();
		flag_background = false;
	}
}
function background1_onclick() {
	if (!flag_background1) {
		$('#backgroundreview1').show();
		flag_background1 = true;
	} else {
		$('#backgroundreview1').hide();
		flag_background1 = false;
	}
}
function edititemteam_click(head) {
	var teamid = $('#team>.selected input[type=checkbox]').val();
	var itemid = $('#initem>.selected input[type=hidden]').val();
	var url = head + teamid + '/' + itemid;
	$('#edititemteam').attr("href", url);
}
function deleteitemteam_click(head) {
	var teamid = $('#team>.selected input[type=checkbox]').val();
	var itemid = $('#initem>.selected input[type=hidden]').val();
	var url = head + teamid + '/' + itemid;
	$('#deleteitemteam').attr("href", url);
}
function self_confirm() {
	return confirm('确认发布到前台？');
}
function searchactor_click(head) {
	var url = head + "?keywords=" + $('input[name=name]').val();
	$('#searchactor').attr("href", url);
}

function buttonaddactor_onclick(head) {
	var url = head + "?keywords=" + $('#orgName').val();
	$('#addactor').attr("href", url);
}

function addactor_onclick(){
	$('#name').attr("value",$('#orgName').val());
}

function buttonsaddactor_onclick(head, id) {
	var ids = "#search" + id;
	var names = "#name" + id;
	var url = head + "?keywords=" + $(names).val();
	$(ids).attr("href", url);
}
function buttonaddactors_onclick(head) {
	var url = head + "?keywords=" + $('#searchactor1').val();
	$('#addactor').attr("href", url);
}
function buttonssearchactor_onclick(head) {
	var url = head + "?keywords=" + $('#searchactor').val();
	$('#searchersactor').attr("href", url);
}
function testConfirmMsg(url, id) {
	alertMsg.confirm("确认发布到前台?", {
		okCall : function() {
			var ids = "hiddensubmit" + id;
			document.getElementById(ids).click();
		}
	});
}	
function callBackActor(){
	var msg=new Object();
	msg.orgName=$('#searchactor1').val();
	$.bringBack(msg);
}