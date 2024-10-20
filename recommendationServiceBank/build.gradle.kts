plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "ru.skyPro"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	// https://mvnrepository.com/artifact/org.springframework/spring-webmvc
	implementation("org.springframework:spring-webmvc:6.1.12")
	// https://mvnrepository.com/artifact/io.swagger.core.v3/swagger-annotations
	implementation("io.swagger.core.v3:swagger-annotations:2.2.25")

	implementation("org.liquibase:liquibase-core:4.27.0")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	implementation("org.postgresql:postgresql:42.7.3")
	// https://mvnrepository.com/artifact/org.webjars/swagger-ui
	implementation("org.webjars:swagger-ui:5.17.14")

	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.mockito:mockito-core")
	testImplementation("org.mockito:mockito-junit-jupiter")
	testImplementation("org.assertj:assertj-core:3.26.0")
	implementation("log4j:log4j:1.2.17")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
