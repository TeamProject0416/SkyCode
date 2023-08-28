$(function () {

    // 화면 밖으로 마우스가 나갔을때
    $(document).bind("mouseleave", function () {
        $('.sub').stop().slideUp();
        $('.navBg1').stop().animate({ height: 0 }, 400, function () {
            $('.navBg2').css("height", "0");
        });
    })

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

});
