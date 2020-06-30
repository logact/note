define([], function () {
    var app = angular.module('intro');

    app.controller('InviteJoinCompanyController', ['$scope', 'http', '$location', '$timeout', '$interval', 'dialog', 'util',
        function ($scope, http, $location, $timeout, $interval, dialog, util) {
            $('.intro-footer').html(' <p style="margin-top: -25px;">粤ICP备16118320号</p> @工业设备物联云平台 2016 ');

            var ctrl = this;
            var key = $location.search()['key'] || '';
            $scope.entity = {}
            _.extend(ctrl, {
                initialize: function () {
                    ctrl.initPageStyle();
                    ctrl.loadInviteInfo();
                },
                initPageStyle: function () {
                    $('.top_li').addClass('grey');
                    $('.logo').addClass('grey');
                    $('.header').addClass('border');
                    $('.header,.intro-wrap').addClass('color1');
                },
                loadInviteInfo: function () {
                    if (key) {
                        http.post('register/getInviteInfo', {
                            key: key
                        }).success(function (result) {
                            if (result.success) {
                                $scope.info = result.datas.info;
                            } else {
                                // 跳转到登陆
                                $timeout(function () {
                                    $location.url('/login');
                                    util.apply($scope);
                                }, 1500)
                            }
                        })
                    }
                },
                checkPassword: function () {
                    if ($scope.entity.password.length < 6) {
                        dialog.noty('密码长度至少6位');
                        return false;
                    }
                    if ($scope.entity.password !== $scope.entity.password1) {
                        dialog.noty('两次输入的密码不一致');
                        return false;
                    }
                    return true;
                },
                accept: function () {
                    var flag = ctrl.checkPassword();
                    if (flag === false) {
                        return false;
                    }
                    http.post('register/acceptInvite', {
                        key: key,
                        pwd: $scope.entity.password
                    }).success(function (result) {
                        if (result.success) {
                            // 跳转到登陆
                            dialog.noty('加入团队成功，2秒后自动跳转到登录页面!');
                            $timeout(function () {
                                $location.url('/login');
                            }, 2000)
                        } else {
                            dialog.noty('未知错误, 请稍后再试');
                        }
                    })
                }
            });
            ctrl.initialize();
        }]);
});