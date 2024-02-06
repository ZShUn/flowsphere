<div align="center">
	<p></p>
	<p></p>
	<img src="https://github.com/ZShUn/flowsphere/blob/main/Resources/logo.jpg" width = "500" height = "200" alt="图片名称" align=center />
	<h1>基于JavaAgent的流量治理解决方案</h1>
</div>
<div>
	<p></p>
	<p></p>
</div>


## 简介

FlowSphere是基于Java字节码增强技术进行建设，其利用Java字节码增强技术为应用提供流量治理功能，
以解决大规模微服务架构体系环境治理问题、灰度发布问题。

## FlowSphere基础架构图
![](https://github.com/ZShUn/flowsphere/blob/main/Resources/infrastructure.jpg)

基于上图可以看出FlowSphere有两个核心能力：

- 内核层，包含整个FlowSphere基础能力，如：插件注册、动态配置等
- 插件层，插件为应用提供实际的流量治理能力，开发者也可以基于内核层规范轻松实现自己想要的业务插件，
每个插件相互独立，互不干扰。

## FlowSphere组件交互图

![](https://github.com/ZShUn/flowsphere/blob/main/Resources/plugin.jpg)

### 支持组件
- Spring Cloud Gateway 2.2.5.RELEASE
- Spring Cloud Nacos 2.2.9.RELEASE
- RocketMQ 4.8
- OpenFeign
- SpringMvc


### 支持功能

- 标签路由

- 标签百分比路由

## 使用方式
### JVM参数配置


### 异步使用方式


### 组件配置

