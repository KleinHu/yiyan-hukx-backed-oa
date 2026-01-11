# 易研后端示例项目
## 易研基础服务说明

1. 目前项目目录层级采用父子maven项目结构
2. 以yiyan-demo为父项目，则会有yiyan-demo-api、yiyan-demo-biz、yiyan-demo-server 3个子模块。
3. yiyan-demo为父级项目，负责依赖版本统一管理
4. yiyan-demo-api：子项目之一，对外暴露api接口，供其他服务调用
5. yiyan-demo-biz：子项目之一，具体业务逻辑实现，包括服务启动类，依赖yiyan-demo-api
6. yiyan-demo-server：子项目之一，服务器启动类
7. code-generator：代码生成器模块，包含一个测试类，给出代码生成使用方法示例。非业务必须模块

后端工程目录：

```
yiyan-demo
    |-- Dockerfile
    |-- code-generator
    |-- pom.xml
    |-- yiyan-demo-api  对外暴露的api接口模块
    |-- yiyan-demo-biz  业务逻辑模块
    `-- yiyan-demo-server  服务启动模块
```

```
yiyan-demo-biz下，代码目录结构，其中operatelog为具体业务
|-- java
|   `-- com
|       `-- cac
|           `-- demo
|               |-- api   api实现类
|               |   `-- operatelog
|               |-- controller  外部接口暴露
|               |   `-- operatelog
|               |-- convert  实体转化
|               |   `-- operatelog
|               |-- dao  数据操作
|               |   `-- operatelog
|               |-- entity  数据库映射实体
|               |   `-- operatelog
|               |-- service  业务层
|               |   `-- operatelog
|               `-- vo  前后端交互数据实体
|                   `-- operatelog
`-- resources
    `-- mapper  mybatis的xml文件
        `-- operatelog

```


## 依赖版本

- spring boot：2.3.12.RELEASE
- spring cloud：Hoxton.SR12
- spring cloud alibab：2.2.5.RELEASE



## 技术栈

| **功能**         | **组件**                   |
| ---------------- | -------------------------- |
| 服务注册与发现   | nacos                      |
| 配置中心         | nacos                      |
| API网关          | spring cloud gateway       |
| 服务调用         | openFegin                  |
| 服务熔断以及降级 | sentinel                   |
| 链路追踪         | SkyWalking                 |
| 分布式事务       | Seata                      |
| 开发框架         | spring boot                |
| 持久层框架       | mybatis/mybatis plus       |
| 分布式缓存       | redis                      |
| 消息队列         | rabbitmq                   |
| 数据库           | MySQL/ORACLE/DM            |
| 日志             | ELK                        |
| 接口文档         | swagger                    |
| 工具类           | guava/hutool               |
| 数据库连接池     | Hikari                     |
| 微服务监控       | Spring Boot Actuator+Admin |
| 定时任务         | xxl-job                    |
| 代码生成         | freemarker模板引擎         |
| 属性拷贝         | mapstruct                  |
| 文件存储服务     | MinIO                      |

前端使用Vue3框架进行开发

| 功能               | 组件           |
| ------------------ | -------------- |
| Vue框架            | Vue3           |
| 开发与构建工具     | Vite           |
| 基础组件库         | arco-design    |
| JavaScript的超集   | TypeScript     |
| Vue存储库          | Pinia          |
| Vue路由            | vue-router     |
| 基础组件库（Vue2） | ant-design-vue |
| 补充组件库（Vue2） | element-ui     |
| Vue存储库（Vue2）  | vuex           |
| 富文本编辑器       | WangEditor     |
| 流程图工具库       | bpm.js         |
| 网页代码编辑器     | monaco-editor  |



# 附录

## 私库

```xml
<!-- Maven私服 -->
<repositories>
    <repository>
        <id>cac-public</id>
        <name>cac-public</name>
        <url>http://10.1.1.33:8081/repository/cac_repo_group/</url>
        <snapshots>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```



## 代码生成模板说明

- controller.java：控制器层
- convert.java ：数据实体属性拷贝
- VO.java：与前端数据传输实体
- query.java: 数据查询实体
- entity.java：数据库对象映射
- Excel.java：excel导出实体类
- mapper.java：dao对象
- mapper.xml：mybatis的mapper文件
- service.java：业务层，接口
- serviceImpl.java：业务层，具体实现

---




