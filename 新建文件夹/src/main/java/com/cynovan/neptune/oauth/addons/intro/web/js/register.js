define(['mousetrap'], function () {
    var app = angular.module('intro');
    app.controller('RegisterController', ['$scope', 'http', '$location', 'util', 'validator', '$compile', 'dialog',
        function ($scope, http, $location, util, validator, $compile, dialog) {

            $scope.user = {
                username: '',
                password: '',
                password1: '',
                name: '',
                agree_term: true
            }

            var ctrl = this;
            _.extend(ctrl, {
                initialize: function () {
                    ctrl.initPageStyle();
                    ctrl.bindEvent();
                },
                bindEvent: function () {
                    Mousetrap.bind(['enter'], function () {
                        if ($('.regbtn').prop('disabled') === false) {
                            ctrl.register();
                        }
                    });
                },
                initPageStyle: function () {
                    $('.header,.intro-wrap').addClass('color1');
                    $('.header').addClass('border');
                },
                checkRequired: function (value, message) {
                    if (!value) {
                        dialog.noty(message);
                        return false;
                    }
                },
                checkRegister: function () {
                    $('.register_content .form-group').removeClass('has-error');
                    var flag = ctrl.checkRequired($scope.user.username, '请输入有效的邮箱或手机号码');
                    if (flag === false) {
                        $('.username-box').addClass('has-error');
                        return false;
                    }
                    var emailChecked = validator.email($scope.user.username);
                    var mobileChecked = validator.mobile($scope.user.username);
                    if (!emailChecked && !mobileChecked) {
                        $('.username-box').addClass('has-error');
                        dialog.noty('请输入有效的邮箱或手机号码');
                        return false;
                    }

                    flag = ctrl.checkRequired($scope.user.name, '请输入姓名');
                    if (flag === false) {
                        $('.name-box').addClass('has-error');
                        return false;
                    }
                    if (!$scope.user.password || $scope.user.password.length < 6) {
                        dialog.noty('密码长度至少6位');
                        $('.password-box').addClass('has-error');
                        return false;
                    }
                    if ($scope.user.password !== $scope.user.password1) {
                        dialog.noty('两次输入的密码不一致');
                        $('.password-box').addClass('has-error');
                        return false;
                    }
                    $scope.user.agree_term = $('input[name="agree_term"]').prop('checked');
                    if (!$scope.user.agree_term) {
                        dialog.noty('请同意相关服务条款');
                        return false;
                    }
                    return true;
                },
                register: function () {
                    var checked = ctrl.checkRegister();
                    if (checked === false) {
                        return false;
                    }
                    $('.regbtn').button('loading').prop('disabled', true);
                    var user = util.encodeJSON($scope.user);
                    http.post('register/registerUser', {
                        user: user
                    }).success(function (result) {
                        $('.regbtn').button('reset');
                        if (result.success) {
                            if (result.datas.code) {
                                let params = {
                                    code: result.datas.code
                                };
                                let from = $location.
                                search()['from'];
                                if (from === 'open-platform') {
                                    params.from = 'open-platform'
                                }
                                $location.path('/cfmreg').search(params);
                            }
                        } else {
                            $('.regbtn').prop('disabled', false);
                            $('.register_error').slideDown('fast')
                        }
                    });
                }
            })
            ;
            ctrl.initialize();
        }]);
});