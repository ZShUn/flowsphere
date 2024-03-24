<div align="center">
	<p></p>
	<p></p>
	<img src="https://github.com/ZShUn/flowsphere/blob/main/images/logo.jpg" width = "500" height = "200" alt="图片名称" align=center />
	<h1>基于JavaAgent的流量治理解决方案</h1>


[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

</div>


## 简介
FlowSphere是基于bytebuddy字节码增强技术进行建设，利用字节码增强技术为微服务提供全链路流量治理能力， 以解决大规模微服务之能力问题，FlowSphere构建了非侵入、高性能、插件化核心框架，同时支持配置中心，动态修改相关配置。



![](https://github.com/ZShUn/flowsphere/blob/main/images/agent.png)

<div align="center">
	<p></p>
	<p></p>
	<img src="https://github.com/ZShUn/flowsphere/blob/main/images/agent.png" width = "1500" height = "350" alt="图片名称" align=center />
	<h1>基于JavaAgent的流量治理解决方案</h1>


[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

</div>

## 功能列表
### HTTP、RPC

标签路由

权重路由

### MQ

SQL92灰度消费

Queue灰度消费

### JOB

节点漂移

## 环境要求
### 语言环境

- Java 8

### 组件版本
- Spring Cloud Gateway 2.2.5.RELEASE
- Spring Cloud Nacos 2.2.9.RELEASE
- RocketMQ 4.8
- OpenFeign 2.2.5.RELEASE
- SpringMvc 5.0.0.RELEASE
- Dubbo 2.7.22.RELEASE


## 使用方式
### JVM参数配置

-javaagent:xxx\flowsphere-agent-0.0.1-SNAPSHOT.jar -Dflow.shpere.tag=tagA

### 异步使用方式

-javaagent:xxx\flowsphere-agent-0.0.1-SNAPSHOT.jar -Dflow.shpere.tag=tagA -Dasync.thread.package.path=xxx

