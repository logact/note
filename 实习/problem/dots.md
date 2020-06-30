# dot

1. ```js
   /**引入模块
   参数一:导入的依赖
   参数二：导入依赖后要执行的函数
   */
      require(['web/build/entry-build'], function () {
           require(['index/web/js/app'], function () {
               angular.bootstrap(document, ['app']);
           });
       });
   
   ```

2. 

