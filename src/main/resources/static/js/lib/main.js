//使用settimeout来避免快速双击和无法被关闭的问题
var touchtime = new Date().getTime();
$("[name='switch-status-more']").on("click", function(){      //快捷入口
  if( new Date().getTime() - touchtime < 150 ){     //防止快速双击
      setTimeout(function(){$(".switch-status").removeClass("show");}, 151);
      setTimeout(function(){$("[name='switch-status-more']").removeClass("open");}, 151);
  }else{
    touchtime = new Date().getTime();
    if($(".switch-status, #release_resource_btn, #release_demand_btn").hasClass("show")){
      $(".switch-status, #release_resource_btn, #release_demand_btn").removeClass("show");
      $("[name='switch-status-more']").removeClass("open");
    }
    else{
      setTimeout(function(){$(".entrance_mark").addClass("show");}, 10);      //首页快捷发布
      setTimeout(function(){$("[name='switch-status-more']").addClass("open");}, 10);     //快捷入口
      setTimeout(function(){$("#login_btn, #release_resource_btn").addClass("show");}, 0);      //第一个按钮
      setTimeout(function(){$("#register_btn, #release_demand_btn").addClass("show");}, 50);      //第二个按钮
      setTimeout(function(){$("#forget_btn").addClass("show");}, 150);      //第三个按钮
    }
    $(".entrance_mark").on("click", function(){$(this).removeClass("show");})
    $(document).on("click", function(){
      $(".switch-status, #release_resource_btn, #release_demand_btn, .entrance_mark").removeClass("show");
      $("[name='switch-status-more']").removeClass("open");
    })
  }
});



$(document).on("click", ".myProfile_one_modify", function(){
  $(".card-one").animate({ opacity:0}, 200,function(){
     $(".card-one").css("display","none");
     $(".card-body-one").animate({ 
      opacity:1
    }, 100,function(){
     $(".card-body-one").css("display","block");
    });
  });
  
})

$(document).on("click", ".myProfile_two_modify", function(){
  $(".card-two").animate({ opacity:0}, 200,function(){
     $(".card-two").css("display","none");
     $(".card-body-two").animate({ 
      opacity:1
    }, 100,function(){
     $(".card-body-two").css("display","block");
    });
  });
  
})




$(document).on("click", "#login_btn, #register_btn", function(){    //登录、注册或忘记密码按钮  #forget_btn
  var mark = $(this).next(".switch_mark");
  mark.addClass("is-active");    //全屏涟漪
  $(".login_loading").removeClass("low");     //改变loading动效层级  上一层
  $(".wx-scopy").addClass("anti-overflow");      //涟漪不超出body
  setTimeout(function(){$(".login_loading").addClass("is-active");}, 200);    //加载loading
  setTimeout(function(){
    mark.addClass("remove");        //删掉蒙板和loading
    $(".login_loading").removeClass("is-active");
  }, 1500);
  setTimeout(function(){
    mark.removeClass("is-active remove");
    $(".login_loading").addClass("low");
    $(".wx-scopy").removeClass("anti-overflow");
  }, 2000);
})

$(document).on("click", "#login_btn, #register_btn, #forget_btn", function(){   //切换登录、注册、忘记密码
  $(".switch-status").removeAttr("disabled");
  $(this).attr("disabled","true");
  $(this).removeClass("mdl-color--accent");
})

$(document).on("click", "#login_btn", function(){   //切换成登录
  setTimeout(function(){
    $("[name='login_form']").show(300);
    $("[name='register_form']").hide(300);
  }, 200);
})

$(document).on("click", "#register_btn", function(){   //切换成注册
  setTimeout(function(){
    $("[name='register_form']").show(300);
    $("[name='login_form']").hide(300);
  }, 200);
})

$(document).on("click", "#setpw i", function(){   //注册时查看密码
  $(this).toggleClass("open");
  if ($("[name='setpw']").attr("type") == "password") {$("[name='setpw']").attr("type", "text")}
  else {$("[name='setpw']").attr("type", "password")}
})

//向下滚收起首页一部分头部
$(".mdl-layout__content").scroll(function(){
// 滚动条距离顶部的距离 小于 8px时
  if($(this).scrollTop() < 8){
    $(".mdl-header-bar, .mdl-layout__header-bg, .mdl-layout__tab-bar, .mdl-layout__tab-bar-button").removeClass("hide");    //展开
  }
  else{
    $(".mdl-header-bar, .mdl-layout__header-bg, .mdl-layout__tab-bar, .mdl-layout__tab-bar-button").addClass("hide");   //收起
  }
});

// 首页---->中间内容的高度若是小于887并且大于544时，直接赋值为887
var mainh_content = $(".page-content").height()
if( mainh_content > 544 && mainh_content < 887 ){
    $(".page-content").css("height", "887px");
}

$(document).on("click", function(){       //解决固定定位时左侧滑定位错误问题
  if($(".mdl-layout__drawer").hasClass("is-visible")){ $("[name='drawer_visible']").css("overflow", "hidden"); }
  else{ $("[name='drawer_visible']").css("overflow", "auto"); }
})

// user  banner部分 
var bodyDom = document.getElementById("scroll-main");
var header = document.getElementById("header-gopactiy");
var userbar = document.getElementById("userbar-gopactiy");
$(".mdl-layout").on("scroll",function(){
  var opacity = bodyDom.scrollTop;
  if(opacity == 0){
    $(header).css("opacity", "0");
    $(userbar).css("opacity", "1");
  }
})
// $(window).resize(function() {
//   var body_length = $(window).width();
//   if(body_length > 1024){
//     console.log("aa");
//   }
// });
//判断页面宽度  user页面banner渐变高度变化
var body_length = $(window).width();
if(body_length > 1024){                                            //大于1024宽度时
  $(".mdl-layout").on("scroll",function(){
    var opacity = bodyDom.scrollTop;
    if(opacity > 0){
      var header_opacity = 0 + opacity*.003;
      var userbar_opacity = 1 - opacity*.003;
      // console.log(opacity);
      $(header).css("opacity", header_opacity);
      $(userbar).css("opacity", userbar_opacity);
    }
    if(opacity >= 370){
      $(".mdl-layout__tab-bar-container").addClass("fixed-userbar");
      $(".mdl-header-userbar").css("margin-bottom", "48px");
    }else{
      $(".mdl-layout__tab-bar-container").removeClass("fixed-userbar");
      $(".mdl-header-userbar").css("margin-bottom", "0");
    }
  })
}
if(body_length <= 1024 && body_length > 768){                     //小于1024大于768宽度时
  console.log("小于1024大于768宽度时");
  $(".mdl-layout").on("scroll",function(){
    var opacity = bodyDom.scrollTop;
    console.log(opacity);
    if(opacity > 0){
      var header_opacity = 0 + opacity*.005;
      var userbar_opacity = 1 - opacity*.006;
      $(header).css("opacity", header_opacity);
      $(userbar).css("opacity", userbar_opacity);
    }
    if(opacity >= 237){
      $(".mdl-layout__tab-bar-container").addClass("fixed-userbar");
      $(".mdl-header-userbar").css("margin-bottom", "48px");
    }else{
      $(".mdl-layout__tab-bar-container").removeClass("fixed-userbar");
      $(".mdl-header-userbar").css("margin-bottom", "0");
    }
  })
}
if(body_length <= 768){                                           //小于768宽度时
  console.log("小于768宽度时");
  $(".mdl-layout").on("scroll",function(){
    var opacity = bodyDom.scrollTop;
    console.log(opacity);
    if(opacity > 0){
      var header_opacity = 0 + opacity*.008;
      var userbar_opacity = 1 - opacity*.015;
      $(header).css("opacity", header_opacity);
      $(userbar).css("opacity", userbar_opacity);
    }
    if(opacity >= 138){
      $(".mdl-layout__tab-bar-container").addClass("fixed-userbar");
      $(".mdl-header-userbar").css("margin-bottom", "48px");
    }else{
      $(".mdl-layout__tab-bar-container").removeClass("fixed-userbar");
      $(".mdl-header-userbar").css("margin-bottom", "0");
    }
  })
}

// user_chat -> right_slip  显示隐藏
$(document).on("click", "#right_slip_btn", function(){$(".right_silp__obfuscator, .right_slip").addClass("is-visible");})
$(document).on("click", ".right_silp__obfuscator", function(){$(".right_silp__obfuscator, .right_slip").removeClass("is-visible");})