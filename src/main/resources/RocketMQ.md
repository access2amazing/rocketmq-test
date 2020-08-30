# RocketMQ

## 1 本地部署

### 1.1 window环境

#### 1.1.1 环境准备

- 下载zip安装包
  - https://www.apache.org/dyn/closer.cgi?path=rocketmq/4.7.1/rocketmq-all-4.7.1-bin-release.zip
- 解压到指定目录
  - D:\soft\rocketmq
- 环境变量设置
  - ROCKETMQ_HOME="D:\soft\rocketmq"
  - NAMESRV_ADDR="localhost:9876"

#### 1.1.2 启动NameServer

- 运行cmd

  - .\bin\mqnamesrv.cmd

- 正常结果

  - ```
    Java HotSpot(TM) 64-Bit Server VM warning: Using the DefNew young collector with the CMS collector is deprecated and will likely be removed in a future release
    Java HotSpot(TM) 64-Bit Server VM warning: UseCMSCompactAtFullCollection is deprecated and will likely be removed in a future release.
    The Name Server boot success. serializeType=JSON
    ```

#### 1.1.3 启动BrokerServer

- 运行cmd

  - .\bin\mqbroker.cmd

- 正常结果

  - ```
    The broker[NJ-PF12MF0X, 192.168.26.49:10911] boot success. serializeType=JSON and name server is localhost:9876
    ```

#### 1.1.4 启动rocketmq-console

- 下载代码
  - git clone https://github.com/apache/rocketmq-externals.git
- 添加依赖
  - 根据实际加依赖
- 打包编译
  - mvn clean package -Dmaven.test.skip=true
- 运行jar
  - java -jar rocketmq-console-ng-2.0.0.jar
- 启动后执行定时任务抛NPE
  - org.apache.rocketmq.console.task.DashboardCollectTask
    - org.apache.rocketmq.console.service.client.MQAdminExtImpl
      - org.apache.rocketmq.console.aspect.admin.MQAdminAspect#aroundMQAdminMethod
        - org.apache.rocketmq.console.service.client.MQAdminInstance#initMQAdminInstance
          - org.apache.rocketmq.tools.admin.DefaultMQAdminExt#start
            - org.apache.rocketmq.tools.admin.DefaultMQAdminExtImpl#start
              - org.apache.rocketmq.client.impl.MQClientManager#getOrCreateMQClientInstance
                - org.apache.rocketmq.client.impl.factory.MQClientInstance#MQClientInstance (Line: 106)
                  - org.apache.rocketmq.client.impl.MQClientAPIImpl#MQClientAPIImpl (Line: 165)
                    - org.apache.rocketmq.remoting.netty.NettyRemotingClient#NettyRemotingClient (Line: 84)

#### 1.1.5 编写producer



#### 1.1.6 编写consumer

