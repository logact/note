define([], function () {
    var app = angular.module('intro');

    app.controller('JoinCompanyController', ['$scope', 'http', '$location', '$timeout', '$interval',
        function ($scope, http, $location, $timeout, $interval) {
            $('.intro-footer').html(' <p style="margin-top: -25px;">粤ICP备16118320号</p> @工业设备物联云平台 2016 ');

            var code = $location.search()['code'];

            $scope.datas = [];

            $scope.options = {
                text: '',
                entityName: 'Company',
                columns: [{
                    name: 'name',
                    title: '名称',
                }, {
                    name: 'email',
                    title: '邮箱',
                }, {
                    name: 'mobile',
                    title: '手机'
                }, {
                    name: '操作',
                    title: '操作'
                }]
            };

            $scope.search = function () {
                console.log('search text ' + $scope.options.text);
                $('.company_search').addClass("loading");

                http.post('register/searchCompany', {
                    keyword: $scope.options.text
                }).success(function (result) {
                    if (result.success) {
                        $scope.datas = result.datas.data;
                    }
                })
            }

            $scope.joinCompany = function (index) {
                http.post('register/applyCompany', {
                    id: $scope.datas[index].id,
                    code: code
                }).success(function (result) {
                    console.log('已申请');
                    $timeout(function () {
                        $location.path('/login');
                    }, 1000);
                })
            }

        }]);
});