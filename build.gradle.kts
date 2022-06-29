plugins {
    val kotlinVersion = "1.5.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.11.1"
}

group = "cn.ultronxr"
version = "1.0-SNAPSHOT"

repositories {
    //mavenLocal()
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter:2.5.5") {
        exclude ("org.springframework.boot", "spring-boot-starter-logging")
    }
    //implementation("org.springframework.boot:spring-boot-starter-aop:2.5.5")
    implementation("org.projectlombok:lombok:1.18.20")
    implementation("cn.hutool:hutool-all:5.5.8")
}
