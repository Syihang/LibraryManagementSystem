# 图书管理系统 (LibraryManagementSystem)

这是一个Java图书管理系统的项目，界面布局采用JavaFX设计，数据库采用MySQL数据库。详情请参阅设计文档。

## 目录

- [项目概述](#项目概述)
- [数据库设计](#数据库设计)
  - [学生表 (student)](#学生表-student)
  - [管理员表 (admin)](#管理员表-admin)
  - [书籍表 (book)](#书籍表-book)
  - [类别表 (type)](#类别表-type)
  - [丢失/损坏情况记录表 (lose)](#丢失损坏情况记录表-lose)
  - [借阅记录表 (borrow)](#借阅记录表-borrow)
  - [学生信誉表 (credit)](#学生信誉表-credit)
- [项目结构](#项目结构)
- [功能界面](#功能界面)

## 项目概述

本项目旨在实现一个简单的图书管理系统，支持图书的借阅、归还、查询等功能。同时，系统还包括管理员登录、学生信息管理等模块。

## 数据库设计

### 学生表 (student)

| 字段         | 类型        | 是否主键 | 是否为空 | 中文描述 | 备注       |
| ------------ | ----------- | -------- | -------- | -------- | ---------- |
| student_id   | CHAR(12)    | 是       | 否       | 学号     |            |
| password     | VARCHAR(12) | 否       | 否       | 密码     |            |
| student_name | VARCHAR(12) | 否       | 否       | 姓名     |            |
| gender       | CHAR(2)     | 否       | 否       | 性别     |            |
| faculty      | VARCHAR(20) | 否       | 否       | 所在学院 |            |
| year         | DATE        | 否       | 否       | 入学年份 |            |
| telephone    | CHAR(11)    | 否       | 否       | 联系电话 | 不重复     |

### 管理员表 (admin)

| 字段       | 类型        | 是否主键 | 是否为空 | 中文描述 | 备注       |
| ---------- | ----------- | -------- | -------- | -------- | ---------- |
| admin_id   | INT         | 是       | 否       | 编号     | 自增主键   |
| account    | CHAR(12)    | 否       | 否       | 账号     | 不重复     |
| password   | VARCHAR(12) | 否       | 否       | 密码     |            |
| admin_name | VARCHAR(12) | 否       | 否       | 姓名     |            |
| telephone  | CHAR(11)    | 否       | 否       | 联系电话 | 不重复     |

### 书籍表 (book)

| 字段       | 类型        | 是否主键 | 是否为空 | 中文描述 | 备注   |
| ---------- | ----------- | -------- | -------- | -------- | ------ |
| ISBN       | CHAR(10)    | 是       | 否       | ISBN编号 |        |
| title      | VARCHAR(20) | 否       | 否       | 书名     |        |
| author     | VARCHAR(12) | 否       | 否       | 作者     |        |
| publisher  | VARCHAR(20) | 否       | 否       | 出版社   |        |
| publication_date | DATE | 否       | 否       | 出版日期 |        |
| total      | INT         | 否       | 否       | 总量     |        |
| price      | DECIMAL(6, 2) | 否  | 否   | 价格     |        |
| type_id    | INT         | 否       | 否       | 类型     | 外键   |

### 类别表 (type)

| 字段     | 类型    | 是否主键 | 是否为空 | 中文描述 | 备注   |
| -------- | ------- | -------- | -------- | -------- | ------ |
| type_id  | INT     | 是       | 否       | 类别ID   | 自增   |
| type     | VARCHAR(10) | 否  | 否   | 类别名   |        |

### 丢失/损坏情况记录表 (lose)

| 字段           | 类型    | 是否主键 | 是否为空 | 中文描述 | 备注                   |
| -------------- | ------- | -------- | -------- | -------- | ---------------------- |
| lose_id        | INT     | 是       | 否       | 记录编号 | 自增                   |
| student_id     | CHAR(12)| 否       | 否       | 学号     | 外键                   |
| ISBN           | CHAR(10)| 否       | 否       | ISBN编号 | 外键                   |
| status         | VARCHAR(5) | 否  | 否   | 状态     | 丢失/损坏              |
| report_time    | DATE    | 否       | 否       | 上报时间 | 默认填充当前时间       |
| is_compensation| BOOLEAN | 否       | 否       | 是否赔偿 |                        |
| remark         | TEXT    | 否       | 是       | 备注     |                        |

**触发器**: 在`lose`表中插入数据时，若时间上报时间为空，则默认填充当前时间。

### 借阅记录表 (borrow)

| 字段               | 类型    | 是否主键 | 是否为空 | 中文描述 | 备注       |
| ------------------ | ------- | -------- | -------- | -------- | ---------- |
| borrow_id          | INT     | 是       | 否       | 记录编号 | 自增       |
| student_id         | CHAR(12)| 否       | 否       | 学号     | 外键       |
| ISBN               | CHAR(10)| 否       | 否       | ISBN编号 | 外键       |
| borrow_time        | DATE    | 否       | 否       | 借阅时间 |            |
| return_time        | DATE    | 否       | 否       | 应归还时间 |            |
| actual_return_time | DATE    | 否       | 是       | 实际归还时间 |            |

**触发器**: 删除学生或书籍信息时，检查是否存在未归还书籍。如果借阅记录表中存在未归还书籍，该书籍信息及借阅该书籍的学生信息不得删除。

### 学生信誉表 (credit)

| 字段         | 类型    | 是否主键 | 是否为空 | 中文描述 | 备注       |
| ------------ | ------- | -------- | -------- | -------- | ---------- |
| student_id   | CHAR(12)| 是       | 否       | 学号     |            |
| borrow_times | INT     | 否       | 否       | 总借阅次数 | 默认为0   |
| late_return_times | INT | 否  | 否   | 延迟归还次数 | 默认为0 |
| credit_value | TINYINT | 否       | 否       | 信誉积分 | 默认为100 |
| credit_rating| CHAR(5) | 否       | 否       | 信誉等级 | 默认为优秀 |
| borrow_limit | TINYINT | 否       | 否       | 借阅上限 | 默认为4   |

**更新规则**:
- `borrow_times`: 从`borrow`表中统计该学生借书总次数。
- `late_return_times`: 从`borrow`表中统计该学生延迟归还次数。
- `credit_value`: 计算公式为 `100 - late_return_times * 5 + borrow_times`，取值范围为0-100，当数值大于100时则取值为100。
- `credit_rating`: 根据`credit_value`确定，分别为优秀（≥90）、良好（80-89）、一般（70-79）、及格（60-69）、不及格（<60）。
- `borrow_limit`: 根据`credit_rating`设置，分别为优秀4、良好3、一般2、及格1、不及格或存在逾期且未归还书籍时为0。

## 项目结构

<img src=".\image\image-20240801094530266.png" alt="image-20240801094530266" />

项目大文件夹下包括lib与src两个文件，其中，lib存储项目所用到的依赖，src存储项目的实现方法以及资源文件。

其中src.mian.java中包含具体的代码，main,src.resources中包含项目所有的资源文件。

 

<img src=".\image\image-20240801094600806.png" alt="image-20240801094600806" />

在Java文件夹下，各个文件夹的具体功能分别如下：

- AI：连接AI，实现对蓝心大模型接口的调用。
- controller：对前端代码进行控住，并将初始化好的数据添加到前端
- database： 操纵数据库，实现增删改查
- entity： 存储项目所需要的所有实体类
- file：通过easy Excel的调用，实现对Excel文件的读写操作
- util： 存储该项目所需要的工具类，如字符串处理，时间处理等操作  



<img src=".\image\image-20240801094627055.png" alt="image-20240801094627055" />

在resource文件夹下，各个模块功能如下所示：

- admin：用来存储管理端fxml布局文件
- css：存储JavaFX所需的css美化效果
- images：存储项目所需的所有图片
- student：存储用户端的fxml布局文件

## 功能界面

### 用户端
<img src=".\image\login.png" alt="image-20240801094252258" style="zoom:80%;" />



<img src=".\image\image-20240801095459110.png" alt="image-20240801095459110"  />

<img src=".\image\image-20240801095633237.png" alt="image-20240801095633237" />

<img src=".\image\image-20240801095944742.png" alt="image-20240801095944742" />

<img src=".\image\image-20240801095956638.png" alt="image-20240801095956638" />

<img src=".\image\image-20240801100011627.png" alt="image-20240801100011627" />

<img src=".\image\image-20240801100028514.png" alt="image-20240801100028514" />

<img src=".\image\image-20240801100045608.png" alt="image-20240801100045608" />

### 管理端
<img src=".\image\image-20240801100105229.png" alt="image-20240801100105229" />

<img src=".\image\image-20240801100125490.png" alt="image-20240801100125490" />

<img src=".\image\image-20240801100140431.png" alt="image-20240801100140431" />
