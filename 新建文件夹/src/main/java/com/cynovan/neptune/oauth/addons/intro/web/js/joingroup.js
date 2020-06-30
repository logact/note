define([], function () {
    var app = angular.module('intro');

    app.controller('JoinGroupController', ['$scope', '$location', 'http', 'dialog', 'util', '$timeout',
        function ($scope, $location, http, dialog, util, $timeout) {
            $('.intro-footer').html(' <p style="margin-top: -25px;">粤ICP备16118320号</p> @工业设备物联云平台 2016 ');

            var ctrl = this;
            $scope.datas = [];
            var code = $location.search()['code'];
            var from = $location.search()['from'];
            $scope.checker = {};
            $scope.company = {
                name: '',
                type: 'personal'
            };
            $scope.invite = {
                code: '',
                mobile: false
            };
            _.extend(ctrl, {
                initialize: function () {
                    ctrl.initPageStyle();
                    ctrl.loadCode();
                },
                initPageStyle: function () {
                    $('.top_li').addClass('grey');
                    $('.logo').addClass('grey');
                    $('.header').addClass('border');
                    $('.header,.intro-wrap').addClass('color1');
                },
                loadCode: function () {
                    http.get('register/toJoinCompanyChecker', code).success(function (result) {
                        ctrl.checkRegister(result);
                    });
                },
                checkRegister: function (result) {
                    $scope.invite.hasCode = _.get(result, 'datas.invited', false);

                    var type = _.get(result, 'datas.type', '');
                    if (type === 'expired') {
                        $location.url('/code_expired');
                        util.apply($scope);
                    } else {
                        $scope.checker = _.get(result, 'datas.checker', {});
                    }
                },
                selfCompany: function () {
                    http.post('register/checkCompanyApply', {
                        code: code
                    }).success(function (result) {
                        if (result.success) {
                            ctrl.createCompany();
                        } else {
                            var applying = _.get(result, 'datas.applying_company', '');
                            dialog.confirm('您已申请加入【' + applying + '】团队，是否继续操作？').on('success', function () {
                                ctrl.createCompany();
                            })

                        }
                    })
                },
                hideCompanyApply: function () {
                    /* 标记用户的【团队申请数据】为（已加入其他团队）*/
                    http.post('register/hideCompanyApply', {
                        code: code
                    }).success(function (result) {
                        if (result.success) {
                            console.log('Hide companyApplies successfully');
                        }
                    })

                },
                createCompany: function () {
                    if ($scope.company.name.length <= 3) {
                        dialog.noty('团队名称不能少于三个字符!');
                        return false;
                    }

                    var button = $('.regbtn');
                    button.button('loading');

                    var company = _.omit($scope.company, ['$$hashKey']);
                    company.code2 = code;
                    http.post('register/createCompany', company).success(function (result) {
                        button.button('reset');
                        if (result.success) {
                            button.addClass('hide');
                            ctrl.hideCompanyApply();    // 标记加入团队申请
                            ctrl.timeoutToLogin();
                        } else {
                            $('.name').addClass("error");
                            dialog.noty('团队名称已存在，请修改');
                        }
                    });
                },
                timeoutToLogin: function () {
                    var element = $('.create_success');
                    var count = 3;
                    element.text('团队创建成功,' + count + '秒后跳转到登录界面...');
                    count--;
                    var interval = setInterval(function () {
                        if (count >= 0) {
                            element.text('团队创建成功,' + count + '秒后跳转到登录界面...');
                        } else {
                            window.clearInterval(interval);
                            ctrl.goToLogin();
                            if (!$scope.$$phase) {
                                $scope.$apply();
                            }
                        }
                        count--;
                    }, 1000);
                },
                joinCompany: function (index) {
                    var company = $scope.datas[index];
                    http.post('register/applyCompany', {
                        id: company.id,
                        code: code
                    }).success(function (result) {
                        dialog.noty(result.messages);
                    })
                },
                search: function () {
                    var text = $('.searchGroup').val() || '';
                    $('.company_search').addClass("loading");

                    var query = {
                        limit: 5
                    };
                    if (text) {
                        query = {
                            '$or': [{
                                'name': {
                                    '$regex': text,
                                    '$options': 'i'
                                }
                            }]
                        }
                    }
                    query = JSON.stringify(query);
                    query = encodeURIComponent(query);
                    http.post('register/searchCompany', {
                        query: query
                    }).success(function (result) {
                        $scope.datas = _.get(result, 'datas.list', []);
                    });
                },
                checkInviteCode: function () {
                    http.post('register/confirmInviteCode', {
                        code: $scope.invite.code
                    }).success(function (result) {
                        if (result.success) {
                            dialog.noty('加入团队成功，2秒后跳转到登录界面...');
                            $timeout(function () {
                                ctrl.goToLogin();
                            }, 2000);
                        } else {
                            dialog.noty('邀请码错误，请稍后再试');
                        }
                    });
                },
                goToLogin: function () {
                    window.location.pathname = "/index";
                }
            });
            ctrl.initialize();
        }]);
});