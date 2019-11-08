$(document).ready(function () {

    $('input').iCheck({
        checkboxClass: 'icheckbox_minimal-green',
        radioClass: 'iradio_minimal-green',
        increaseArea: '20%'
    });

    var $formPanelTwo = $('.form-panel.two');

    var panelOne = $formPanelTwo.height();
    var panelTwo = $formPanelTwo[0].scrollHeight;

    $formPanelTwo.not('.form-panel.two.active').on('click', function (e) {
        e.preventDefault();

        $('.form-toggle').addClass('visible');
        $('.form-panel.one').addClass('hidden');
        $('.form-panel.two').addClass('active');
        $('.form').animate({
            'height': panelTwo
        }, 200);
    });

    $('.form-toggle').on('click', function (e) {
        e.preventDefault();
        $(this).removeClass('visible');
        $('.form-panel.one').removeClass('hidden');
        $('.form-panel.two').removeClass('active');
        $('.form').animate({
            'height': panelOne + 92
        }, 200);
    });

});

function login() {
    var $loginButton = $("#loginButton");
    var username = $(".one input[name='username']").val().trim();
    var password = $(".one input[name='password']").val().trim();
    if (username === "") {
        $MB.n_warning("请输入用户名！");
        return;
    }
    if (password === "") {
        $MB.n_warning("请输入密码！");
        return;
    }

   $.ajax({
        type: "post",
        url:  "/auth/login",
        data: {
            "username": username,
            "password": password,
        },
        dataType: "json",
        success: function (data) {
            if (data.code === 0) {
                location.href = '/loginIndex';
            } else {
                $MB.n_warning(data.message);
                $loginButton.html("登录");
            }
        }
    });
}
$("#logoutbtn").on("click", function() {
    $.ajax({
        "url": "/auth/logout",
        "type": "GET",
        "dataType": "json",
        "success": function(data) {
          if(data.code==0)
          {
              location.href = '/';
          }
        },
        "error": function(jqXHR, exception) {
            if (jqXHR.status === 0) {
                alert('Not connect.\n Verify Network.');
            } else if (jqXHR.status == 500) {
                alert(jqXHR.responseJSON.message);
            } else {
                alert('Uncaught Error.\n' + jqXHR.responseText);
            }
        }
    });
});
function regist() {
    var username = $(".two input[id='regusername']").val().trim();
    var password = $(".two input[id='regpassword']").val().trim();
    var cpassword = $(".two input[id='password_confirmation']").val().trim();
    var realname=$(".two input[id='realname']").val().trim();
    var hos=$(".two select[id='hospital']").val();
    var phonenumber=$(".two input[id='phonenumber']").val().trim();
    var regemail=$(".two input[id='regemail']").val().trim();
    var invitvation=$(".two input[id='invitvation']").val().trim();
    if (username === "") {
        $MB.n_warning("用户名不能为空！");
        return;
    }
    if (password === "") {
        $MB.n_warning("密码不能为空！");
        return;
    }
    if (cpassword === "") {
        $MB.n_warning("请再次输入密码！");
        return;
    }
    if (cpassword !== password) {
        $MB.n_warning("两次密码输入不一致！");
        return;
    }
    if(invitvation=== '')
    {
        $MB.n_warning("请输入邀请码");
        return;
    }
    if (hos==0)
    {
        ownner="石河子医院";
    }
    else
    {
        ownner="北京阜外医院";
    }
    var user = {
        "username": username,
        "password": password,
        "hospital": ownner,
        "realname": realname,
        "mobile": phonenumber,
        "email": regemail
    };
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "post",
        url: "/auth/register"+ "?code="+invitvation,
        data: JSON.stringify(user),
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $MB.n_success("注册成功，请登录");
                $(".two input[name='username']").val("");
                $(".two input[name='password']").val("");
                $(".two input[name='cpassword']").val("");
                $('.form-toggle').trigger('click');
            } else {
                $MB.n_warning(r.message);
            }
        },
        error: function(jqXHR, exception) {
            if (jqXHR.status === 0) {
                alert('Not connect.\n Verify Network.');
            } else if (jqXHR.status == 500) {
                alert(jqXHR.responseJSON.message);
            } else {
                alert('Uncaught Error.\n' + jqXHR.responseText);
            }
        }
    });
}

function regcancel() {
    $("#regusername").val("");
    $("#regpassword").val("");
    $("#password_confirmation").val("");
    $("#realname").val("");
    $("#reghospital").val("");
    $("#phonenumber").val("");
    $("#regemail").val("");
    $("#invitvation").val("");
    $("#regMessage").text("");
}

document.onkeyup = function (e) {
    if (window.event)
        e = window.event;
    var code = e.charCode || e.keyCode;
    if (code === 13) {
        login();
    }
};