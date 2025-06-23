plugins {
    id("java")
    id("edu.sc.seis.launch4j") version "3.0.5"
}

group = "org.infoLK"
version = "1.0-SNAPSHOT"
val appMain = "org.infoLK.Main"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
}

launch4j {
    mainClassName = appMain
    outfile = "ChessApp.exe"
}