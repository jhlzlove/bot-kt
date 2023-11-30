plugins {
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
    application
}

group = "org.bot"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation("io.ktor:ktor-server-core-jvm:2.3.6")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.6")
    testImplementation(kotlin("test"))
    // ktor
    implementation("io.ktor:ktor-client-core:2.3.6")
    implementation("io.ktor:ktor-client-cio:2.3.6")
    implementation("io.ktor:ktor-client-websockets:2.3.6")
    // https://mvnrepository.com/artifact/com.akuleshov7/ktoml-core
    implementation("com.akuleshov7:ktoml-core:0.5.0")

    // json
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.1")
    // datetime
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
    // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("MainKt")
}