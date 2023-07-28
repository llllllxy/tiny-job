# Tiny Job
# 轻量级定时任务调度系统
![SpringBoot](https://img.shields.io/badge/springboot-2.6.11-green.svg?style=flat-square)
<a href='https://gitee.com/leisureLXY/tiny-job/stargazers'><img src='https://gitee.com/leisureLXY/tiny-job/badge/star.svg?theme=dark' alt='star'></img></a>
<a href='https://gitee.com/leisureLXY/tiny-job/members'><img src='https://gitee.com/leisureLXY/tiny-job/badge/fork.svg?theme=dark' alt='fork'></img></a>

> 一个基于SpringBoot+Quartz的的轻量级定时任务调度系统

## 主要技术选型

1、后端：
- SpringBoot 2.6.11
- Quartz 2.3.2
- Mybatis-Plus 3.5.3.1
- Httpclient 4.5.13

2、前端：
- Layui 2.8.11
- jQuery 3.4.1

## 运行环境
- Jdk8
- MySQL5.6+

## 运行启动教程
1. 新建MySQL数据库并导入sql文件夹下的数据库脚本
2. 修改配置文件中application.yml中数据库连接信息
3. 运行启动类TinyJobApplication，即可正常启动项目
4. 管理后台登录地址：http://localhost:9009  账户密码 admin / 123456a?

## 平台功能
1、项目管理
- 增加项目维度，方便对主机信息和定时任务进行归类管理

2、主机管理
- 维护主机地址，一个主机信息可维护多个主机地址用于负载均衡，如 `http://172.89.56.117:8899`, `http://172.89.56.118:8899`
- 支持多种负载均衡策略（`FIRST`，`LAST`，`ROUND`，`RANDOM`），并提供标准化接口，可根据需要自行扩展
  - FIRST：第一个
  - LAST：最后一个
  - ROUND：轮询
  - RANDOM：随机

3、任务管理
- 在线配置定时任务，包括新增任务、修改任务、删除任务、手动执行一次，以及实时启动/停止任务等功能，定时任务包括以下属性：
  - 所属项目：对应所属项目
  - 任务主机：对应主机信息
  - 任务名称：任务的名称
  - 任务组：任务的分组，只能由英文数字和下划线组成
  - 触发器类型：支持CRON和SIMPLE两种触发器(SIMPLE触发器即为按照固定秒数间隔执行的触发器)
  - Cron执行表达式：
  - 间隔时间(秒)：
  - 请求类型：`GET`,`POST`,`POST_JSON`
  - 请求路径：和主机地址拼接，作为最终的请求地址
  - 请求参数：请求的参数信息，以标准JSON的格式配置
  - 请求头：请求的头信息，以标准JSON的格式配置
  - 主机路由策略：第一个、最后一个、轮询、随机
  - 调度过期策略：立即执行、执行一次、放弃执行(默认)
  - 是否并发执行：

4、任务执行日志
- 查询展示定时任务的历史执行记录信息
- 任务执行日志做签名完整性保护存储(国密3算法签名)

5、预警邮箱配置
- 配置预警邮箱用于接收任务失败时的预警消息

## 功能界面展示
首页
![首页](src/main/resources/static/images/readme/首页.png)

项目管理
![项目管理](src/main/resources/static/images/readme/项目管理.png)

项目管理-编辑
![项目管理-编辑](src/main/resources/static/images/readme/项目管理-编辑.png)

主机管理
![主机管理](src/main/resources/static/images/readme/主机管理.png)

主机管理-编辑
![主机管理-编辑](src/main/resources/static/images/readme/主机管理-编辑.png)

任务管理
![任务管理](src/main/resources/static/images/readme/任务管理.png)

任务管理-编辑
![项目管理-编辑](src/main/resources/static/images/readme/任务管理-编辑.png)

任务日志
![任务日志](src/main/resources/static/images/readme/任务日志.png)

## 常用cron表达式示例
- 0/2 * * * * ?   表示每2秒 执行任务

- 0 0/2 * * * ?   表示每2分钟 执行任务

- 0 0 2 1 * ?   表示在每月的1日的凌晨2点调整任务

- 0 15 10 ? * MON-FRI   表示周一到周五每天上午10:15执行作业

- 0 15 10 ? 6L 2002-2006   表示2002-2006年的每个月的最后一个星期五上午10:15执行作

- 0 0 10,14,16 * * ?   每天上午10点，下午2点，4点

- 0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时

- 0 0 12 ? * WED    表示每个星期三中午12点

- 0 0 12 * * ?   每天中午12点触发

- 0 15 10 ? * *    每天上午10:15触发

- 0 15 10 * * ?     每天上午10:15触发

- 0 15 10 * * ?    每天上午10:15触发

- 0 15 10 * * ? 2005    2005年的每天上午10:15触发

- 0 * 14 * * ?     在每天下午2点到下午2:59期间的每1分钟触发

- 0 0/5 14 * * ?    在每天下午2点到下午2:55期间的每5分钟触发

- 0 0/5 14,18 * * ?     在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发

- 0 0-5 14 * * ?    在每天下午2点到下午2:05期间的每1分钟触发

- 0 10,44 14 ? 3 WED    每年三月的星期三的下午2:10和2:44触发

- 0 15 10 ? * MON-FRI    周一至周五的上午10:15触发

- 0 15 10 15 * ?    每月15日上午10:15触发

- 0 15 10 L * ?    每月最后一日的上午10:15触发

- 0 15 10 ? * 6L    每月的最后一个星期五上午10:15触发

- 0 15 10 ? * 6L 2002-2005   2002年至2005年的每月的最后一个星期五上午10:15触发

- 0 15 10 ? * 6#3   每月的第三个星期五上午10:15触发