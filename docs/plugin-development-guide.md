# MES 插件开发指南

## 1. 系统架构概述

### 1.1 技术框架

MES 系统采用现代化的技术框架，提供稳定、高效的企业级应用支持。

#### 后端技术
- **Java**：JDK 25 或更高版本
- **Spring Framework**：提供依赖注入、事务管理等核心功能
- **Spring Boot**：简化应用配置和部署
- **AspectJ**：实现面向切面编程，用于日志、事务等横切关注点
- **Maven**：项目构建和依赖管理

#### 前端技术
- **Thymeleaf**：服务器端模板引擎
- **jQuery**：JavaScript 库，简化 DOM 操作
- **Bootstrap**：响应式前端框架
- **AngularJS**：前端 MVVM 框架（可选）

#### 数据存储
- **MySQL**：关系型数据库，存储业务数据
- **Redis**：缓存数据库，提高系统性能

#### 中间件
- **Tomcat**：Java Web 应用服务器
- **ActiveMQ**：消息队列，实现异步通信

### 1.2 插件架构

MES 系统采用插件架构，通过插件机制实现功能的扩展和定制。每个插件都是一个独立的模块，可以单独开发、部署和管理。

### 1.3 插件类型

- **核心插件**：提供基础功能，如基本数据模型、用户管理等
- **业务插件**：提供特定业务功能，如订单管理、供应链管理等
- **集成插件**：提供与外部系统的集成功能
- **分析插件**：提供数据分析和决策支持功能

### 1.4 插件目录结构

```
mes-plugins/
├── mes-plugins-[插件名称]/
│   ├── src/main/
│   │   ├── java/[包路径]/
│   │   └── resources/
│   │       ├── [插件名称]/
│   │       │   ├── model/      # 数据模型定义
│   │       │   ├── view/        # 视图定义
│   │       │   └── report/      # 报表定义
│   │       └── qcadoo-plugin.xml  # 插件定义文件
│   └── pom.xml                  # Maven 配置文件
```

## 2. 插件开发流程

### 2.1 创建插件项目

1. 在 `mes-plugins` 目录下创建新的插件目录
2. 创建 `pom.xml` 文件，配置插件依赖
3. 创建 `src/main/resources/qcadoo-plugin.xml` 文件，定义插件基本信息
4. 创建必要的目录结构

### 2.2 定义数据模型

在 `src/main/resources/[插件名称]/model/` 目录下创建 XML 文件，定义数据模型。

**示例：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<model xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
        xsi:noNamespaceSchemaLocation="http://schema.qcadoo.org/model.xsd">

    <entity name="example" package="example">
        <field name="id" type="id"/>
        <field name="name" type="string" length="255" required="true"/>
        <field name="description" type="string" length="1000"/>
        <!-- 其他字段 -->
    </entity>

</model>
```

### 2.3 创建视图

在 `src/main/resources/[插件名称]/view/` 目录下创建 XML 文件，定义用户界面。

**示例：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<view xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
        xsi:noNamespaceSchemaLocation="http://schema.qcadoo.org/view.xsd">

    <page name="exampleList" extensionOf="list">
        <table name="examples" entity="example">
            <columns>
                <column name="name" sortable="true" filter="true"/>
                <column name="description" sortable="true" filter="true"/>
            </columns>
            <toolbar>
                <button name="add" action="navigateTo" params="{view: 'exampleDetails', mode: 'new'}"/>
                <button name="edit" action="navigateTo" params="{view: 'exampleDetails', mode: 'edit'}" condition="singleSelected"/>
                <button name="delete" action="delete" condition="singleSelected"/>
            </toolbar>
        </table>
    </page>

</view>
```

### 2.4 实现服务层

在 `src/main/java/[包路径]/` 目录下创建 Java 类，实现业务逻辑。

**示例：**

```java
package com.qcadoo.mes.example;

import org.springframework.stereotype.Service;

@Service
public class ExampleService {
    // 业务逻辑实现
}
```

### 2.5 配置插件依赖

在 `qcadoo-plugin.xml` 文件中配置插件依赖关系。

**示例：**

```xml
<dependencies>
    <dependency>
        <plugin>basic</plugin>
        <version>[1.1.8</version>
    </dependency>
</dependencies>
```

### 2.6 构建和部署

1. 使用 Maven 构建插件：`mvn clean install`
2. 将构建好的 JAR 文件复制到 MES 系统的插件目录
3. 重启 MES 系统

## 3. 最佳实践

### 3.1 命名规范

- 插件名称：使用小写字母和连字符，如 `mes-plugins-supply-chain`
- 包路径：使用反向域名，如 `com.qcadoo.mes.supplyChain`
- 类名：使用驼峰命名法，如 `SupplierService`
- 方法名：使用驼峰命名法，如 `createSupplier`

### 3.2 代码组织

- 按功能模块组织代码
- 使用服务层封装业务逻辑
- 遵循依赖注入原则
- 实现接口分离

### 3.3 数据模型设计

- 使用合理的字段类型和长度
- 定义适当的约束和验证规则
- 建立正确的关联关系
- 考虑数据安全性和性能

### 3.4 视图设计

- 保持界面简洁易用
- 遵循系统的设计风格
- 提供必要的搜索和过滤功能
- 确保表单验证和错误提示

### 3.5 性能优化

- 使用缓存减少数据库查询
- 优化 SQL 查询
- 避免不必要的计算和操作
- 合理使用事务

## 4. 常见问题

### 4.1 插件加载失败

- 检查插件依赖是否正确
- 检查插件配置文件是否有效
- 检查代码是否存在编译错误

### 4.2 数据模型冲突

- 确保实体名称唯一
- 避免字段名称冲突
- 使用适当的命名空间

### 4.3 权限问题

- 正确配置插件的权限角色
- 确保用户拥有必要的权限
- 实现适当的权限检查

### 4.4 性能问题

- 检查数据库索引
- 优化查询语句
- 减少网络请求
- 使用异步处理

## 5. 开发工具推荐

- **IDE**：IntelliJ IDEA 或 Eclipse
- **版本控制**：Git
- **构建工具**：Maven
- **数据库工具**：MySQL Workbench 或 pgAdmin
- **API 测试**：Postman

## 6. 示例插件

系统已包含多个示例插件，如：

- `mes-plugins-basic`：基础功能插件
- `mes-plugins-orders`：订单管理插件
- `mes-plugins-supply-chain`：供应链管理插件
- `mes-plugins-data-analysis`：数据分析插件
- `mes-plugins-system-integration`：系统集成插件
- `mes-plugins-ai-ml`：人工智能和机器学习插件
- `mes-plugins-mobile-app`：移动应用插件
- `mes-plugins-energy-management`：能源管理插件
- `mes-plugins-predictive-maintenance`：预测性维护插件

开发者可以参考这些插件的实现方式进行开发。

## 7. 联系方式

如有任何问题或建议，请联系：

- 邮箱：support@qcadoo.com
- 网站：http://www.qcadoo.com
- GitHub：https://github.com/qcadoo/mes
