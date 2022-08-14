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

引入依赖：
```xml
<!-- 版本请以maven仓库最版为准 -->
<dependency>
    <groupId>cn.xiaoandcai</groupId>
    <artifactId>jsonDiff</artifactId>
    <version>1.0.0</version>
</dependency>
```

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





```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.0</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>cn.xiaoandcai</groupId>
    <artifactId>JsonDiff</artifactId>
    <version>1.0.0</version>
    <packaging>pom</packaging>
    <name>JsonDiff</name>
    <url>https://gitee.com/local-li/json-diff</url>
    <description>一款高性能的Json差异发现工具</description>

    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>


    <licenses>
        <license>
            <name>Apache License, Version 2.0</name>
            <url>https://gitee.com/local-li/json-diff/blob/master/LICENSE</url>
            <distribution>repo</distribution>
            <comments>A business-friendly OSS license</comments>
        </license>
    </licenses>

    <scm>
        <url>https://gitee.com/local-li/json-diff</url>
        <connection>https://gitee.com/local-li/json-diff.git</connection>
    </scm>

    <dependencies>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>com.alibaba.fastjson2</groupId>
            <artifactId>fastjson2</artifactId>
            <version>2.0.7</version>
        </dependency>
    </dependencies>

    <developers>
        <developer>
            <name>codeleep</name>
            <id>codeleep</id>
            <email>codeleep@163.com</email>
            <roles>
                <role>Developer</role>
            </roles>
            <timezone>+8</timezone>
        </developer>
    </developers>

    <profiles>
        <profile>
            <id>default</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <distributionManagement>
                <snapshotRepository>
                    <id>ossrh</id>
                    <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
                </snapshotRepository>
                <repository>
                    <id>ossrh</id>
                    <url>https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/</url>
                </repository>
            </distributionManagement>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.sonatype.plugins</groupId>
                        <artifactId>nexus-staging-maven-plugin</artifactId>
                        <version>1.6.7</version>
                        <extensions>true</extensions>
                        <configuration>
                            <serverId>ossrh</serverId>
                            <nexusUrl>https://s01.oss.sonatype.org/</nexusUrl>
                            <autoReleaseAfterClose>true</autoReleaseAfterClose>
                        </configuration>
                    </plugin>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-source-plugin</artifactId>
                        <version>2.2.1</version>
                        <executions>
                            <execution>
                                <id>attach-sources</id>
                                <goals>
                                    <goal>jar-no-fork</goal>
                                </goals>
                            </execution>
                        </executions>
                    </plugin>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-javadoc-plugin</artifactId>
                        <version>2.9.1</version>
                        <configuration>
                            <!-- jdk1.8要加上，1.7要去掉，否则会报错 -->
                            <additionalJOptions>
                                <additionalJOption>-Xdoclint:none</additionalJOption>
                            </additionalJOptions>
                        </configuration>
                        <executions>
                            <execution>
                                <id>attach-javadocs</id>
                                <goals>
                                    <goal>jar</goal>
                                </goals>
                            </execution>
                        </executions>
                    </plugin>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-gpg-plugin</artifactId>
                        <version>1.5</version>
                        <executions>
                            <execution>
                                <id>sign-artifacts</id>
                                <phase>verify</phase>
                                <goals>
                                    <goal>sign</goal>
                                </goals>
                            </execution>
                        </executions>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

