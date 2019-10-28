# 自动化代码生成工具
让程序制造代码，从此告别996。解放双手，解放生命，解放全人类！

## 使用方法
* 执行测试用例，可根据数据库表结构，自动生成pojo,dao,mapper,service,controller及页面全栈代码
* spring-generator.xml：生成器的配置文件
* generator-config.properties：运行环境配置文件

## 注释参数
* 注释 -E枚举参数(enumeration)s -S搜索参数(searchable) -I忽略字段(ignore)
* 举例：状态 -E1:启用,2:禁用 -S  
* 含义：字段含义为“状态”,1代表启用2代表禁用并且该字段会出现在搜索栏中

## 更新历史
### 2019-04-22
1. -I参数(ignore)：忽略字段，不参与自动生成
### 2019-04-15
1. 抽取CodeStyle工具方法到StringUtils工具类中
1. Field支持枚举类型
1. 增加complexResourceName变量
1. 增加对搜索条件的支持

## TODO
1.下拉列表支持绑定字典
2.支持时间范围选择
3.增加字符串类型的枚举参数处理
