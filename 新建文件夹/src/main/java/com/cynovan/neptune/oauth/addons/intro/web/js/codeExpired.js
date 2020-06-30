/**
 * Created by Aric on 2017/4/26.
 */

define([], function () {
    var app = angular.module('intro');

    app.controller('CodeExpiredController', ['$scope', function ($scope) {
        $('.intro-footer').html(' <p style="margin-top: -25px;">粤ICP备16118320号</p> @工业设备物联云平台 2016 ');

        var ctrl = this;
        _.extend(ctrl, {
            initialize: function () {
                ctrl.initPageStyle();
            },
            initPageStyle: function () {
                $('.top_li').addClass('grey');
                $('.logo').addClass('grey');
                $('.header').addClass('border');
                $('.header,.intro-wrap').addClass('color1');
            }
        });
        ctrl.initialize();
    }]);
});