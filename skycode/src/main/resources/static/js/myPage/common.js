// $(document).ready(function () {
//        //탭(ul) onoff
//        $('.tabBox>.subTab').children().css('display', 'none');
//        $('.tabBox>.subTab div:first-child').css('display', 'block');
//        $('.tabBox>.mainTab li:first-child').addClass('on');
//    });
//
//    $('.tabBox').delegate('.mainTab>li', 'click', function () {
//        var index = $(this).parent().children().index(this);
//        $(this).siblings().removeClass();
//        $(this).addClass('on');
//        $(this).parent().next('.subTab').children().hide().eq(index).show();
//    });