# EAlbum
一个简易的云相册，主要用来应付学校实训（真实）+熟悉ssm+前端康复训练（x），所以可以看到我用了很多框架来完成...<br>
前端设计主要是模仿小米云相册的。
[示例链接](https://ealbum.episooo.cn "EAlbum")
## 技术栈
#### 前端框架
 - Bootstrap
 - Vue
 - Layui
#### 后台框架
 - Spring
 - Spring MVC
 - Mybatis

## 项目进度
#### 已实现：
 - 用户
   - 登录注册
 - 照片
   - 查看
   - 上传
   - 下载
   - 删除
 - 相册
   - 添加相册
   - 删除相册
   - 查看相册
 - 回收站
   - 恢复照片
   - 彻底删除照片
#### 待完善：
 - 多选图片进行下载/删除
#### 待优化：
 - 懒加载和分页
 
## 使用须知
 - 本程序在用户注册时需要通过邮件发送激活码，因此请确保您的邮箱开启了SMTP服务。
 - 克隆后您需要在resource目录下添加mail.properties文件,内容如下。
```properties
mail.host=yourEmailHost #目前仅支持QQ邮箱或绑定在QQ邮箱的域名邮箱
mail.password=yourEmailPassword  #在邮箱服务上开启SMTP时授予的密码
mail.organization=yourOrganizationName  #这决定了发邮件时使用的公司名 可任意填写
```
 - 数据库文件位于webapp下。
