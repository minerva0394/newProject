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

#### 1、

已经实现集成mybatis框架，实现增删改查功能，并修改了文件框架，具体实现看提交记录

#### 2、

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

### 2023年02月12日16:01:21
#### 前端部分
解决跨域请求：

一、在后端controller类中添加@CrossOrigin注解

>跨域，指的是浏览器不能执行其他网站的脚本。它是由浏览器的同源策略造成的，是浏览器对JavaScript施加的安全限制。
>所谓同源是指，域名，协议，端口均相同，不明白没关系，举个栗子：
>http://www.123.com/index.html 调用 http://www.123.com/server.PHP （非跨域）
>http://www.123.com/index.html 调用 http://www.456.com/server.php （主域名不同:123/456，跨域）
>http://abc.123.com/index.html 调用 http://def.123.com/server.php（子域名不同:abc/def，跨域）
>http://www.123.com:8080/index.html调用 http://www.123.com:8081/server.php（端口不同:8080/8081，跨域）
>http://www.123.com/index.html 调用 https://www.123.com/server.php（协议不同:http/https，跨域）
>请注意：localhost和127.0.0.1虽然都指向本机，但也属于跨域。
>浏览器执行javascript脚本时，会检查这个脚本属于哪个页面，如果不是同源页面，就不会被执行。
>
>当域名www.abc.com下的js代码去访问www.def.com域名下的资源，就会受到限制。
>
>@CrossOrigin可以处理跨域请求，让你能访问不是一个域的文件。

二、后端common包中添加对应config

~~~java
package com.zzl.newprojectspring.common;


import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 配置跨域请求
 */
public class CorsConfig {

    // 当前跨域请求最大有效时长。这里默认1天
    private static final long MAX_AGE = 24 * 60 * 60;


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080"); // 1 设置访问源地址
        corsConfiguration.addAllowedHeader("*"); // 2 设置访问源请求头
        corsConfiguration.addAllowedMethod("*"); // 3 设置访问源请求方法
        corsConfiguration.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**", corsConfiguration); // 4 对接口配置跨域设置
        return new CorsFilter(source);
    }
}

~~~

#### 后端部分

实现模糊查询，前端显示数据库具体模拟数据

Mybatis-plus依赖

~~~xml
        <!-- mybatis-plus -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.5.1</version>
        </dependency>
~~~

驼峰命名法时需要注意，mybatisPlus会讲后面的大写转成下划线，需要在entity中添加注解

~~~ java
@TableField("")
~~~

后端实现swagger集成，在调试的时候，需要对参数进行如下设置

```java
@ApiImplicitParams({
            @ApiImplicitParam(dataType = "Integer", name = "pageNum", value = "页码", required = false),
            @ApiImplicitParam(dataType = "Integer", name = "pageSize", value = "每页数量", required = false),
            @ApiImplicitParam(name = "studentName", value = "学生姓名", required = false),
            @ApiImplicitParam(name = "studentNo", value = "学生学号", required = false),
            @ApiImplicitParam(name = "studentSex", value = "学生性别", required = false),
            @ApiImplicitParam(name = "studentMajor", value = "学生专业", required = false)
})
```

确保datatype和数据库保持一致，确保required和业务逻辑一致



### 2023年02月13日10:36:47
#### 前端部分
Axios 封装

``` npm i axios -S
npm i axios -S
```

request.js

~~~ js
import axios from 'axios'

const request = axios.create({
    baseURL: '/api',
    timeout: 5000
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    // config.headers['token'] = user.token;  // 设置请求头
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)


export default request

~~~

Homevue.js

封装方法

~~~ js
      this.request.get("http://localhost:8085/student/page?",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          studentName:this.studentName,
          studentNo:this.studentNo,
          studentSex:this.studentSex,
          studentMajor:this.studentMajor
        }
      }).then(res => {
        console.log()
        this.total = res.total
        this.tableData = res.records
      })
~~~



### 2023年02月14日00:10:25

已实现vue的增删改查，接口描述方式统一

#### 后端部分

数据库字段新增逻辑删除符号位

```properties
#逻辑删除配置
# 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
mybatis-plus.global-config.db-config.logic-delete-field=deleted
# 逻辑已删除值(默认为 1)
mybatis-plus.global-config.db-config.logic-delete-value= 1
# 逻辑未删除值(默认为 0)
mybatis-plus.global-config.db-config.logic-not-delete-value= 0
```

```java
/**
 * 逻辑删除符号位
 */
@ApiModelProperty(value = "是否删除，0=存在，1=删除")
@TableLogic
private Integer deleted;
```



```java
//设置分页插件属性，请求页超出范围时，返回上一页
PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
paginationInnerInterceptor.setOverflow(true);
```

设置分页，删除最后一页且最后一页无数据时，返回改页的上一页

同时需要设置前端参数

#### 前端部分

```vue
handleDelete(studentNo) {
  this.request.delete("/student/delete/" + studentNo).then(res => {
    if (res) {
      this.$message.success("删除成功")
      //设置删除最后一页的时候跳转
      this.pageNum = this.pageSize
      this.load()
    } else {
      this.$message.error("删除失败")
    }
  })
},
```

### 2023年02月14日11:04:52

