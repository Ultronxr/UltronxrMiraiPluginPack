group = "cn.ultronxr.umpp"
version = "0.1.0"
description = "Java 开发的应用于 Mirai Console 的QQ聊天机器人插件包。"

plugins {
    val kotlinVersion = "1.6.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("org.gradle.idea")
    id("net.mamoe.mirai-console") version "2.14.0"
    //id("net.mamoe.mirai-core") version "2.12.1"
}

idea {
    module {
        inheritOutputDirs = true
    }
}

repositories {
    //mavenLocal()
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

dependencies {
    //implementation("org.springframework.boot:spring-boot-starter:2.5.5") {
    //    exclude ("org.springframework.boot", "spring-boot-starter-logging")
    //}
    //implementation("org.springframework.boot:spring-boot-starter-aop:2.5.5")

    // 日志
    implementation("org.slf4j:slf4j-log4j12:1.7.36")
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
    implementation("org.apache.logging.log4j:log4j-core:2.17.2")

    // 数据序列化
    implementation("com.fasterxml.jackson.core:jackson-core:2.13.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.3")
    implementation("org.apache.commons:commons-lang3:3.12.0")

    // 开发工具/插件
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    implementation("cn.hutool:hutool-all:5.8.3")

    // 二维码
    implementation("com.google.zxing:core:3.5.0")
    implementation("com.google.zxing:javase:3.5.0")



    // 测试环境依赖
    testCompileOnly("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")

}

java{
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

mirai {
    jvmTarget = JavaVersion.VERSION_11
}
