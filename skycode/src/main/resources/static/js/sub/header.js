$(function () {
    // 내비게이션바
    $('nav').mouseenter(function (e) {
        e.preventDefault // a태그 기본 이벤트 제거
        $('.sub').stop().slideDown();
        $('.navBg1').stop().animate({ height: 180 }, 400);
        $('.navBg2').css("height", "100vh");
    });

    $('.navBg2').mouseenter(function () {
        $('.sub').stop().slideUp();
        $('.navBg1').stop().animate({ height: 0 }, 400, function () {
            $('.navBg2').css("height", "0");
        });
    });

    // modalUser
    $('.userModal').click(function () {
        $('.jhModalUser').css("display", "block");
        $('.jhUser').stop().animate({ right: 0 });
        $("body").css("overflow", "hidden");
    });

    $('.closeMenu,.jhModalUserBg').click(function () {
        $('.jhUser').stop().animate({ right: -500 }, function () {
            $('.jhModalUser').css("display", "none");
            $("body").css("overflow", "auto");
        });
    });

    $(document).ready(function () {
        //탭(ul) onoff
        $('.tabBox>.subTab').children().css('display', 'none');
        $('.tabBox>.subTab div:first-child').css('display', 'block');
        $('.tabBox>.mainTab li:first-child').addClass('on');
    });

    $('.tabBox').delegate('.mainTab>li', 'click', function () {
        var index = $(this).parent().children().index(this);
        $(this).siblings().removeClass();
        $(this).addClass('on');
        $(this).parent().next('.subTab').children().hide().eq(index).show();
    });

});
