<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/user/pwdReset">
                        <div class="form-group has-feedback">
                            <label for="password">原密码</label>
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                <input id="password" class="form-control" placeholder="原密码" maxlength="20" name="oriPwd"
                                       type="password">
                            </div>

                            <span style="color:red;display: none;" class="tips"></span>
                            <span style="display: none;"
                                  class="glyphicon glyphicon-remove form-control-feedback"></span>
                            <span style="display: none;" class="glyphicon glyphicon-ok form-control-feedback"></span>
                        </div>

                        <div class="form-group has-feedback">
                            <label for="passwordConfirm">现密码</label>
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                <input id="passwordConfirm" class="form-control" placeholder="现密码" maxlength="20"
                                       name="nowPwd"
                                       type="password">
                            </div>
                            <span style="color:red;display: none;" class="tips"></span>
                            <span style="display: none;"
                                  class="glyphicon glyphicon-remove form-control-feedback"></span>
                            <span style="display: none;" class="glyphicon glyphicon-ok form-control-feedback"></span>
                        </div>
                        <button type="submit" id="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
<script src="https://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script>
    var regPasswordSpecial = /[~!@#%&=;':",./<>_\}\]\-\$\(\)\*\+\.\[\?\\\^\{\|]/;
    var regPasswordAlpha = /[a-zA-Z]/;
    var regPasswordNum = /[0-9]/;
    var flag = true;
    // 密码匹配
    // 匹配字母、数字、特殊字符至少两种的函数
    function atLeastTwo(password) {
        var a = regPasswordSpecial.test(password) ? 1 : 0;
        var b = regPasswordAlpha.test(password) ? 1 : 0;
        var c = regPasswordNum.test(password) ? 1 : 0;
        return a + b + c;
    }

    $('#password').change(function () {
        if ($(this).val().length < 8) {
            alert("密码太短，不能少于8个字符")
        } else {
            if (atLeastTwo($(this).val()) < 2) {
                alert("密码中至少包含字母、数字、特殊字符的两种")
            }
        }
    });
    $('#passwordConfirm').change(function () {
        if ($(this).val().length < 8) {
            alert("密码太短，不能少于8个字符")
        } else {
            if (atLeastTwo($(this).val()) < 2) {
                alert("密码中至少包含字母、数字、特殊字符的两种")
            }
        }
        if ($(this).val() == $('#password').val()) {
            alert("两次密码不能相同")
        }
    });
    $('#submit').click(function () {
        if (atLeastTwo($('#password').val()) >= 2 && $('#password').val().length >= 8
            && atLeastTwo($('#passwordConfirm').val()) >= 2 && $('#passwordConfirm').val().length >= 8
            && $('#passwordConfirm').val() != $('#password').val()) {
            $('form').submit();
        } else {
            alert("请验证密码后再提交")
            return false;
        }
    });


</script>
</html>