define(function(){
    var app = angular.module('app',[])
    app.controller('IndexController',['$scope',function($scope){
        let ctrl = this
        $scope.num=localStorage.getItem('num')
        $scope.canWrite=true
        $scope.$watch('text',function(newValue,oldValue){
            var strToShow=''
            if(typeof(newValue)!='undefined'&& newValue.length>10){
                strToShow=newValue.substr(0,10)
                $scope.text=strToShow
                $scope.num=0
            }else if(typeof(newValue)=='undefined'){
                $scope.num=10
            }else{
                $scope.num=10-newValue.length
            }
            console.log(newValue,oldValue)
        })    
        _.extend(ctrl,{
            // 也可以配合ng-change 命令但是这样的话使用copy会使字数限制失效
            // numChange:function(){
            //     console.log($scope.text)
            //     if(localStorage.getItem('num')==0){
            //         $scope.canWrite=false
            //     }
            // },
            delete:function(){ 
                $scope.text=''
            },
            save:function(){
                localStorage.setItem('text',$scope.text)
            },
            read:function(){
                $scope.text=localStorage.getItem('text')
            }
        })
    }])
})