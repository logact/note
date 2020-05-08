define(function(){
    localStorage.setItem('num',10)
    var app = angular.module('app',[])
    app.controller('IndexController',['$scope',function($scope){
        let ctrl = this
        $scope.users=[{
            account:'1342',
            username:'logact',
            password:"fasdfas",
            sex:'man'
        },{
            account:'1342',
            username:'logact',
            password:"fasdfas",
            sex:'man'
        }]
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
            add:function(){
                localStorage.setItem('text',$scope.text)
            },
            read:function(){
                $scope.text=localStorage.getItem('text')
            }
        })
    }])
})