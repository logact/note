define(['module1','module2'],()=>{
    var app = angular.module('app',[])
    app.controller('IndexController',['$scope',($scope)=>{
        $scope.moduleIndex='i am moduleIndex '
    }])
})