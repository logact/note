/**
 * Created by azure on 22/11/2016.
 */
define(['mousetrap'], function () {
    var app = angular.module('intro');
    app.controller('RetrievePwdController', ['$scope', 'http', '$interval', '$timeout', 'dialog', '$location', 'validator',
        function ($scope, http, $interval, $timeout, dialog, $location, validator) {
            $('.intro-footer').html(' <p style="margin-top: -25px;">粤ICP备16118320号</p> @工业设备物联云平台 2016 ');

            var ctrl = this;

            $scope.info = {
                type: '',
                account: '',
                code: '',
                pwd1: '',
                pwd2: ''
            }
            _.extend(ctrl, {
                initialize: function () {
                    ctrl.initPageStyle();
                    ctrl.bindEvent();
                },
                bindEvent: function () {
                    Mousetrap.bind(['enter'], function () {
                        if ($('.confirmBtn').prop('disabled') === false) {
                            ctrl.confirmModify();
                        }
                    });
                },
                initPageStyle: function () {
                    $('.top_li').addClass('grey');
                    $('.logo').addClass('grey');
                    $('.header').addClass('border');
                    $('.header,.intro-wrap').addClass('color1');
                },
                checkRequired: function (value, message) {
                    if (!value) {
                        dialog.noty(message);
                        return false;
                    }
                },
                checkAccount: function () {
                    var flag = ctrl.checkRequired($scope.info.account, '请输入有效的邮箱或手机号码');
                    if (flag === false) {
                        $('.username-box').addClass('has-error');
                        return false;
                    }
                    var emailChecked = validator.email($scope.info.account);
                    var mobileChecked = validator.mobile($scope.info.account);
                    if (!emailChecked && !mobileChecked) {
                        $('.username-box').addClass('has-error');
                        dialog.noty('请输入有效的邮箱或手机号码');
                        return false;
                    }
                    return true;
                },
                sendCode: function () {
                    var flag = ctrl.checkAccount();
                    if (flag === false) {
                        return false;
                    }
                    console.log("132123");
                    http.post('retrievePwd/sendCode', {
                        account: $scope.info.account
                    }).success(function (result) {
                        dialog.noty(result.messages);
                        if (result.success) {
                            ctrl.calcResendState();
                        }
                    })
                },
                calcResendState: function () {
                    var times = 60;
                    var button = $('.sendCodeBtn');
                    button.prop('disabled', true);
                    var interval = $interval(function () {
                        if (times > 0) {
                            times--;
                            button.text(`再次发送(${times})`);
                        } else {
                            if (interval) {
                                $interval.cancel(interval);
                                button.text('获取验证码').prop('disabled', false);
                            }
                        }
                    }, 1000);
                },
                checkModify: function () {
                    $('.retrieveform .form-group').removeClass('has-error');
                    var flag = ctrl.checkAccount();
                    if (!$scope.info.code) {
                        $('.code-box').addClass('has-error');
                        dialog.noty('请输入验证码');
                        return false;
                    }
                    if (!$scope.info.pwd1 || $scope.info.pwd1.length < 6) {
                        dialog.noty('密码长度至少6位');
                        $('.password-box').addClass('has-error');
                        return false;
                    }
                    if ($scope.info.pwd1 !== $scope.info.pwd2) {
                        dialog.noty('两次输入的密码不一致');
                        $('.password-box').addClass('has-error');
                        return false;
                    }
                    return true;
                },
                confirmModify: function () {
                    var flag = ctrl.checkModify();
                    if (flag === false) {
                        return false;
                    }

                    $('.confirmBtn').addClass('loading').prop('disabled', true);
                    http.post('retrievePwd/confirmModify', {
                        account: $scope.info.account,
                        pwd: $scope.info.pwd1,
                        code: $scope.info.code
                    }).success(function (data) {
                        dialog.noty(data.messages);
                        if (data.success == true) {
                            $timeout(function () {
                                $location.path('/login');
                            }, 2000);
                        } else {
                            $('.confirmBtn').prop('disabled', false);
                        }
                    });
                }
            });
            ctrl.initialize();
        }]);
});