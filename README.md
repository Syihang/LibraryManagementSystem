# LibraryManagementSystem
这是我的Java实训作业 界面布局采用JavaFX设计 数据库采用Mysql数据库 （详细说明见设计文档）



### 数据库设计

##### 表4.2 学生表（student）

| 字段         | 类型        | 是否主键 | 是否为空 | 中文描述 | 备注   |
| ------------ | ----------- | -------- | -------- | -------- | ------ |
| student_id   | CHAR(12)    | 是       | 否       | 学号     |        |
| password     | VARCHAR(12) | 否       | 否       | 密码     |        |
| student_name | VARCHAR(12) | 否       | 否       | 姓名     |        |
| gender       | CHAR(2)     | 否       | 否       | 性别     |        |
| faculty      | VARCHAR(20) | 否       | 否       | 所在学院 |        |
| year         | DATE        | 否       | 否       | 入学年份 |        |
| telephone    | CHAR(11)    | 否       | 否       | 联系电话 | 不重复 |

 

##### 表4.3 管理员表（admin）：

| 字段       | 类型        | 是否主键 | 是否为空 | 中文描述 | 备注     |
| ---------- | ----------- | -------- | -------- | -------- | -------- |
| admin_id   | INT         | 是       | 否       | 编号     | 自增主键 |
| account    | CHAR(12)    | 否       | 否       | 账号     | 不重复   |
| password   | VARCHAR(12) | 否       | 否       | 密码     |          |
| admin_name | VARCHAR(12) | 否       | 否       | 姓名     |          |
| telephone  | CHAR(11)    | 否       | 否       | 联系电话 | 不重复   |

 

##### 表4.4 书籍表(book)：

| 字段             | 类型          | 是否主键 | 是否为可为空 | 中文描述 | 备注 |
| ---------------- | ------------- | -------- | ------------ | -------- | ---- |
| ISBN             | CHAR(10)      | 是       | 否           | ISBN编号 |      |
| title            | VARCHAR(20)   | 否       | 否           | 书名     |      |
| author           | VARCHAR(12)   | 否       | 否           | 作者     |      |
| publisher        | VARCHAR(20)   | 否       | 否           | 出版社   |      |
| publication_date | DATE          | 否       | 否           | 出版日期 |      |
| total            | INT           | 否       | 否           | 总量     |      |
| price            | DECIMAL(6, 2) | 否       | 否           | 价格     |      |
| type_id          | INT           | 否       | 否           | 类型     | 外键 |

 

##### 表4.5 类别表(type):

| 字段    | 类型        | 是否主键 | 是否为空 | 中文描述 | 备注 |
| ------- | ----------- | -------- | -------- | -------- | ---- |
| type_id | INT         | 是       | 否       | 类别ID   | 自增 |
| type    | VARCHAR(10) | 否       | 否       | 类别名   |      |

 

##### 表4.6 丢失/损坏情况记录表（lose）：

| 字段            | 类型       | 是否主键 | 是否为空 | 中文描述 | 备注             |
| --------------- | ---------- | -------- | -------- | -------- | ---------------- |
| lose_id         | INT        | 是       | 否       | 记录编号 | 自增             |
| student_id      | CHAR(12)   | 否       | 否       | 学号     | 外键             |
| ISBN            | CHAR(10)   | 否       | 否       | ISBN编号 | 外键             |
| status          | VARCHAR(5) | 否       | 否       | 状态     | 丢失/损坏        |
| report_time     | DATE       | 否       | 否       | 上报时间 | 默认填充当前时间 |
| is_compensation | BOOLEN     | 否       | 否       | 是否赔偿 |                  |
| remark          | TEXT       | 否       | 是       | 备注     |                  |

定义触发器事件，在lose表中插入数据时，若时间上报时间为空，则默认填充当前时间。

 

##### 表4.7 借阅记录表（borrow）：

| 字段               | 类型     | 是否主键 | 是否为空 | 中文描述     | 备注 |
| ------------------ | -------- | -------- | -------- | ------------ | ---- |
| borrow_id          | INT      | 是       | 否       | 记录编号     | 自增 |
| student_id         | CHAR(12) | 否       | 否       | 学号         | 外键 |
| ISBN               | CHAR(10) | 否       | 否       | ISBN编号     | 外键 |
| borrow_time        | DATE     | 否       | 否       | 借阅时间     |      |
| return_time        | DATE     | 否       | 否       | 应归还时间   |      |
| actual_return_time | DATE     | 否       | 是       | 实际归还时间 |      |

定义触发器事件，删除学生或书籍信息时，检查是否存在未归还书籍，当借阅记录表中存在未归还书籍时，该书籍信息及借阅该书籍的学生信息不得删除。

 

##### 表4.8 学生信誉表（credit）：

| 字段              | 类型     | 是否主键 | 是否为空 | 中文描述     | 备注       |
| ----------------- | -------- | -------- | -------- | ------------ | ---------- |
| student_id        | CHAR(12) | 是       | 否       | 学号         |            |
| borrow_times      | INT      | 否       | 否       | 总借阅次数   | 默认为0    |
| late_return_times | INT      | 否       | 否       | 延迟归还次数 | 默认为0    |
| credit_value      | TINYINT  | 否       | 否       | 信誉积分     | 默认为100  |
| credit_rating     | CHAR(5)  | 否       | 否       | 信誉等级     | 默认为优秀 |
| borrow_limit      | TINYINT  | 否       | 否       | 借阅上限     | 默认为4    |

 

##### 学生信誉表字段更新规则：

该表与学生表、以及借阅记录表同步更新。每一条学生表中的学生数据对应该表中的一条数据。

其中:

borrow_times = count(student_id) from borrow where borrow. student_id = credit. student_id（借阅记录表中该学生借书总次数）

late_return_times = count(return_time > actual_return_time) from borrow where borrow. student_id = credit. student_id （借阅记录表中该学生延迟归还次数）

credit_value = 100 - late_return_times * 5 + borrow_times （取值范围为0-100，当数值大于100时则取值为100）

credit_rating = 优秀（credit_value>=90 ）、良好（90 > credit_value >= 80）、一般（80 > credit_value >= 70）、及格（70 > credit_value >= 60）、不及格（60 > credit_value）

borrow_limit （当信誉等级为优秀时，借阅上限为4、当信誉等级为良好时，借阅上限为3、当信誉等级为一般时，借阅上限为2、当信誉等级为及格时，借阅上限为1、当信誉等级不及格时或存在逾期且未归还书籍时（count(return_time <当前时间 and actual_return_time = null) ），借阅上限为0）



### 项目结构       

![image-20240801094530266](D:\GitRepo\LibraryManagementSystem\image\image-20240801094530266.png)

项目大文件夹下包括lib与src两个文件，其中，lib存储项目所用到的依赖，src存储项目的实现方法以及资源文件。

其中src.mian.java中包含具体的代码，main,src.resources中包含项目所有的资源文件。

 

![image-20240801094600806](D:\GitRepo\LibraryManagementSystem\image\image-20240801094600806.png)

在Java文件夹下，各个文件夹的具体功能分别如下：

- AI：连接AI，实现对蓝心大模型接口的调用。
- controller：对前端代码进行控住，并将初始化好的数据添加到前端
- database： 操纵数据库，实现增删改查
- entity： 存储项目所需要的所有实体类
- file：通过easy Excel的调用，实现对Excel文件的读写操作
- util： 存储该项目所需要的工具类，如字符串处理，时间处理等操作  



![image-20240801094627055](D:\GitRepo\LibraryManagementSystem\image\image-20240801094627055.png)

在resource文件夹下，各个模块功能如下所示：

- admin：用来存储管理端fxml布局文件
- css：存储JavaFX所需的css美化效果
- images：存储项目所需的所有图片
- student：存储用户端的fxml布局文件



### 功能界面



#### 用户端

<img src="D:\GitRepo\LibraryManagementSystem\image\login.png" alt="image-20240801094252258" style="zoom:80%;" />



<img src="D:\GitRepo\LibraryManagementSystem\image\image-20240801095459110.png" alt="image-20240801095459110"  />

![image-20240801095633237](D:\GitRepo\LibraryManagementSystem\image\image-20240801095633237.png)

![image-20240801095944742](D:\GitRepo\LibraryManagementSystem\image\image-20240801095944742.png)

![image-20240801095956638](D:\GitRepo\LibraryManagementSystem\image\image-20240801095956638.png)

![image-20240801100011627](D:\GitRepo\LibraryManagementSystem\image\image-20240801100011627.png)

![image-20240801100028514](D:\GitRepo\LibraryManagementSystem\image\image-20240801100028514.png)

![image-20240801100045608](D:\GitRepo\LibraryManagementSystem\image\image-20240801100045608.png)



#### 管理端

![image-20240801100105229](D:\GitRepo\LibraryManagementSystem\image\image-20240801100105229.png)

![image-20240801100125490](D:\GitRepo\LibraryManagementSystem\image\image-20240801100125490.png)

![image-20240801100140431](D:\GitRepo\LibraryManagementSystem\image\image-20240801100140431.png)
