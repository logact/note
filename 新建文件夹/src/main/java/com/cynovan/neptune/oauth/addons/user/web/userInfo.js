define(['web/base/service', 'web/base/directive'], function () {
    var app = angular.module('userInfo', ['cnv.services', 'cnv.directives']);

    app.controller('UserInfoController', ['$scope', function ($scope) {
        var ctrl = this;
        _.extend(ctrl, {
            initialize: function () {

            }
        });

        ctrl.initialize();
    }]);
});