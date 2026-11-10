plugins {
    id("java")
    id("application")
}

group = "com.ads.bd2.riak"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.basho.riak:riak-client:2.1.1")
    implementation("org.slf4j:slf4j-simple:1.7.36")
}

application {
    mainClass = "com.ads.bd2.riak.Main"
    applicationDefaultJvmArgs = listOf(
        "--add-opens=java.base/java.nio=ALL-UNNAMED",
        "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED"
    )
}

