
# CoolCar

**CoolCar**是一款基于安卓开发的智能车载APP，主要包括**健康信息**和**环境信息**两大板块，兼有地图定位、车友讨论和介绍指南等功能。通过集成在方向盘上的硬件设备，系统能够采集到驾驶员的体温、心率、血压等生理指标以及车内环境的温度、湿度、紫外线强度等指标。APP巧妙地将车内环境和车主健康状况监测融合到一起，使车主能及时掌握自身健康指标以及车内环境，避免因健康状况而引发的安全事故。此外，系统能够通过APP向驾驶者**推送**健康信息，并进行语音播报，可在紧急时刻**自动**拨打求救电话并向呼救目标发送自己的位置信息。
	
|Author|Perfwxc|
|---|---
|E-mail|bigwxc@gmail.com
|WebSite|www.perfwxc.cn


------

# 目录

* [项目背景](#项目背景)
    * 社会背景
    * 竞品分析
* [项目简介](#项目简介)
* [开发过程](#开发过程)
    * 开发语言
    * 开发工具
    * API调用
    * 数据库
* [功能与界面展示](#功能与界面展示)
* [视频展示](#视频展示)
------
## 项目背景

### 社会背景

如今随着互联网技术的深度发展，大数据、云计算、人工智能等前沿技术逐渐深入到人类生活的方方面面。

伴随着汽车行业的发展，汽车智能化、网联化已成为大势所趋。

与此同时，**驾车安全性**也成为驾驶员驾车出行时的重要因素。

------

### 竞品分析

|#|应用|功能介绍|核心特色
|---|---|----|----|
|1|奔驰智能互联手机应用<br>　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/奔驰.png" width=100 height=100>|1、车辆远程遥控，远程开锁。<br>2、用户手机地图上查看车辆停放位置。<br>3、地图查询，车载导航系统。<br>|**仅实现了汽车远程遥控**
|2|　　　乐蜂窝<br>　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/乐蜂窝.png" width=100 height=100>|1、测量用户的心率、血压等健康指标<br>2、检查车辆位置、车辆胎压胎温<br>|**缺少针对驾驶员的健康监测**
|3|　　车载精灵<br>　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/车载精灵.png" width=100 height=100>|1、驾驶员人体健康监测分析<br>2、车内环境监测分析<br>|**对驾驶员与车内环境进行全面监测**

------

### 项目简介


    本项目从车主身体健康以及车内环境信息出发，通过集成在车主驾驶座位、安全带等车内传感器或外置检测设备，定时为车主做出基础体检，通过测量得到的温度、血压、心率、酒精含量等生命体征信息，将数据存入到车载中控中的智能监控APP内，完成与云端大数据对比，进行车主健康状况分析、历史趋势分析等，实时通过文字与语音为车主提供健康监测报告和健康建议。测量车内环境也是如此，通过外置硬件定时测量车内的空气质量、烟雾浓度、光照强度、温湿度、一氧化碳浓度等信息，汇总分析后做出报告以及建议。
    设计主要分为了三部分，包括硬件传感器、软件APP以及云端服务器，其中硬件与APP是提供给用户的，云端服务器作为后台，只做数据存储与分析的工作。产品设计的初衷为在用户驾驶时，定时测量用户健康状况，通过信息推送告知用户的健康状态，防止意外或者危险的发生。同时，良好的车内环境也是健康的保障。长时间不开车窗，会导致车内的氧气浓度降低，车内的人会感到头晕、乏力等不适状况。有新闻报道，部分家长由于疏忽将孩子锁在车内，孩子最终因窒息而死，多么令人惋惜！本项目设计的环境检测功能可以将车内环境及时推送到手机，一定程度上避免了危险发生。



------

## 开发过程

### 开发语言

项目整体采用Java（Android）开发，数据库采用MySQL，后端与数据库的交互采用PHP。

### 开发工具

安卓项目开发采用Android Studio 3.0，最低支持安卓版本为5.0。服务器采用腾讯云服务器，服务器软件使用了PHPStudy（包含Apache、PHPMyAdmin、MySQL等集成环境）。<br>
　　　　　　　　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/安卓开发工具.png" height=200px>  　　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/phpstudy.png" height=200px>

### API调用
|#|API|使用功能|调用地址|
|---|----|----|-----|
|1|`百度地图`| 用于地图定位、导航   |[百度地图](http://lbsyun.baidu.com/index.php?title=androidsdk/guide/create-project/androidstudio "悬停显示")|
|2|`百度AI`| 用于语音播报、语音唤醒，为用户带来更好的人机交互   |[百度AI](http://ai.baidu.com/docs#/TTS-Android-SDK/top "悬停显示")|
|3|`MPChart`| 用于绘制图表、折线等，类似ECharts    |[MPChart](https://github.com/PhilJay/MPAndroidChart "悬停显示")|
|4|`个推`|  用于服务器后台检测到异常数据后自动推送  |[个推](http://docs.getui.com/getui/start/andorid/ "悬停显示")|
|5|`腾讯短信`|  用于短信验证注册、登陆 |[腾讯短信](https://cloud.tencent.com/ "悬停显示")|

### 数据库
数据库采用MySQL，由于没有接受过专业的数据库课程学习，数据库设计较为鸡肋，能满足基本需求。<br>
附数据库.sql文件下载：[CoolCar.sql](https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/data/coolcar.sql "悬停显示")


## 功能与界面展示

|#|功能介绍|界面展示|
|---|---|----
|1|**主界面**<br>`APP的主界面，设计简洁，包括了人体健康与环境信息两个板块，点击即可进入相应界面`|　　　　　　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/主界面.png" height=400>
|2|**人体健康**<br>`人体健康信息界面，展示了用户各时刻的身体指标，点击时间可进入详细健康信息页面，点击中部指标，系统会进行语音播报，点击右侧评级，系统会播报相应健康建议，点击右上方信箱按钮，可以查看自己的历史健康变化趋势`|　　　　　　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/健康数据信息.png" height=400>
|3|**各时刻详细信息**<br>`详细健康信息与建议界面，系统为用户的健康指标进行打分，并在底部为用户提供了相应建议`|　　　　　　　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/各时刻信息建议.JPG" width=200>
|4|**历史健康趋势**<br>`历史健康趋势页面，通过折线图展示了用户过去一段时间内的健康变化趋势，使用户进一步了解自己的健康状况`|　　　　　　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/折线图.png" height=400>
|5|**月度健康报告**<br>`月度健康报告设计思想类似于网易云音乐的年度听歌报告，展示了用户一个月期间的身体健康情况`|　　　　　　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/月度健康报告.png" height=400>
|6|**车内环境**<br>`车内环境信息界面，展示了车内各时刻的环境指标，点击时间可以进入详细环境信息界面，点击中部的指标，系统会进行语言播报，点击右侧评级，系统会播报相应的环境建议`|　　　　　　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/车内环境.png" height=400>
|7|**个人信息**<br>`个人信息界面展示了用户的基本情况，包括了用户的应急联系人的联络方式`|　　　　　　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/个人信息.png" height=400>
|8|**编辑信息**<br>`编辑信息界面用于编辑用户的个人信息`|　　　　　　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/编辑信息.png" height=400>
|9|**地图定位**<br>`地图定位为用户提供定位导航服务，方便用户驾车`|　　　　　　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/地图定位.png" height=400>
|10|**车友讨论**<br>`系统根据用户个人信息中的车型，为用户自动分配车友圈，在此可与车友讨论交流`|　　　　　　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/车友讨论.png" height=400>
|11|**介绍指南**<br>`若用户对APP的操作不熟悉，可根据介绍指南操作`|　　![](https://github.com/perfwxc/CoolCar/raw/master/release/pics/引导.png)
|12|**异常报警**<br>`系统检测到用户的身体健康数据获车内环境数据有异常时，健康信息页面的评级将显示异常图标，并通知用户`|　　　　　　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/异常报警.png" width=200>
|13|**消息推送**<br>`由服务器后台实时推送用户的健康信息`|　　　　　　　<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/消息推送.png" height=400>

## 视频展示

[点击观看本项目演示视频](https://www.bilibili.com/video/av31378239/ "悬停显示")

**如果这个项目对您有帮助的话请点击Star哦**:sparkling_heart::sparkling_heart:<br>
**对项目有任何疑问可发送至我的邮箱**
	bigwxc@gmail.com


