# PCMS
Programming Competition Management System

activity结构是：
MainActivity(登录界面)->RegisterActivity(注册界面)+MD5Utils(MD5加密算法对密码加密)  

登录分两种：  

(1)ManagerActivity(管理员界面) -->（账户：admin，密码：123456）  
一个适配器：ManagerAdapter  
三个fragment: EditFragment(编辑) ManageFragment(管理) ReplyFragment(回复)  
对应的layout：fragment_edit      fragment_manage      fragment_reply   
  
(2)UserActivity(用户界面)  
一个适配器：UserAdapter  
三个fragment: HomeFragment(主页) MessageFragment(信息) MineFragment(我的)  
对应的layout：fragment_home     fragment_message      fragment_mine  
