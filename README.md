# PCMS
Programming Competition Management System

## activity结构<br>
    MainActivity(登录界面)->RegisterActivity(注册界面)+MD5Utils(MD5加密算法对密码加密)

### 登录分两种：<br>

#### (1)ManagerActivity(管理员界面) -->（账户：admin，密码：zut_acm54321）<br>
    一个适配器：ManagerAdapter
    三个fragment: EditFragment(编辑) ManageFragment(管理) ReplyFragment(回复)
    对应的layout：fragment_edit      fragment_manage      fragment_reply

#### (2)UserActivity(用户界面)   --> (账户：user，密码：zut_acm54321)<br>
    一个适配器：UserAdapter
    三个fragment: HomeFragment(主页) MessageFragment(信息) MineFragment(我的)
    对应的layout：fragment_home     fragment_message      fragment_mine

### 注册
    已解决QwQ。
    更改学院为下拉选择模式
    学号限制:方便获奖信息表学号与此学号一致,方便管理员发通知
    新增院系表
    密码位数限制
### 数据库contest
    user：用户账号管理表, 主键：学号, 外键：学院，身份
    status：用户身份表
    dep：院系表
### 数据库contact
    contact:存储用户和管理员的聊天记录(存到contest里暂时实现不了，所以新开一个数据库)
### Fragment嵌套(比葫芦画瓢可以直接套这个样式，如果你的模块也需要嵌套的话)<br>
#### 1.EditFragment(编辑) <br>
    有两个fragment: NewsFragment(新闻)  NoticeFragment(通知)
    一个FragmentController管理：EditFragmentController
    一个Fragment负责执行：就是EditFragment本身，其对于的布局fragment_edit也做了相应修改

#### 2.ManageFragment(奖项管理)<br>
    有四个fragment:
    AwardsAddFragment(添加)  AwardsDeleteFragment(删除)
    AwardsUpdateFragment(修改) AwardsQueryFragment(查询)
    一个FragmentController管理：ManageFragmentController
    一个Fragment负责执行：就是ManageFragment本身，其对于的布局fragment_manage也做了相应修改

### 用户信息管理
    用户界面 -> 我的：点击昵称可以修改昵称
    管理员界面 -> 管理 -> 用户管理：查询到所有用户，点击item可以删除用户
    
 
