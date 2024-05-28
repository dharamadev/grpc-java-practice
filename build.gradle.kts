plugins {
    id("java")
    id("idea")
    id("jacoco")
    id("com.google.protobuf") version "0.9.4"
}

group = "com.techendear.grpc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.27.0"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.64.0"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
            }
        }
    }
}
dependencies {
    implementation("io.grpc:grpc-netty-shaded:1.64.0")
    implementation("io.grpc:grpc-protobuf:1.64.0")
    implementation("io.grpc:grpc-stub:1.64.0")
    runtimeOnly("io.grpc:grpc-services:1.64.0")
    implementation("org.apache.tomcat:tomcat-annotations-api:11.0.0-M20")
    implementation("com.google.protobuf:protobuf-java-util:4.27.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

idea {
    module {
        generatedSourceDirs.addAll(listOf(
            file("${protobuf.generatedFilesBaseDir}/main/grpc"),
            file("${protobuf.generatedFilesBaseDir}/main/java")
        ))
    }
}

tasks.test {
    useJUnitPlatform()
}