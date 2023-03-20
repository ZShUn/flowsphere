# ancient


## 简介

ancient是一款无侵入agent流量治理库，主要基于byte-buddy进行开发，致力解决发布风险问题、内部环境多版本并行问题。
让程序员脱离熬夜发版，提高公司内部研发效率。

## 示意图
![binaryTree](../Resources/binaryTree.jpg "binaryTree")

## 支持组件

Spring Cloud Gateway 2.2.5.RELEASE

Spring Cloud Nacos 2.2.9.RELEASE

RocketMQ 4.8

## 使用方式
### JVM参数配置
-javaagent:xxx\agent\ancient-agent-0.0.1-SNAPSHOT.jar

### 异步使用方式
-Dasync.thread.package.path=packageName


## 注意事项
项目开发中......