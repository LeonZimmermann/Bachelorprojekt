import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.7.4"
  id("io.spring.dependency-management") version "1.0.14.RELEASE"
  kotlin("jvm") version "1.6.21"
  kotlin("plugin.spring") version "1.6.21"
  kotlin("plugin.jpa") version "1.6.21"
}

group = "dev.leonzimmermann.demo.extendable"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
  mavenCentral()
  maven {
    setUrl("https://clojars.org/repo/")
  }
  maven {
    setUrl("https://jitpack.io")
  }
  google()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  runtimeOnly("org.postgresql:postgresql")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.springdoc:springdoc-openapi-ui:1.6.11")

  // SimpleNLG
  implementation("uk.ac.abdn:SimpleNLG:4.5.0")
  implementation("gov.nih.nlm.nls.lexaccess:lexaccess-dist:2013")

  // Apache Jena
  implementation("org.apache.jena:jena-core:4.6.1")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("junit:junit:4.13.2")
  testImplementation("org.assertj:assertj-core:3.23.1")
  testImplementation("org.mockito:mockito-core:4.8.0")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}