java{
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

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
    //implementation("org.springframework.boot:spring-boot-starter:2.5.5") {
    //    exclude ("org.springframework.boot", "spring-boot-starter-logging")
    //}
    //implementation("org.springframework.boot:spring-boot-starter-aop:2.5.5")
    implementation("org.slf4j:slf4j-log4j12:1.7.35")
    implementation("org.apache.logging.log4j:log4j-api:2.17.1")
    implementation("org.apache.logging.log4j:log4j-core:2.17.1")

    implementation("org.projectlombok:lombok:1.18.24")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    testCompileOnly("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")

    implementation("cn.hutool:hutool-all:5.5.8")

    implementation("com.fasterxml.jackson.core:jackson-core:2.11.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.3")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.11.3")
}
