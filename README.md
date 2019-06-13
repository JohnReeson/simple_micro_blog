# simple_micro_blog
对微博部分基础功能的简单实现，后端基于SSM框架，前端基于bootstrap3，mysql数据库(micro_blog_structure.sql)。

### 测试账号
user: admin  password: !QAZ2wsx
服务器从闲置到首次加载需要半分钟左右，请耐心等待。

### 基本功能

1. 登录/注册（邮箱验证）
2. 发布，删除，评论，转发微博
3. 关注，取消关注用户
4. 查看关注和粉丝
5. 搜索微博和用户

### 配置
1. jdbc.properties mysql数据库配置
2. MailServiceImpl 邮件服务器配置和验证跳转链接
3. /microBlog 的路径前缀在本地debug时使用，发布到根路径需要删除。

### 兼容性
On computer, Chrome75/IE11/EdgeHTML17
