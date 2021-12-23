### 场景群和机器人--demo

- 此demo主要展示场景群和机器人相关功能，包括创建场景群，发送动态卡片，更新动态卡片，创建置顶卡片，更新置顶卡片，关闭置顶卡片等。
- 此demo需要开发者在钉钉开发后台创建群模版、机器人和消息卡片模版。
- 项目结构
  - rear-end：后端模块，springboot构建，接口功能包括：创建场景群，发送im卡片，更新im卡片，创建置顶卡片，开启置顶卡片，更新置顶卡片，关闭置顶卡片等。
  - front-end：前端模块，react构建，场景功能包括：自动登录，触发创建动作，设置群名称（默认），设置消息内容（默认），更新消息内容（默认），开启置顶卡片等。

### 研发环境准备

1. 需要有一个钉钉注册企业，如果没有可以创建：https://oa.dingtalk.com/register_new.htm#/

2. 成为钉钉开发者，参考文档：https://developers.dingtalk.com/document/app/become-a-dingtalk-developer

3. 登录钉钉开放平台后台创建一个H5应用： https://open-dev.dingtalk.com/#/index

4. 配置应用

   配置开发管理，参考文档：https://developers.dingtalk.com/document/app/configure-orgapp

   - **此处配置“应用首页地址”需公网地址，若无公网ip，可使用钉钉内网穿透工具：**

     https://developers.dingtalk.com/document/resourcedownload/http-intranet-penetration

![image-20210706171740868](https://img.alicdn.com/imgextra/i1/O1CN015ZmCJ31jN4sCKXpNa_!!6000000004535-2-tps-1133-551.png)



配置相关权限：https://developers.dingtalk.com/document/app/address-book-permissions

本demo使用接口相关权限：

​	”成员信息读权限“、“chat相关接口的管理权限”

![image-20210706172027870](https://img.alicdn.com/imgextra/i2/O1CN014fd6zp1O62CmoD14b_!!6000000001655-2-tps-1378-599.png)

5. 创建场景群模版：https://developers.dingtalk.com/document/chatgroup/fill-in-a-group-template

   ![](https://img.alicdn.com/imgextra/i3/O1CN01SjFrwQ1jIxITqcA6S_!!6000000004526-2-tps-1212-249.png)

6. 创建机器人模版：https://developers.dingtalk.com/document/chatgroup/create-group-assistant

   ![](https://img.alicdn.com/imgextra/i4/O1CN01DbuTUs1LWeMSsT0vo_!!6000000001307-2-tps-1228-246.png)

7. 创建消息模版：https://developers.dingtalk.com/document/chatgroup/create-message-template
  **请注意，在此demo中，我们需要在消息模版中设置三个数据源变量：（不设置无法发送消息）**
  **"content"——消息内容**
  **"msg"——消息内容补充**
  **"date"——消息时间**
   ![](https://img.alicdn.com/imgextra/i1/O1CN01soEyzL23BR6weVtx5_!!6000000007217-2-tps-1175-266.png)

### 运行前准备

 下载demo

```shell
git clone https://github.com/open-dingtalk/h5app-scene-group-demo.git
```

### 获取相应参数

获取到以下参数，修改后端application.yaml

```yaml
app:
  app_key: *****
  app_secret: *****
  agent_id: *****
  corp_id: *****
groupTemplateId: *****
botTemplateId: *****
cardTemplateId: *****
```

参数获取方法：登录开发者后台

1. 获取corpId：https://open-dev.dingtalk.com/#/index
2. 进入应用开发-企业内部开发-点击进入应用-基础信息-获取appKey、appSecret、agentId
3. 获取groupTemplateId（群模版id）：https://open-dev.dingtalk.com/fe/im#/group/list
4. 获取botTemplateId（机器人id）：https://open-dev.dingtalk.com/fe/im#/robot/list 
5. 获取cardTemplateId（消息卡片id）：https://h5.dingtalk.com/interactive-card-builder/index.html#/

### 修改前端页面

**打开项目，命令行中执行以下命令，编译打包生成build文件**

```shell
cd front-end
npm install
npm run build
```

**将打包好的静态资源文件放入后端**

![image-20210706173224172](https://img.alicdn.com/imgextra/i2/O1CN01QLp1Qw1TCVrPddfjZ_!!6000000002346-2-tps-322-521.png)

### 启动项目

- 启动springboot
- 移动端钉钉点击工作台，找到创建的应用，进入应用

### Cloud IDE启动

（云开发启动方法，普通情况忽视）

1、CloudIDE-Native启动：在cloudide的右下角Termial中的命令行中，输入启动命名：mvn spring-boot:run ,验证是否能在IDE容器中启动成功，启动成功后在IDE左下角有一个“预览”功能，可以把云端IDE启动的服务在本地浏览器中访问到，则说明Cloud-Native配置是OK的。 2、提交代码到CodeUp上； 3、开始部署到线上环境。在IDE左边工具栏中打开云开发插件，然后点击部署按钮，开始部署到云端。如果部署成功，则会在输出日志中，打印一个临时域名，可以直接访问。 到这一步，存量web应用就算迁移完了。

### 运行展示

![6](https://img.alicdn.com/imgextra/i3/O1CN01R0Xgm81UZF9wCQaJy_!!6000000002531-2-tps-497-572.png)

建群操作

![image-20210909164100930](https://img.alicdn.com/imgextra/i4/O1CN01GwFceT27LkUcaYELO_!!6000000007781-2-tps-341-639.png)

相关场景群自动创建

![3](https://img.alicdn.com/imgextra/i1/O1CN01t8pdph1pyzLx0CsMR_!!6000000005430-2-tps-496-400.png)

发送卡片消息

![image-20210909164930643](https://img.alicdn.com/imgextra/i4/O1CN01w0bMqC1SnK5hm28Wr_!!6000000002291-2-tps-335-465.png)

群内机器人发送消息

![1 ](https://img.alicdn.com/imgextra/i2/O1CN01tDJJLV22Vl53jgTyp_!!6000000007126-2-tps-497-586.png)

点击更新消息

![image-20210909164543933](https://img.alicdn.com/imgextra/i4/O1CN01L5dz6Z1elHiUfHf6D_!!6000000003911-2-tps-331-478.png)

机器人更新消息

![image-20210909165213720](https://img.alicdn.com/imgextra/i1/O1CN01sM0TDV1VE0OYzQiaC_!!6000000002620-2-tps-330-238.png)

发送一张群置顶的消息卡片



### 参考文档

1. 创建场景群，文档链接：https://developers.dingtalk.com/document/chatgroup/create-a-scene-group-v2
2. 发送可交互动态卡片，文档链接：https://developers.dingtalk.com/document/chatgroup/send-interactive-dynamic-cards
3. 更新卡片消息，文档链接：https://developers.dingtalk.com/document/chatgroup/update-dingtalk-interactive-cards
4. 创建置顶卡片，文档链接：https://developers.dingtalk.com/document/chatgroup/create-an-interactive-card-instance
5. 开启置顶卡片，文档链接：https://developers.dingtalk.com/document/chatgroup/group-session-open-interactive-card-instance-ceiling
6. 关闭置顶卡片，文档链接：https://developers.dingtalk.com/document/chatgroup/group-session-close-interactive-card-instance-ceiling
