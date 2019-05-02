
# CoolCar

**CoolCar**是一款基于安卓开发的智能车载APP，主要包括**健康信息**和**环境信息**两大板块，兼有地图定位、车友讨论和介绍指南等小功能。通过集成在方向盘上的硬件设备，系统能够采集到驾驶员的体温、心率、血压等生理指标以及车内环境的温度、湿度、紫外线强度等指标。APP巧妙地将车内环境和车主健康状况监测融合到一起，使车主能及时掌握自身健康指标以及车内环境，避免因健康状况而引发的安全事故。此外，系统能够通过APP向驾驶者**推送**健康信息，并进行语音播报，可在紧急时刻**自动**拨打求救电话并向呼救目标发送自己的位置信息。
	
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


本系统主要包括**健康信息**和**环境信息**两大板块，兼有地图定位、车友讨论和介绍指南等小功能。通过集成在方向盘上的硬件设备，系统能够采集到驾驶员的体温、心率、血压等生理指标以及车内环境的温度、湿度、紫外线强度等指标。APP巧妙地将车内环境和车主健康状况监测融合到一起，使车主能及时掌握自身健康指标以及车内环境，避免因健康状况而引发的安全事故。此外，系统能够通过APP向驾驶者**推送**健康信息，并进行语音播报，可在紧急时刻**自动**拨打求救电话并向呼救目标发送自己的位置信息。


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
|1|`主界面`<br>APP的主界面，设计简洁，包括了人体健康与环境信息两个板块，点击即可进入相应界面|<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/主界面.png" height=400>
|2|`人体健康`<br>人体健康信息界面，展示了用户各时刻的身体指标，点击时间可进入详细健康信息页面，点击中部指标，系统会进行语音播报，点击右侧评级，系统会播报相应健康建议，点击右上方信箱按钮，可以查看自己的历史健康变化趋势|<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/健康数据信息.png" height=400>
|3|`各时刻详细信息`<br>详细健康信息与建议界面，系统为用户的健康指标进行打分，并在底部为用户提供了相应建议|<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/各时刻信息建议.jpg" height=400>
|4|`历史健康趋势`<br>历史健康趋势页面，通过折线图展示了用户过去一段时间内的健康变化趋势，使用户进一步了解自己的健康状况|<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/折线图.png" height=400>
|5|`月度健康报告`<br>月度健康报告设计思想类似于网易云音乐的年度听歌报告，展示了用户一个月期间的身体健康情况|<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/月度健康报告.png" height=400>
|6|`车内环境`<br>车内环境信息界面，展示了车内各时刻的环境指标，点击时间可以进入详细环境信息界面，点击中部的指标，系统会进行语言播报，点击右侧评级，系统会播报相应的环境建议|<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/车内环境.png" height=400>
|7|`个人信息`<br>个人信息界面展示了用户的基本情况，包括了用户的**应急联系人**的联络方式|<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/个人信息.png" height=400>
|8|`编辑信息`<br>编辑信息界面用于编辑用户的个人信息|<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/编辑信息.png" height=400>
|9|`地图定位`<br>地图定位为用户提供定位导航服务，方便用户驾车|<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/地图定位.png" height=400>
|10|`车友讨论`<br>系统根据用户个人信息中的车型，为用户自动分配车友圈，在此可与车友讨论交流|<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/车友讨论.png" height=400>
|11|`介绍指南`<br>若用户对APP的操作不熟悉，可根据介绍指南操作|![](https://github.com/perfwxc/CoolCar/raw/master/release/pics/引导.png)
|12|`异常报警`<br>系统检测到用户的身体健康数据获车内环境数据有异常时，健康信息页面的评级将显示异常图标，并通知用户|<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/异常报警.png" height=400>
|13|`消息推送`<br>由服务器后台实时推送用户的健康信息|<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/消息推送.png" height=400>
|14|`硬件设备`<br>硬件设备基于STM32 F407开发，图示为将硬件集成在方向盘后的效果|<img src="https://raw.githubusercontent.com/perfwxc/CoolCar/master/release/pics/硬件.jpg" height=400>

文本
------
### 普通文本
这是一段普通的文本
### 单行文本
    Hello,大家好，我是果冻虾仁。
在一行开头加入1个Tab或者4个空格。
### 文本块
#### 语法1
在连续几行的文本开头加入1个Tab或者4个空格。

    欢迎到访
    很高兴见到您
    祝您，早上好，中午好，下午好，晚安

#### 语法2
使用一对各三个的反引号：
```
欢迎到访
我是C++码农
你可以在知乎、CSDN、简书搜索【果冻虾仁】找到我
```
该语法也可以实现代码高亮，见[代码高亮](#代码高亮)
### 文字高亮
文字高亮功能能使行内部分文字高亮，使用一对反引号。
语法：
```
`linux` `网络编程` `socket` `epoll` 
```
效果：`linux` `网络编程` `socket` `epoll`

也适合做一篇文章的tag
#### 换行
直接回车不能换行，  
可以在上一行文本后面补两个空格，  
这样下一行的文本就换行了。

或者就是在两行文本直接加一个空行。

也能实现换行效果，不过这个行间距有点大。
#### 斜体、粗体、删除线

|语法|效果|
|----|-----|
|`*斜体1*`|*斜体1*|
|`_斜体2_`| _斜体2_|
|`**粗体1**`|**粗体1**|
|`__粗体2__`|__粗体2__|
|`这是一个 ~~删除线~~`|这是一个 ~~删除线~~|
|`***斜粗体1***`|***斜粗体1***|
|`___斜粗体2___`|___斜粗体2___|
|`***~~斜粗体删除线1~~***`|***~~斜粗体删除线1~~***|
|`~~***斜粗体删除线2***~~`|~~***斜粗体删除线2***~~|

    斜体、粗体、删除线可混合使用

图片
------
基本格式：
```
![alt](URL title)
```
alt和title即对应HTML中的alt和title属性（都可省略）：
- alt表示图片显示失败时的替换文本
- title表示鼠标悬停在图片时的显示文本（注意这里要加引号）

URL即图片的url地址，如果引用本仓库中的图片，直接使用**相对路径**就可了，如果引用其他github仓库中的图片要注意格式，即：`仓库地址/raw/分支名/图片路径`，如：
```
https://github.com/guodongxiaren/ImageCache/raw/master/Logo/foryou.gif
```

|#|语法|效果|
|---|---|----
|1|`baidu`|![baidu](http://www.baidu.com/img/bdlogo.gif "百度logo")
|2|`jieshao`|![](https://github.com/perfwxc/CoolCar/raw/master/release/pics/编辑信息.png)

注意例2的写法使用了**URL标识符**的形式，在[链接](#链接)一节有介绍。
>在文末有foryou的定义：


链接
------
### 链接外部URL

|#|语法|效果|
|---|----|-----|
|1|`[我的博客](http://blog.csdn.net/guodongxiaren "悬停显示")`|[我的博客](http://blog.csdn.net/guodongxiaren "悬停显示")|
|2|`[我的知乎][zhihu] `|[我的知乎][zhihu] |

语法2由两部分组成：
- 第一部分使用两个中括号，[ ]里的标识符（本例中zhihu），可以是数字，字母等的组合，标识符上下对应就行了（**姑且称之为URL标识符**）
- 第二部分标记实际URL。

>使用URL标识符能达到复用的目的，一般把全文所有的URL标识符统一放在文章末尾，这样看起来比较干净。
>>URL标识符是我起的名字，不知道是否准确。囧。。

### 链接本仓库里的URL

|语法|效果|
|----|-----|
|`[我的简介](/example/profile.md)`|[我的简介](/example/profile.md)|
|`[example](./example)`|[example](./example)|

### 图片链接
给图片加链接的本质是混合图片显示语法和普通的链接语法。普通的链接中[ ]内部是链接要显示的文本，而图片链接[ ]里面则是要显示的图片。  
直接混合两种语法当然可以，但是十分啰嗦，为此我们可以使用URL标识符的形式。

|#|语法|效果|
|---|----|:---:|
|1|`[![weibo-logo]](http://weibo.com/linpiaochen)`|[![weibo-logo]](http://weibo.com/linpiaochen)|
|2|`[![](/img/zhihu.png "我的知乎，欢迎关注")][zhihu]`|[![](/img/zhihu.png "我的知乎，欢迎关注")][zhihu]|
|3|`[![csdn-logo]][csdn]`|[![csdn-logo]][csdn]|

因为图片本身和链接本身都支持URL标识符的形式，所以图片链接也可以很简洁（见例3）。  
注意，此时鼠标悬停时显示的文字是图片的title，而非链接本身的title了。
> 本文URL标识符都放置于文末

### 锚点
其实呢，每一个标题都是一个锚点，和HTML的锚点（`#`）类似，比如我们 

|语法|效果|
|---|---|
|`[回到顶部](#readme)`|[回到顶部](#readme)|

不过要注意，标题中的英文字母都被转化为**小写字母**了。
> 以前GitHub对中文支持的不好，所以中文标题不能正确识别为锚点，但是现在已经没问题啦！

