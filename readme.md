# 开发log

## 1、文件夹命名

### vue

前端代码存放点

### spring

后端代码存放点

## 2、开发过程

### 2023年02月10日21:43:47

#### 前端部分

vue-cli安装：npm install -g @vue/cli

npm设置淘宝镜像加速：npm config set registry https://registry.npm.taobao.org

创建vue项目：`vue create vue`

运行vue项目：

```
cd vue

npm run serve
```

安装ElementUI：`npm i element-ui -S`

#### 后端部分

application.java 中添加下面注解，解决前端页面/error问题

```java
@RestController
```

application.properties中添加链接数据库，端口设置为8085，解决跨域问题

```
server.port=8085
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/newProject?serverTimezone=GMT%2b8
spring.datasource.username=root
spring.datasource.password=
```

pom文件配置如下：

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.5.9</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.qingge</groupId>
    <artifactId>springboot</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springboot</name>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.2.1</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>nexus-aliyun</id>
            <name>nexus-aliyun</name>
            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>

    <pluginRepositories>
        <pluginRepository>
            <id>public</id>
            <name>aliyun nexus</name>
            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </pluginRepository>
    </pluginRepositories>

</project>
```

### 2023年02月11日22:54:59

#### 后端部分

entity包中新建student表，与SQL表中字段一一对应。

**其中**，createTime需要引入java.sql包，字段改为Timestamp，同时用@JsonFormat进行格式化

~~~java
package com.zzl.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 学生表
 */
@Data
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生id，唯一
     */
    private Integer id;

    /**
     * 学生学号，要唯一，不能重复
     */
    private String studentNo;

    /**
     * 学生登录密码
     */
    private String studentPassword;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生性别
     */
    private String studentSex;

    /**
     * 学院
     */
    private String studentCollege;

    /**
     * 专业
     */
    private String studentMajor;

    /**
     * 学生绩点
     */
    private Double studentGpa;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

}

~~~

mapper包中新增StudentMapper，编写CURD接口相关代码

~~~java
package com.zzl.mapper;

import com.zzl.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {

    /**
     * 查询所有用户
     */
    @Select("select * from student")
    List<Student> findAll();
}
~~~

在controller中新增StudentController

```java
package com.zzl.controller;

import com.zzl.entity.Student;
import com.zzl.mapper.StudentMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class studentController {

    @Resource
    private StudentMapper studentMapper;

    @GetMapping("/")
    public List<Student> index() {
        List<Student> all = studentMapper.findAll();
        return all;
    }
}

```

