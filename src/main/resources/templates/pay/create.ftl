<script>
    function onBridgeReady() {
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId": "${payResponse.appId}",     //公众号名称，由商户传入
                "timeStamp": "${payResponse.timeStamp}",         //时间戳，自1970年以来的秒数
                "nonceStr": "${payResponse.nonceStr}", //随机串
                "package": "${payResponse.packAge}",
                "signType": "MD5",         //微信签名方式：
                "paySign": "${payResponse.paySign}" //微信签名
            },
            function (res) {
                location.href = "${returnUrl}";
            }
        );
    }

    if (typeof WeixinJSBridge == "undefined") {
        if (document.addEventListener) {
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        } else if (document.attachEvent) {
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    } else {
        onBridgeReady();
    }
</script>