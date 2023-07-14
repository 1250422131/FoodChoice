# FoodChoice

食选，解决生活中每天吃饭，吃什么，做什么，怎么做的问题，此项目也是我对JetpackCompose的MVI架构学习的一次实践。

## 项目介绍

此项目为JetpackCompose的一个学习项目，主要对学到的一些简单知识的应用，同时对程序模块化进行一次进一步的尝试。

主要采用了MVI设计，实现了离线数据加载，网络加载，依赖注入，导航管理等，使其成为一个稳定可用的程序。

大体框架主要参考了谷歌的[nowinandroid](https://github.com/android/nowinandroid/)（只实现了其中的一小部分）。

## 程序现阶段框架

![](http://message.biliimg.com/bfs/im/20a058ef1cb3d919269fa15cf0d2d60a351201307.png)

其中core层主要对公共依赖，公共UI组件，全局主题样式，网络请求，数据持久性以及数据适配器进行了分模块。

这样看是杀鸡用了宰牛刀，但FoodChoice主要是对模块化进行学习，另外这样的分模块其实也是有必要的，这让我的代码更有拓展性和维护性，使得项目代码比较茁壮。

而feature层主要对各个功能进行了模块化，只是由于我们用了Compose可用用一个activity来完成各个界面的展示，这也是为什么
没有用**服务发现**，或者**路由组件库**什么的。


## 项目进度
- [x] 实现Cook，食物选择功能
- [x] 各模块适当采用依赖注入
- [ ] 使用统一版本管理（Version Catalog）
- [ ] 考虑对数据同步改为WorkManager任务
- [ ] 实现食物抽签功能


## 参考项目
首页食物选择参考了[Cook](https://github.com/YunYouJun/cook)，[Web项目源地址](https://cook.yunyoujun.cn)
FoodChoice利用Compose进行了一次复刻，其中数据都来自[Cook](https://github.com/YunYouJun/cook)项目。
