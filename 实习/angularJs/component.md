# component 

## 问题

1. 使用如果从一个index 页面中直接通过component 方法的templateUrl关联另一个组件那么这个就会受到跨域问题的影响而无法访问，因为angular js在导入这个组件的时候实际上是调用一个get的异步请求
2. 如果直接另一个组件的js文件那么console不会报错，但是没有效果显示。



