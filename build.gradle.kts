plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

application {
    mainClass.set("org.gooseapple.Main")
}

javafx {
    version = "24.0.2"
    modules("javafx.controls", "javafx.fxml", "javafx.media")
}

group = "org.gooseapple"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.github.davidmoten:rtree2:0.9.3")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(22))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("--enable-preview")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    jvmArgs("--enable-preview")
}

tasks.withType<JavaExec>().configureEach {
    jvmArgs("--enable-preview")
}
