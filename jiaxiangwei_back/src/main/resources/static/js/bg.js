Date.prototype.format =function(format) {
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(),    //day
        "h+" : this.getHours(),   //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
        (this.getFullYear()+"").substr(4- RegExp.$1.length));
    for(var k in o)if(new RegExp("("+ k +")").test(format))
        format = format.replace(RegExp.$1,
            RegExp.$1.length==1? o[k] :
                ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}

$(function(){
	$(".aclick").click(function(){
		if($(".dpmenu").css("display")=="none"){
			$(".dpmenu").css("display","block");
		}
		else{
			$(".dpmenu").css("display","none");
		}
	});
	
	$(".cmenu > li").click(function(){
        $(".x_cmenuli").removeClass("x_cmenuli");
        $(this).addClass("x_cmenuli");
		$(this).find(".submenu").slideDown();
		$(this).siblings().find(".submenu").slideUp();
	});

	$(".a").click(function(){
		if($(".capbox").css("display")=="none"){			
			$(".capbox").slideDown();
		}
		else{
			$(".capbox").slideUp();
		}
	});

	$(".tspan li").click(function(){
		var index=$(this).index();
		$(this).css("background-color","#fff");
		$(this).siblings().css("background-color","#4b8df8");
		$("#table"+index).css("display","block");
		$("#table"+index).siblings().css("display","none");
	});

	$(".censpan li").click(function(){
		var index=$(this).index();
		$(this).css("background-color","#fff");
		$(this).siblings().css("background-color","#4b8df8");
		$(".div"+index).css("display","block");
		$(".div"+index).siblings().css("display","none");
	});

	$(".qdiv").click(function(){
		$('body').append(' <div class="alldiv"></div>');
     	var href=$(this).attr("href");
		$('.alldiv').css('display','block');
        $(href).css('display','block');
	});
	
	$(".xx").click(function(){
		$('.alldiv').css('display','none');
        $('.d').css('display','none');
		$('.alldiv').remove();
	});

   $("#xSearchbtn").bind("click",function(){
       $(".caption").show();
   });
   $(".caption button[type='submit']").bind("click",function(){
       $(this).parents(".caption").hide();
   });
    $(".x_admin_box,.x_nav1,.x_nav2").bind("click",function(){

        $(".x_nav_focus").removeClass("x_nav_focus");
        $(this).addClass("x_nav_focus");
    });

    $(document).click(function(e){
        var bol = $(e.target).parents().is(".caption"),
            adminBol = $(e.target).parents().is(".x_admin_box"),
            xnav1Bol = $(e.target).parents().is(".x_nav1"),
            xnav2Bol = $(e.target).parents().is(".x_nav2");

        if(!bol&&$(e.target).attr("id")!="xSearchbtn"&&!$(e.target).is(".icon-plus-search")&&!$(e.target).parents().is(".panel")){
            $(".caption").hide();
        }
        if(!adminBol){
            $(".x_admin_box").removeClass("x_nav_focus").find(" .dpmenu").hide();
        }
        if(!xnav1Bol){
            $(".x_nav1 ul").hide();
        }
        if(!xnav2Bol){
            $(".x_nav2 ul").hide();
        }
    });
    /**************左侧菜单默认状态**************/
    var _cur = $(".cttit").html(),
        _cur = $.trim(_cur).replace("当前位置：",""),
        _cur = _cur.split("&gt;");

    $.each($(".cmenu > li"),function(i,n){
        //console.log($.trim($(n).children("a").text())+"||"+_cur[0]);

        if($.trim($(n).children("a").text())==_cur[0]){
            $(n).trigger("click");
            $.each($(".cmenu > li li"),function(j,m){
                //console.log($.trim($(m).text())+"||"+_cur[1]);
                if($.trim($(m).text())==_cur[1]){
                    $(m).addClass("cmenuCur");
                }
            })

        }

    });

    $("table.table").wrap("<div class='scrollTable' />");
});

/*************弹出层****************/
//{'id':'newslinkPopup','width':'680','height':'360'} 设置弹出层宽高
//"id"  默认宽高
function xPopup(op){
    var oo, w,h;
    if(typeof op === 'string'){
        oo = $("#"+op);
    }
    if(typeof op === "object"){
        oo = $("#"+op.id),w = op.width,h = op.height;
        oo.css({
            'width':w+'px',
            'height':h+'px',
            'marginTop':-h/2 +'px',
            'marginLeft':-w/2 +'px'
        });
    }
    oo.show(200);
    $(".xPopupClose").bind("click",function(){
        $(this).parents(".xPopup").hide(200);
    });
}
/***load url 弹出层***/
function xPopupUrl(op){
    var oo=$("#"+op.id),_url=op.url;
    oo.show(200);
    $.ajax({
        'url':_url,
        success:function(data){
            console.log(typeof data);
            $(".xPopup_body").html(data);
        }
    });

    $(".xPopupClose").bind("click",function(){
        $(this).parents(".xPopup").hide(200);
    });


}


