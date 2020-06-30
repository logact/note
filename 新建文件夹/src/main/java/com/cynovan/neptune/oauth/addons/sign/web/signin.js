define(['web/base/service', 'web/base/directive'], function () {
    var app = angular.module('signin', ['cnv.services', 'cnv.directives']);

    app.controller('SignInController', ['$scope', 'http', '$element', '$interval', 'dialog', function ($scope, http, $element, $interval, dialog) {
        var ctrl = this;
        $scope.loginMode = 'pwd';
        $scope.entity = {};
        _.extend(ctrl, {
            initialize: function () {
                ctrl.bindEvent();
            },
            bindEvent: function () {
                if (window.location.search.indexOf("error") !== -1) {
                    dialog.noty("账号或密码错误，请重新输入");
                }

                $("body").bind("keypress", function (event) {
                    if (event.which === 13) {
                        ctrl.login();
                    }
                });

                /*把redirect_uri 直接写入在localStorage中*/
                var redirect_uri = ctrl.findGetParameter('redirect_uri');
                $('#redirect_uri').val(redirect_uri);
            },
            findGetParameter: function (parameterName) {
                var result = null,
                    tmp = [];
                location.search
                    .substr(1)
                    .split("&")
                    .forEach(function (item) {
                        tmp = item.split("=");
                        if (tmp[0] === parameterName) result = decodeURIComponent(tmp[1]);
                    });
                return result;
            },
            checkUserName: function (name) {
                let mobileRegex = /(^0?1[3|4|5|6|7|8|9]\d{9}$)/;
                let emailRegex = /^[_\.0-9a-z-]+@([0-9a-z][0-9a-z-]+\.){1,4}[a-z]{2,3}$/;
                if ($scope.loginMode === 'msg') {
                    return mobileRegex.test(name);
                } else {
                    return mobileRegex.test(name) || emailRegex.test(name);
                }
            },
            checkPwdCode: function () {
                if ($scope.loginMode === 'msg') {
                    //    暂时只判断是否为空
                    if (_.isEmpty($scope.entity.code)) {
                        dialog.noty('请输入验证码');
                        return false;
                    }
                } else {
                    if (_.isEmpty($scope.entity.password)) {
                        dialog.noty('请输入密码');
                        return false;
                    }
                }
                return true;
            },
            login: function () {
                if (!ctrl.checkUserName($scope.entity.username)) {
                    dialog.noty('请输入正确的手机号/邮箱');
                    return false;
                }
                if (!ctrl.checkPwdCode()) {
                    return false;
                }
                $("#signInForm").submit();
            },
            changeLoginMode: function (type) {
                $scope.loginMode = type;
            },
            calcResendState: function () {
                if (!ctrl.checkUserName($scope.entity.username)) {
                    dialog.noty('请输入正确的手机号');
                    return false;
                }
                var times = 60;
                var button = $('.get-code');
                button.css({
                    'pointer-events': "none",
                    'color': '#9c9c9c'
                });
                var interval = $interval(function () {
                    if (times > 0) {
                        times--;
                        button.text(`再次发送(${times})`);
                    } else {
                        if (interval) {
                            $interval.cancel(interval);
                            button.text('获取验证码');
                            button.css({
                                'pointer-events': "all",
                                'color': '#488EFF'
                            });
                        }
                    }
                }, 1000);
            }
        });
        ctrl.initialize();
    }]);
});
