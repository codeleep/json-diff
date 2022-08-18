<h1 style="text-align: center">JsonDiff 高性能json差异发现工具</h1>
<div style="text-align: center">

[![GitHub license](https://img.shields.io/github/license/local-li/jsonDiff)](https://github.com/local-li/jsonDiff/blob/master/LICENSE)
[![star](https://gitee.com/local-li/json-diff/badge/star.svg?theme=white)](https://gitee.com/local-li/json-diff/stargazers)
<a href='https://gitee.com/local-li/json-diff/members'><img src='https://gitee.com/local-li/json-diff/badge/fork.svg?theme=white' alt='fork'></img></a>
[![GitHub stars](https://img.shields.io/github/stars/local-li/jsonDiff)](https://github.com/local-li/jsonDiff/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/local-li/jsonDiff)](https://github.com/local-li/jsonDiff/network)
[![GitHub issues](https://img.shields.io/github/issues/local-li/jsonDiff)](https://github.com/local-li/jsonDiff/issues)

</div>

## 介绍

它几乎可以发现任何JSON结构的差异，并且将错误信息反馈给用户。

### 优点

- 高效的
- 精准定位差异
- 轻量级
- 依赖非常干净，只依赖fastJson





## 使用文档

### 快速开始

引入依赖：
```xml
<!-- 版本请以maven仓库最版为准 -->
<dependency>
    <groupId>cn.xiaoandcai</groupId>
    <artifactId>jsonDiff</artifactId>
    <version>1.1.0</version>
</dependency>
```

```java
class {
    public void diffKeepOrder() {
        String array1 = "[1, 2, 3, 4, 5]";
        String array2 = "[1, 6, 3, 4, 5]";

        JsonComparedOption jsonComparedOption = new JsonComparedOption().setIgnoreOrder(true);
        JsonCompareResult jsonCompareResult = new DefaultJsonDifference()
        .option(jsonComparedOption)
        .detectDiff(JSON.parseArray(array1), JSON.parseArray(array2));
        System.out.println(JSON.toJSONString(jsonCompareResult));
    }
}
```

主要分为两个对象 JsonComparedOption.class 和 JsonCompareResult.class；分别是配置对象和处理对象。

返回结果：

```json
{
  "defectsList": [
    {
      "actual": 6,
      "expect": 2,
      "illustrate": "The 1 element is inconsistent",
      "indexPath": "root[1]"
    }
  ],
  "match": false
}
```

工具会返回两个json对象的差异。



### 配置

| 配置        | 类型                            | 备注                                                         |
| ----------- | ------------------------------- | ------------------------------------------------------------ |
| ignoreOrder | boolean                         | 是否比较过程中忽略数组顺序                                   |
| mapping     | Map<String, String>             | 将真实字段映射到期望字段，key是真实字段name，value是期望的字段name |
| ignorePath  | List<String>                    | 忽略的path，当对比到对应的层级时，会被跳过                   |
| keyFunction | Function<String, Stack<String>> | ignoreOrder=true时，数组是元素对象时, 指定根据哪些key联系对象。入参是当前的path |



## 测试用例编写规范
### 测试用例文件字段
文件中数据结构
```json
{
  "right": [
    {
      "actual": [{"a": 1}],
      "expect": [{"b": 2}],
      "ret": {},
      "option": {
        "ignoreOrder": false,
        "mapping": {"a": 1},
        "ignorePath": [".a/a"],
        "ignoreKey": [".a.b"]
      }
    }
  ],
  "err"[.....],
  "optionRight":[......],
  "optionErr":[........],
}
```
每一个测试文件结构都如上，right，err，optionRight，optionErr分别为测试种类名称，测试名称下对应的是每一个测试用例[]中每一个object都是对应的一个用例每一测试用例为如下类似的object

| 名称          | 类型   | 备注      |
|-------------|------|:--------|
| right       | 数组   | 正常用例    |
| err         | 数组   | 异常用例    |
| optionRight | 数组   | 配置正常用例  |
| optionErr   | 数组   | 配置异常用例  |
注： right，err，不会加载配置
```json
    {
      "actual": [{"a": 1}],
      "expect": [{"b": 2}],
      "ret": {},
      "option": {
        "ignoreOrder": false,
        "mapping": {"a": 1},
        "ignorePath": [".a/a"],
        "ignoreKey": [".a.b"]
      }
```
"actual": 实际需要对比的值 类型：测试array文件为array 测试object文件为object   
"expect": 期望该值为什么   类型：测试array文件为array 测试object文件为object  
"ret": 返回内容，如果用例为right中的自动判断返回内容中match是否为true进行判断   类型：object  
"option": 配置信息 对应本项目中的配置文件  类型：Object  
&emsp;&emsp; "ignoreOrder": 是否忽略数组书序 类型：Boolean  
&emsp;&emsp; "mapping": key 是 actual   value 是 expect 映射 类型：Object   
&emsp;&emsp; "ignorePath": 忽略的path. 以 . 来区分json层级; 会精准匹配路径  类型：array  
&emsp;&emsp; "ignoreKey": 忽略的key。actual中有的字段，但expect没有的，会被忽略掉    类型：array  

### 执行测试
#### 直接进入测试代码执行
进入src/test/java/me/codeleep/jsondiff/test/ 中有两个后缀为Test的java文件，可直接运行该文件，也能进入其中运行指定方法
注：每一个方法运行都会把对应集合的方法全部运行一遍
#### 通过TestNG xml文件执行
进入src/test/resources/testNG 其中有三个文件，分别是执行全部的case，执行全部异常或者匹配错误错误的用例，执行全部应当匹配正确