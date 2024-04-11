<h1 style="text-align: center">JsonDiff 高性能json差异发现工具</h1>
<div style="text-align: center">


[![GitHub license](https://img.shields.io/github/license/codeleep/json-diff)](https://github.com/codeleep/json-diff/blob/master/LICENSE)
[![star](https://gitee.com/codeleep/json-diff/badge/star.svg?theme=white)](https://gitee.com/codeleep/json-diff/stargazers)
<a href='https://gitee.com/codeleep/json-diff/members'><img src='https://gitee.com/codeleep/json-diff/badge/fork.svg?theme=white' alt='fork'></img></a>
[![GitHub stars](https://img.shields.io/github/stars/codeleep/json-diff)](https://github.com/codeleep/json-diff/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/codeleep/json-diff)](https://github.com/codeleep/json-diff/network)
[![GitHub issues](https://img.shields.io/github/issues/codeleep/json-diff)](https://github.com/codeleep/json-diff/issues)

</div>

## 一、介绍

它几乎可以发现任何JSON结构的差异，并且将错误信息反馈给用户。

### 1.优点

- 高效的
- 精准定位差异
- 轻量级
- 依赖非常干净，只依赖fastJson





## 二、使用文档

### 1.快速开始

引入依赖：

```xml
<dependency>
    <groupId>cn.xiaoandcai</groupId>
    <artifactId>json-diff</artifactId>
    <!-- 旧版本可能存在某些缺陷。版本请以maven仓库最版为准。 -->
    <version>${version}</version>
</dependency>
```
[版本查看](./VersionHistory.md)
2024-04-11 最新版本：4.0.6-RC1-RELEASE

```java
/**
 * @author: codeleep
 * @createTime: 2022/11/22 16:57
 * @description: 使用示例
 */
public class UseExample {

    public static void main(String[] args) {
        String array1 = "[1, 2, 3, 4, 5]";
        String array2 = "[1, 3, 9, 4, 5]";

        JsonComparedOption jsonComparedOption = new JsonComparedOption().setIgnoreOrder(true);
        JsonCompareResult jsonCompareResult = new DefaultJsonDifference()
                .option(jsonComparedOption)
                .detectDiff(array1, array2);
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
            "actual": 9,
            "expect": 2,
            "illustrate": "The expect('2') data is inconsistent with the actual('9') data",
            "indexPath": "root[]"
        }
    ],
    "match": false
}
```

工具会返回两个json对象的差异。



### 2.配置

| 配置             | 类型                           | 备注                                                         |
| ---------------- | ------------------------------ | ------------------------------------------------------------ |
| ignoreOrder      | boolean                        | 是否比较过程中忽略数组顺序                                   |
| mapping          | Map<String, String>            | 将真实字段映射到期望字段，key是真实字段name，value是期望的字段name |
| ignorePath       | Set\<String\>                  | 当对比的路径完全匹配时会被跳过。遇到数组使用 `[]` 即可。无需填入下标 |
| ignoreKey        | Set\<String\>                  | 对比object时。或忽略该key。对整个json生效                    |

> 在 `2.0.1-RC1-RELEASE` 之后版本中移除了 `keyFunction` 配置参数。可以使用 `ignorePath` 来代替达到同样的效果。

工具提供了四个配置，来之对比过程中一些其他的要求。工具还在积极开发中，如果有新的需求，可以给作者提一个issuse。

在开发中。很多时候对比配置一致。可以使用 `JsonDiffOption` 进行开启唯一配置。这样也将获取更好的性能；
```java

```

### 3.进阶

#### 3.1. 全局使用固定配置

由于在设计中考虑到各线程比较配置相互独立。所以默认将配置防止在 `ThreadLocal` 中进行存储。但在大多数情况下，我们在全局比较时，配置并不会发生变化。

工具提供了全局配置方式。采用的方式是静态类属性。这样也会获得更好的性能。

```java
// 开启并设置全局配置
JsonDiffOption.openUniqueOption();
JsonDiffOption.setGloballyUniqueOption(new JsonComparedOption());
// 不想使用时可以调用调整回线程独有模式
 JsonDiffOption.closeUniqueOption();
```

#### 3.2. 数组元素为对象关联

当我们在遇到数组元素是一个对象时。如下：

```json
[
    {
        "date": "23日星期五",
        "sunrise": "06:16",
        "high": "高温 18.0℃"
    },
    {
        "date": "24日星期六",
        "sunrise": "06:14",
        "high": "高温 21.0℃"
    }
]
```

在比较时, 如果希望 `date` 字段一致，则认为两个对象一致。那么可以将 `sunrise`, `high` 字段都配置到 `ignorePath` 中。如：

```java
HashSet<String> ignorePath = new HashSet<>();
ignorePath.add("root[].sunrise");
ignorePath.add("root[].high");
```

如果只是不想关注某个字段。即是 `ignorePath` 正常用法。配置如上。

#### 3.3. 字段映射

在比较两个对象时。也许由于字段名变更。导致校验不通过。这时可以使用 `mapping` 配置。将 真实字段名称映射至期望字段名称。在比较过程中会将

actual.mappingKey 与 expect.mappingValue 认为是应该比较的对象。具体配置如下

```java
// mapping key 是 actual 键名
// mapping value 是 expect 键名
HashMap<String, String> mapping = new HashMap<>();
mapping.put("date", "sunrise");
```



#### 3.4. 字段忽略

如果有一些字段是想在整个json都进行忽略的，可以使用 `ignoreKey` 进行全局忽略。当然如果不想全局忽略，但是配置了该项，还是会被忽略掉。

```java
HashSet<String> ignoreKey = new HashSet<>();
ignoreKey.add("sunrise");
ignoreKey.add("high");
```



#### 3.5 自定义比较器

在某些特俗场景下，我们需要自己实现比较器。这时可以通过 `me.codeleep.jsondiff.core.handle.HandleFactory` 来进行实现。可以参考 `me.codeleep.jsondiff.core.handle.AbstractHandleFactory` 实现

将我们的实现设置到JsonComparedOption中即可生效。

对于各个类型的比较器可以尝试继承系统比较器来进行使用：

- me.codeleep.jsondiff.core.handle.array.ComplexArrayJsonNeat
- me.codeleep.jsondiff.core.handle.object.ComplexObjectJsonNeat
- me.codeleep.jsondiff.core.handle.other.ComplexOtherJsonNeat
- me.codeleep.jsondiff.core.handle.primitive.ComplexPrimitiveJsonNeat

在工具中，目前提供了一个 me.codeleep.jsondiff.core.handle.custom.AlignArrayJsonDiff 来实现当数组长度不一致时，进行补齐，从而避免直接返回错误信息。




### 4.其他说明

前面提到工具几乎可以支持所有json结果的对比校验，并且发现差异。那它到底可以支持哪些呢，不知道是否符合你的需求呢？

- 对象 ✅

  这是最简单的数据结构了，其中元素都以key-value构成，key是字符串，value可以是任何数据结构。

- 数组 ✅

  支持严格顺序对比和忽略顺序对比，可以细化数组元素的类型

    - 基本类型 ✅

    - 对象类型 ✅

      该类型在对比时，可以通过ignorePath参数进行元素是否进行比较，将不关心的元素忽略掉。当然ignoreKey也可以，但其是全局生效

    - 数组类型 ✅

      元素也是数组，这样就形成了多维数组，工具理论上来说支持n维数组的对比

    - 元素类型不统一 ✅

      数组中，类型可能包含前面三种类型，这时工具会按照类型分类进行匹配，最后找不到的元素再反馈给用户。

由于json结构在单个看来，就只有对象和数组两种类型，该工具完美支持了所有类型。

#### 

## 三、交流群：710435809
- 如果长时间未通过可发邮件至 codeleep@163.com 与我联系
- 如果觉得对你有帮助，欢迎star。你的start是对我最大的鼓励。



## 四、测试用例编写规范

### 1.测试用例文件字段

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
        "mapping": {"a": "b"},
        "ignorePath": ["root[].a"],
        "ignoreKey": ["b"]
      }
    }
  ],
  "err"[.....],
  "optionRight":[......],
  "optionErr":[........],
}
```

每一个测试文件结构都如上，right，err，optionRight，optionErr分别为测试种类名称，测试名称下对应的是每一个测试用例[]中每一个object都是对应的一个用例每一测试用例为如下类似的object

| 名称                          | 类型 | 备注         |
| ----------------------------- | ---- | :----------- |
| right                         | 数组 | 正常用例     |
| err                           | 数组 | 异常用例     |
| optionRight                   | 数组 | 配置正常用例 |
| optionErr                     | 数组 | 配置异常用例 |
| 注： right，err，不会加载配置 |      |              |

```json
    {
      "actual": [{"a": 1}],
      "expect": [{"b": 2}],
      "ret": {},
      "option": {
        "ignoreOrder": false,
        "mapping": {"a": 1},
        "ignorePath": ["root[].a"],
        "ignoreKey": ["b"]
      }
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

### 2.执行测试

#### 直接进入测试代码执行

进入src/test/java/me/codeleep/jsondiff/test/ 中有两个后缀为Test的java文件，可直接运行该文件，也能进入其中运行指定方法
注：每一个方法运行都会把对应集合的方法全部运行一遍

#### 通过TestNG xml文件执行

进入src/test/resources/testNG 其中有三个文件，分别是执行全部的case，执行全部异常或者匹配错误错误的用例，执行全部应当匹配正确 