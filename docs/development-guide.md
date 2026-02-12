# SmartMES 开发指南

## 1. 开发环境搭建

### 1.1 硬件要求

- **开发机器**：至少 16GB 内存，500GB 硬盘，8 核 CPU
- **操作系统**：Windows 10/11、Linux（Ubuntu 20.04+）、macOS 10.15+

### 1.2 软件要求

- **JDK**：Java 25 或更高版本
- **Maven**：3.8.6 或更高版本
- **IDE**：IntelliJ IDEA（推荐）或 Eclipse
- **数据库**：MySQL 8.0 或更高版本
- **Git**：2.30 或更高版本
- **Tomcat**：9.0 或更高版本（用于本地部署测试）

### 1.3 环境配置步骤

1. **安装 JDK**
   - 下载并安装 Java 25
   - 配置环境变量 `JAVA_HOME`
   - 将 `%JAVA_HOME%\bin` 添加到 `PATH`

2. **安装 Maven**
   - 下载并解压 Maven 3.8.6+
   - 配置环境变量 `MAVEN_HOME`
   - 将 `%MAVEN_HOME%\bin` 添加到 `PATH`

3. **安装 Git**
   - 下载并安装 Git
   - 配置用户信息：
     ```bash
     git config --global user.name "Your Name"
     git config --global user.email "your.email@example.com"
     ```

4. **安装 MySQL**
   - 下载并安装 MySQL 8.0+
   - 创建数据库：
     ```sql
     CREATE DATABASE mes CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
     CREATE USER 'mes'@'localhost' IDENTIFIED BY 'mes123456';
     GRANT ALL PRIVILEGES ON mes.* TO 'mes'@'localhost';
     FLUSH PRIVILEGES;
     ```

5. **克隆代码库**
   ```bash
   git clone https://github.com/luyang668899/SmartMES.git
   cd SmartMES
   ```

6. **导入项目到 IDE**
   - **IntelliJ IDEA**：
     - 选择 "Open" -> 选择 SmartMES 目录
     - 选择 "Import as Maven project"
     - 等待依赖下载完成
   - **Eclipse**：
     - 选择 "File" -> "Import" -> "Existing Maven Projects"
     - 选择 SmartMES 目录
     - 点击 "Finish"

7. **配置数据库连接**
   - 修改 `mes-application/conf/dev/db.properties` 文件：
     ```properties
     db.url=jdbc:mysql://localhost:3306/mes?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC
     db.username=mes
     db.password=mes123456
     ```

8. **构建项目**
   ```bash
   mvn clean install -DskipTests
   ```

## 2. 项目结构

### 2.1 核心模块

- **mes-application**：主应用程序，包含核心功能和 Web 界面
- **mes-plugins**：插件集合，包含各种功能模块

### 2.2 插件结构

每个插件遵循以下结构：

```
mes-plugins-xxx/
├── src/
│   ├── main/
│   │   ├── java/          # Java 源代码
│   │   ├── resources/     # 资源文件
│   │   │   ├── xxx/        # 插件特有资源
│   │   │   │   ├── model/  # 数据模型定义（XML）
│   │   │   │   ├── view/   # 视图定义（XML）
│   │   │   │   └── report/ # 报表定义
│   │   │   ├── qcadoo-plugin.xml  # 插件配置
│   │   │   └── root-context.xml   # Spring 配置
│   └── test/              # 测试代码
└── pom.xml                # Maven 配置
```

### 2.3 关键文件

- **qcadoo-plugin.xml**：插件元数据和依赖配置
- **model/*.xml**：数据模型定义，包括实体、字段、关系等
- **view/*.xml**：视图定义，包括表单、列表、导航等
- **root-context.xml**：Spring 应用上下文配置

## 3. 插件开发

### 3.1 创建新插件

1. **复制模板插件**
   - 复制 `mes-plugins-basic` 目录为新插件目录
   - 重命名目录为 `mes-plugins-xxx`（xxx 为插件名称）

2. **修改 pom.xml**
   - 更新 `artifactId`、`name` 和 `description`
   - 调整依赖关系

3. **修改 qcadoo-plugin.xml**
   - 更新插件 ID、名称和版本
   - 配置插件依赖

4. **添加到父模块**
   - 在 `mes-plugins/pom.xml` 中添加新插件模块

### 3.2 数据模型开发

1. **定义实体**
   - 在 `src/main/resources/xxx/model/` 目录下创建 XML 文件
   - 定义实体、字段、关系等

   ```xml
   <model xmlns="http://www.qcadoo.org/model/1.0">
       <entity name="MyEntity" package="com.qcadoo.mes.xxx">
           <field name="name" type="string" required="true"/>
           <field name="description" type="text"/>
           <field name="active" type="boolean" defaultValue="true"/>
           <many-to-one name="parent" entity="MyEntity"/>
           <one-to-many name="children" entity="MyEntity" mappedBy="parent"/>
       </entity>
   </model>
   ```

2. **生成实体类**
   - 运行 Maven 构建：
     ```bash
     mvn clean install -DskipTests
     ```
   - 实体类将自动生成到 `target/generated-sources` 目录

### 3.3 视图开发

1. **定义表单**
   - 在 `src/main/resources/xxx/view/` 目录下创建 XML 文件
   - 定义表单字段、布局等

   ```xml
   <view xmlns="http://www.qcadoo.org/view/1.0">
       <form name="myEntityForm" entity="MyEntity">
           <field name="name"/>
           <field name="description"/>
           <field name="active"/>
           <field name="parent"/>
       </form>
   </view>
   ```

2. **定义列表**
   - 创建列表视图 XML 文件：

   ```xml
   <view xmlns="http://www.qcadoo.org/view/1.0">
       <list name="myEntityList" entity="MyEntity">
           <column name="name"/>
           <column name="active"/>
           <column name="parent.name"/>
           <action name="view"/>
           <action name="edit"/>
           <action name="delete"/>
       </list>
   </view>
   ```

3. **定义导航**
   - 在 `qcadoo-plugin.xml` 中配置菜单：

   ```xml
   <plugin>
       <menus>
           <menu name="myEntity" title="My Entity" parent="plugins">
               <menu name="myEntityList" title="List" view="MyEntityList"/>
               <menu name="myEntityForm" title="New" view="MyEntityForm"/>
           </menu>
       </menus>
   </plugin>
   ```

### 3.4 业务逻辑开发

1. **创建服务类**
   - 在 `src/main/java/com/qcadoo/mes/xxx/service/` 目录下创建服务类

   ```java
   package com.qcadoo.mes.xxx.service;

   import com.qcadoo.mes.xxx.model.MyEntity;
   import com.qcadoo.model.api.DataDefinition;
   import com.qcadoo.model.api.DataDefinitionService;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;

   @Service
   public class MyEntityService {

       @Autowired
       private DataDefinitionService dataDefinitionService;

       private DataDefinition getMyEntityDD() {
           return dataDefinitionService.get("xxx", "myEntity");
       }

       public MyEntity createMyEntity(String name, String description) {
           MyEntity myEntity = (MyEntity) getMyEntityDD().create();
           myEntity.setName(name);
           myEntity.setDescription(description);
           myEntity.setActive(true);
           return (MyEntity) getMyEntityDD().save(myEntity);
       }
   }
   ```

2. **创建控制器**
   - 在 `src/main/java/com/qcadoo/mes/xxx/controller/` 目录下创建控制器

   ```java
   package com.qcadoo.mes.xxx.controller;

   import com.qcadoo.mes.xxx.service.MyEntityService;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Controller;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.servlet.ModelAndView;

   @Controller
   @RequestMapping("/myEntity")
   public class MyEntityController {

       @Autowired
       private MyEntityService myEntityService;

       @RequestMapping("/list")
       public ModelAndView list() {
           ModelAndView mav = new ModelAndView("myEntityList");
           return mav;
       }
   }
   ```

### 3.5 数据访问

1. **使用 DataDefinition**
   ```java
   // 获取数据定义
   DataDefinition myEntityDD = dataDefinitionService.get("xxx", "myEntity");
   
   // 创建实体
   MyEntity myEntity = (MyEntity) myEntityDD.create();
   
   // 保存实体
   myEntity = (MyEntity) myEntityDD.save(myEntity);
   
   // 查询实体
   MyEntity foundEntity = (MyEntity) myEntityDD.get(id);
   
   // 删除实体
   myEntityDD.delete(id);
   ```

2. **使用 QueryDSL**
   ```java
   // 创建查询
   List<MyEntity> entities = myEntityDD.find()
       .add(SearchRestrictions.eq("active", true))
       .addOrder(SearchOrders.asc("name"))
       .list()
       .getEntities();
   ```

## 4. 前端开发

### 4.1 视图配置

- **表单配置**：定义字段、布局、验证规则
- **列表配置**：定义列、过滤器、排序
- **导航配置**：定义菜单、权限

### 4.2 JavaScript 开发

- **事件处理**：表单提交、字段变化、按钮点击
- **AJAX 调用**：异步数据加载、保存
- **前端验证**：客户端数据验证

### 4.3 CSS 定制

- **主题定制**：修改系统外观
- **响应式设计**：适配不同屏幕尺寸

## 5. 测试

### 5.1 单元测试

- **服务测试**：测试业务逻辑
- **控制器测试**：测试 API 接口
- **数据访问测试**：测试数据操作

### 5.2 集成测试

- **功能测试**：测试完整业务流程
- **端到端测试**：测试用户交互

### 5.3 性能测试

- **负载测试**：测试系统在高负载下的表现
- **响应时间测试**：测试页面加载和操作响应时间

## 6. 部署

### 6.1 开发环境部署

1. **启动 Tomcat**
   - 配置 `CATALINA_HOME` 环境变量
   - 启动 Tomcat：`%CATALINA_HOME%\bin\startup.bat`（Windows）或 `$CATALINA_HOME/bin/startup.sh`（Linux）

2. **部署应用**
   - 将 `mes-application/target/mes.war` 复制到 `%CATALINA_HOME%\webapps` 目录
   - Tomcat 会自动解压并部署

3. **访问系统**
   - 打开浏览器：http://localhost:8080/mes
   - 登录：默认用户名 `admin`，密码 `admin`

### 6.2 生产环境部署

1. **配置生产环境**
   - 修改 `mes-application/conf/prod/` 目录下的配置文件
   - 配置数据库连接、邮件服务器、缓存等

2. **构建生产版本**
   ```bash
   mvn clean install -Pprod -DskipTests
   ```

3. **部署到生产服务器**
   - 将 `mes-application/target/mes.war` 复制到生产服务器的 Tomcat webapps 目录
   - 配置服务器防火墙、SSL 证书等

4. **系统初始化**
   - 登录系统
   - 配置基础数据：公司、工厂、用户等
   - 配置业务参数：生产流程、质量标准等

## 7. 最佳实践

### 7.1 编码规范

- **Java 编码规范**：遵循 Google Java Style Guide
- **XML 编码规范**：使用缩进、统一命名
- **命名约定**：
  - 类名：大驼峰命名法（如 `MyEntityService`）
  - 方法名：小驼峰命名法（如 `createMyEntity`）
  - 变量名：小驼峰命名法（如 `myEntityName`）
  - 常量名：全大写，下划线分隔（如 `MAX_RESULTS`）

### 7.2 性能优化

- **数据库优化**：使用索引、优化查询
- **缓存策略**：合理使用缓存，减少数据库访问
- **代码优化**：避免循环中的数据库操作，使用批处理

### 7.3 安全最佳实践

- **输入验证**：验证所有用户输入
- **权限控制**：基于角色的访问控制
- **密码安全**：使用安全的密码存储方式
- **SQL 注入防护**：使用参数化查询
- **XSS 防护**：转义用户输入

### 7.4 文档规范

- **代码注释**：为关键代码添加注释
- **API 文档**：使用 Javadoc 生成 API 文档
- **开发文档**：记录架构设计、开发流程
- **用户文档**：编写用户手册、操作指南

## 8. 常见问题

### 8.1 开发环境问题

- **依赖冲突**：检查 Maven 依赖版本
- **编译错误**：检查代码语法、依赖配置
- **数据库连接失败**：检查数据库服务状态、连接参数

### 8.2 运行时问题

- **404 错误**：检查 URL 路径、控制器映射
- **500 错误**：查看系统日志，检查异常信息
- **权限错误**：检查用户权限、角色配置

### 8.3 插件开发问题

- **插件加载失败**：检查 qcadoo-plugin.xml 配置
- **实体生成失败**：检查模型 XML 语法
- **视图显示错误**：检查视图 XML 配置

## 9. 资源

### 9.1 开发工具

- **IntelliJ IDEA**：https://www.jetbrains.com/idea/
- **Eclipse**：https://www.eclipse.org/
- **MySQL Workbench**：https://www.mysql.com/products/workbench/
- **Postman**：https://www.postman.com/

### 9.2 参考文档

- **Spring Framework**：https://spring.io/docs
- **QueryDSL**：http://www.querydsl.com/
- **MySQL**：https://dev.mysql.com/doc/
- **Tomcat**：https://tomcat.apache.org/tomcat-9.0-doc/

### 9.3 社区资源

- **Qcadoo 论坛**：https://forum.qcadoo.org/
- **GitHub Issues**：https://github.com/qcadoo/mes/issues
- **Stack Overflow**：https://stackoverflow.com/

## 10. 附录

### 10.1 插件开发工具

| 工具 | 用途 | 下载链接 |
|------|------|----------|
| Maven | 项目构建 | https://maven.apache.org/download.cgi |
| Git | 版本控制 | https://git-scm.com/downloads |
| MySQL | 数据库 | https://dev.mysql.com/downloads/ |
| Tomcat | 应用服务器 | https://tomcat.apache.org/download-90.cgi |

### 10.2 常用命令

- **构建项目**：`mvn clean install -DskipTests`
- **运行测试**：`mvn test`
- **生成 IDE 配置**：`mvn eclipse:eclipse` 或 `mvn idea:idea`
- **查看依赖树**：`mvn dependency:tree`
- **部署到 Tomcat**：`mvn tomcat7:deploy`（需配置）

### 10.3 故障排除

| 问题 | 可能原因 | 解决方案 |
|------|----------|----------|
| 构建失败 | 依赖下载失败 | 检查网络连接，清理 Maven 缓存 |
| 启动失败 | 端口被占用 | 修改 Tomcat 端口或停止占用端口的进程 |
| 数据库连接失败 | 连接参数错误 | 检查 db.properties 配置 |
| 插件不加载 | 依赖缺失 | 检查 qcadoo-plugin.xml 中的依赖配置 |

---

**作者**：SmartMES 开发团队
**版本**：1.0
**最后更新**：2026-02-12