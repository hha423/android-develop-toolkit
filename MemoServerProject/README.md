Mysql常用操作示例：

create table if not exists user(id integer primary key auto_increment, username varchar(20), password varchar(40))

rename table user to users

drop table if exists user

insert into user(username, password) values('admin','123456')

update user set password='111111' where username='admin'

delete from user where username='admin'

select * from user where username='admin'


一、所用表格
create table if not exists user(id integer primary key not null auto_increment, username varchar(20) not null, password varchar(40) not null, level integer) 

create table if not exists memo(id integer primary key not null auto_increment, userid integer not null, title varchar(30) not null, content text not null , tag varchar(120) , createt varchar(13), updatet varchar(13));

create table if not exists tag(id integer primary key not null auto_increment, name varchar(20) not null);

create table if not exists sms(id integer primary key not null auto_increment, mobile varchar(11), code varchar(6), createtime varchar(13)):

create table if not exists sugest(id integer primary key not null auto_increment, userid integer, content text, createtime varchar(13));

create table if not exists reply(id integer primary key not null auto_increment, userid integer, sugestid integer, content text, createtime varchar(13));

CREATE TABLE IF NOT EXISTS log(id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, userid INTEGER, createtime VARCHAR(13));

二、请求示例
http://allthelucky.ap01.aws.af.cm/console.jsp
http://allthelucky.ap01.aws.af.cm/memoServer

{
    "data": {
        "username": "18672950256",
        "password": "123456",
        "code": "780759",
        "offset": 1,
        "userid": 1,
        "test": {
            "value12": "value12",
            "value1": "value1"
        },
        "size": 15
    },
    "spec": {
        "sign": "abd94d462f67b77405af84616159a0db4ff4b619",
        "appkey": "123456789"
    },
    "action": "get_code",
    "debug": "true"
}

响应示例：
{
    "data": {
        "count": 1,
        "list": [
            {
                "content": "大家好，这是一篇测试日志内容。也是管理员添加的第一条哦。",
                "id": 1,
                "updatet": "2013/08/26",
                "title": "测试日记",
                "createt": "2013/08/24",
                "tag": "测试",
                "userid": 1
            }
        ]
    },
    "code": "00"
}

或
{
    "message": "error sign",
    "code": "-1"
}
