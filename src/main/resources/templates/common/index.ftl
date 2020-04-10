<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>微信订单系统</title>

    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="/sell/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/sell/assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="/sell/assets/css/form-elements.css">
    <link rel="stylesheet" href="/sell/assets/css/style.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- Favicon and touch icons -->
    <link rel="shortcut icon" href="././assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="/sell/assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="/sell/assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="/sell/assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="/sell/assets/ico/apple-touch-icon-57-precomposed.png">

</head>

<body background="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1586340202205&di=7ccbe1d275ced8263ca3f6306f2a84f2&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201311%2F16%2F120430ev666vhzwhtkmwnz.jpg">

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3>Login to our site</h3>
                            <p>Enter your username and password to log on:</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom">
                        <form role="form" action="/sell/user/login" method="post" class="login-form">
                            <div class="form-group">
                                <label class="sr-only" for="form-username">Username</label>
                                <input type="text" name="username" placeholder="Username..."
                                       class="form-username form-control" id="form-username">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input type="password" name="pwd" placeholder="Password..."
                                       class="form-password form-control" id="form-password">
                            </div>
                            <div class="row clearfix">
                                <div class="col-md-6 column">
                                    <button type="button" class="btn btn-default btn-block btn-primary"
                                            onclick="register()">注册
                                    </button>
                                </div>
                                <div class="col-md-6 column">
                                    <button type="submit" class="btn btn-default btn-block btn-success">登录</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Javascript -->
<script src="/sell/assets/js/jquery-1.11.1.min.js"></script>
<script src="/sell/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="/sell/assets/js/jquery.backstretch.min.js"></script>
<script src="/sell/assets/js/scripts.js"></script>

<!--[if lt IE 10]>
<script src="/sell/assets/js/placeholder.js"></script>
<![endif]-->

</body>
<script>
    function register() {
        location.href = "http://dining.natapp1.cc/sell/user/registerPage";
    }

</script>
</html>