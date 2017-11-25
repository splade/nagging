# 多个 Profiles 切换

* spring.profiles.active=dev

* 指定　application-dev.yml 文档

* 在命令行中或者环境变量中指定　spring.profiles.active

* java -jar spring-boot-application.jar --spring.profiles.active=dev

* 使用　maven 来切换

> 定义 profiles, 使用 properties 中的 build.profile.id 属性

* application.yml 中指定的是通用的配置, 其中的 spring.profiles.active=xxx 是基本配置中的,可以覆写