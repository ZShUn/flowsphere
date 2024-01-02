# flowsphere


## 简介

flowsphere是一款无侵入agent流量治理库，主要基于byte-buddy进行开发，致力解决发布风险问题、内部环境多版本并行问题。
让程序员脱离熬夜发版，提高公司内部研发效率。

## 架构图
![](https://github.com/ZShUn/ancient/blob/main/Resources/Architecture.png)

## 支持组件

- Spring Cloud Gateway 2.2.5.RELEASE

- Spring Cloud Nacos 2.2.9.RELEASE

- RocketMQ 4.8

## 支持功能

- 版本路由

- RocketMQ队列路由

## 使用方式
### JVM参数配置
-javaagent:xxx\agent\ancient-agent-0.0.1-SNAPSHOT.jar

### 异步使用方式
-Dasync.thread.package.path=packagePath

### RocketMQ使用方式
```
{
  "rocketMQEntity":{
    "topic":"TopicTest",
    "producerEntityList":[
      {
        "queueList":[ //Topic发送的队列
          0,
          1
        ]
      }
    ],
    "consumerEntityList":[
      {
        "groupName":"CID_JODIE_2",
        "queueList":[ //消费者消费队列
          3,
          4
        ]
      }
    ]
  }
}
```

## 版本规划
![](https://github.com/ZShUn/ancient/blob/main/Resources/Planning.png)