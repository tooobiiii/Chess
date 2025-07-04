dependencies {
    implementation(project(":Core"))
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")
    implementation("com.h2database:h2:2.3.232")
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "me.tooobiiii.ChessServer"
        }
    }
}