define(['mousetrap'], function () {
    var app = angular.module('intro');
    app.controller('CfmRegController', ['$scope', 'http', '$location', '$timeout', '$interval', 'util', 'dialog',
        function ($scope, http, $location, $timeout, $interval, util, dialog) {
            $('.intro-footer').html(' <p style="margin-top: -25px;">粤ICP备16118320号</p> @工业设备物联云平台 2016 ');
            var ctrl = this;
            var code = $location.search()['code'];
            $scope.checker = {};
            $scope.info = {
                captcha: ''
            };
            _.extend(ctrl, {
                initialize: function () {
                    ctrl.initPageStyle();
                    ctrl.initCheckerState();
                    
                },
                bindEvent: function () {
                    if ($scope.checker.mobile) {
                        Mousetrap.bind(['enter'], function () {
                            if ($('.confirmBtn').prop('disabled') === false) {
                                ctrl.confirmRegister();
                            }
                        });
                    }
                },
                initCheckerState: function () {
                    http.get('register/loadCheckerData', code).success(function (result) {
                        var checker = result.datas.checker;
                        $scope.checker = checker;
                        if (checker.email) {
                            checker.mail_url = 'http://mail.' + checker.email.substring(checker.email.indexOf('@') + 1);
                        } else {
                            checker.joingroup_url = 'joingroup?code=' + checker.code2;
                        }
                        $timeout(function ()    {
                            ctrl.calcResendState();
                        }, 100);
                        util.apply($scope);
                        ctrl.bindEvent();
                    });
                },
                resendRegisterEmail: function () {
                    $('.resendbtn').prop('disabled', true);
                    http.get('register/resendRegisterEmail', code).success(function () {
                        $timeout(function () {
                            ctrl.calcResendState();
                        }, 100);
                    });
                },
                calcResendState: function () {
                    var button = $('.resendbtn');
                    button.prop('disabled', true);
                    var times = 60;

                    button.text('再次发送(' + times + ')');
                    var interval = $interval(function () {
                        if (times > 0) {
                            times--;
                            button.text('再次发送(' + times + ')');
                        } else {
                            if (interval) {
                                $interval.cancel(interval);
                                button.text('再次发送').prop('disabled', false);
                            }
                        }
                    }, 1000);
                },
                confirmRegister: function () {
                    var codeRegex = /(^[0-9]{4}$)/;
                    if (!codeRegex.test($scope.info.captcha)) {
                        dialog.noty('请输入正确的验证码!');
                        return false;
                    }
                    $('.confirmBtn').button('loading').prop('disabled', true);

                    http.post('register/checkCaptcha', {
                        code: $scope.checker.code1,
                        checkcode: $scope.info.captcha
                    }).success(function (result) {
                        $('.confirmBtn').button('reset');
                        $('.confirmBtn').prop('disabled', false);
                        if (result.success) {
                            let params = {
                                code: result.datas.code2
                            };
                            let from = $location.search()['from'];
                            if (from === 'open-platform') {
                                params.from = 'open-platform'
                            }
                            $location.path('/joingroup').search(params);
                        } else {
                            dialog.noty('验证码错误');
                        }
                    })
                },
                initPageStyle: function () {
                    $('.header,.intro-wrap').addClass('color1');
                    $('.header').addClass('border');
                }
            });
            ctrl.initialize();
        }]);
});