<h1 style="text-align: center">JsonDiff 高性能json差异发现工具</h1>
<div style="text-align: center">

[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://gitee.com/local-li/json-diff/blob/master/LICENSE)
[![star](https://gitee.com/local-li/json-diff/badge/star.svg?theme=white)](https://gitee.com/wangjiabin-x/uh5)
[![fork](https://gitee.com/local-li/json-diff/badge/fork.svg?theme=white)](https://gitee.com/wangjiabin-x/uh5)

</div>

## 介绍

它几乎可以发现任何JSON结果的差异，并且将错误信息反馈给用户。

### 优点

- 高效的
- 精准定位差异
- 轻量级
- 依赖非常干净，只依赖fastJson





## 使用文档

### 快速开始
```java
class {
    public void diffKeepOrder() {
        MetaData metaData = load(expectPath, actualPath);
        JsonComparedOption jsonComparedOption = new JsonComparedOption().setIgnoreOrder(true);
        JsonCompareResult jsonCompareResult = new DefaultJsonDifference().defaultJsonDifference
                .option(jsonComparedOption)
                .detectDiff((JSONObject) metaData.getExpect(), (JSONObject) metaData.getActual());
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
            "actual": "23日星期五",
            "expect": "23日星期五-------",
            "illustrate": "properties are different",
            "indexPath": "root[4].date"
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

