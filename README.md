<div align="center">
	<p></p>
	<p></p>
	<img src="https://github.com/ZShUn/flowsphere/blob/main/Resources/logo.jpg" width = "500" height = "200" alt="图片名称" align=center />
	<h1>基于JavaAgent的流量治理解决方案</h1>


[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

</div>





 


## FlowSphere

FlowSphere是基于Java字节码增强技术进行建设，其利用Java字节码增强技术为应用提供流量治理功能，
以解决大规模微服务架构体系环境治理问题、灰度发布问题。

### 基础架构图
![](https://github.com/ZShUn/flowsphere/blob/main/Resources/infrastructure.jpg)

基于上图可以看出FlowSphere有两个核心能力：

- 内核层，包含整个FlowSphere基础能力，如：插件注册、动态配置等
- 插件层，插件为应用提供实际的流量治理能力，开发者也可以基于内核层规范轻松实现自己想要的业务插件，
  每个插件相互独立，互不干扰。

### 组件交互图

![](https://github.com/ZShUn/flowsphere/blob/main/Resources/plugin.jpg)

#### 支持组件
- Spring Cloud Gateway 2.2.5.RELEASE
- Spring Cloud Nacos 2.2.9.RELEASE
- RocketMQ 4.8
- OpenFeign 2.2.5.RELEASE
- SpringMvc 5.0.0.RELEASE
- Dubbo 2.7.22.RELEASE


#### 支持功能

- 标签路由

- 标签百分比路由

## 使用方式
### JVM参数配置

-javaagent:xxx\flowsphere-agent-0.0.1-SNAPSHOT.jar -Dflow.shpere.tag=tagA

### 异步使用方式

-javaagent:xxx\flowsphere-agent-0.0.1-SNAPSHOT.jar -Dflow.shpere.tag=tagA -Dasync.thread.package.path=xxx

### Agent配置

配置详解

| 配置项                                    | 配置值                                      | 配置说明                |
| -------------------------------------- | ---------------------------------------- | ------------------- |
| plugins                                | nacos、rocketmq、springmvc、springcloudgateway、feign、dubbo | 加载生效组件配置项           |
| pluginConfigDataSource.type            | nacos                                    | 组件元数据配置中心           |
| pluginConfigDataSource.pros.dataId     | default                                  | nacos配置中心dataId     |
| pluginConfigDataSource.pros.groupId    | DEFAULT_GROUP                            | nacos配置中心groupId    |
| pluginConfigDataSource.pros.timeout    | 3000                                     | nacos配置中心timeout    |
| pluginConfigDataSource.pros.serverAddr | 127.0.0.1:8848                           | nacos配置中心serverAddr |

配置示例
```
plugins:
  - nacos
  - rocketmq
  - springmvc
  - springcloudgateway
  - feign
  - dubbo
pluginConfigDataSource:
  type: nacos
  pros:
    dataId: default
    groupId: DEFAULT_GROUP
    timeout: 3000
    serverAddr: 127.0.0.1:8848
```
### 组件配置

配置详解

| 组件                   | 配置项                                      |             配置说明              |
| -------------------- | ---------------------------------------- | :---------------------------: |
| rocketmq             | rocketMQConsumerBlackList                |           配置灰度消费者组            |
| spring-cloug-gateway | userWeight.userIds=[xxx]<br/>userWeight.tagWeights[0].tag=xxx<br/>userWeight.tagWeights[0].weight=[xxx] | spring-cloug-gateway用户灰度权重配置项 |
| spring-cloug-gateway | regionWeight.regions=[xxx]<br/>regionWeight.tagWeights[0].tag=xxx<br/>regionWeight.tagWeights[0].weight=[xxx] | spring-cloug-gateway区域灰度权重配置项 |


配置示例
```
{
    "rocketmq":{
        "rocketMQConsumerBlackList":[
            "groupName",
            "groupName1"
        ]
    },
    "springcloudgateway":{
        "regionWeight":{
            "regions":[
                "xxx"
            ],
            "tagWeights":[
                {
                    "tag":"tagA",
                    "weight":0.2
                },
                {
                    "tag":"tagA1",
                    "weight":0.8
                }
            ]
        },
        "userWeight":{
            "userIds":[
                "xxx"
            ],
            "tagWeights":[
                {
                    "tag":"tagA",
                    "weight":0.2
                },
                {
                    "tag":"tagA1",
                    "weight":0.8
                }
            ]
        }
    }
}
```
