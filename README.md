# PCMS
Programming Competition Management System

## activity结构<br>
    MainActivity(登录界面)->RegisterActivity(注册界面)+MD5Utils(MD5加密算法对密码加密)<br>

### 登录分两种：<br>

#### (1)ManagerActivity(管理员界面) -->（账户：admin，密码：zut_acm54321）<br>
    一个适配器：ManagerAdapter<br>
    三个fragment: EditFragment(编辑) ManageFragment(管理) ReplyFragment(回复)<br>
    对应的layout：fragment_edit      fragment_manage      fragment_reply<br>

#### (2)UserActivity(用户界面)   --> (账户：user，密码：zut_acm54321)<br>
    一个适配器：UserAdapter  <br>
    三个fragment: HomeFragment(主页) MessageFragment(信息) MineFragment(我的)  <br>
    对应的layout：fragment_home     fragment_message      fragment_mine  <br>

### 注册问题
    注意:现在注册过后进不去主界面了,有空写注册
### 数据库contest
    user：用户账号管理表
    status：用户身份表
### 数据库contact
    contact:存储用户和管理员的聊天记录(存到contest里暂时实现不了，所以新开一个数据库)
### Fragment嵌套(比葫芦画瓢可以直接套这个样式，如果你的模块也需要嵌套的话)<br>
#### 1.EditFragment(编辑) <br>
    有两个fragment: NewsFragment(新闻)  NoticeFragment(通知)<br>
    一个FragmentController管理：EditFragmentController<br>
    一个Fragment负责执行：就是EditFragment本身，其对于的布局fragment_edit也做了相应修改<br>

#### 2.ManageFragment(奖项管理)<br>
    有四个fragment: <br>
    AwardsAddFragment(添加)  AwardsDeleteFragment(删除)<br>
    AwardsUpdateFragment(修改) AwardsQueryFragment(查询)<br>
    一个FragmentController管理：ManageFragmentController<br>
    一个Fragment负责执行：就是ManageFragment本身，其对于的布局fragment_manage也做了相应修改<br>

    
 
