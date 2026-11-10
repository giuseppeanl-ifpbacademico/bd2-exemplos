plugins {
    java
    application
}

group = "com.ads.bd2"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.thoughtworks.xstream:xstream:1.4.20")
    implementation("org.codehaus.jettison:jettison:1.5.4")
    implementation("org.mongodb:mongodb-driver-sync:5.2.1")
}

application {
    mainClass = "com.ads.bd2.agenda.app.Main"
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}