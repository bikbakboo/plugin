import com.android.build.gradle.BaseExtension
import com.lagradost.cloudstream3.gradle.CloudstreamExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        // Jitpack für Tools und Abhängigkeiten
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.7.3")
        // Cloudstream Plugin für den Build-Prozess
        classpath("com.github.recloudstream:gradle:-SNAPSHOT")
        // Erhöht auf 2.1.10, um die Inkompatibilität der Metadaten (2.3.0) zu lösen
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

fun Project.cloudstream(configuration: CloudstreamExtension.() -> Unit) = extensions.getByName<CloudstreamExtension>("cloudstream").configuration()

fun Project.android(configuration: BaseExtension.() -> Unit) = extensions.getByName<BaseExtension>("android").configuration()

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "com.lagradost.cloudstream3.gradle")

    cloudstream {
        // Setzt das Repository automatisch für den GitHub Workflow
        setRepo(System.getenv("GITHUB_REPOSITORY") ?: "user/repo")
    }

    android {
        namespace = "com.example"

        defaultConfig {
            minSdk = 21
            compileSdkVersion(35)
            targetSdk = 35
        }

        compileOptions {
            // Java 17 ist für neuere Kotlin-Versionen erforderlich
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        tasks.withType<KotlinJvmCompile> {
            compilerOptions {
                // JVM Target auf 17 für volle Kompatibilität
                jvmTarget.set(JvmTarget.JVM_17) 
                freeCompilerArgs.addAll(
                    "-Xno-call-assertions",
                    "-Xno-param-assertions",
                    "-Xno-receiver-assertions"
                )
            }
        }
    }

    dependencies {
        val cloudstream by configurations
        val implementation by configurations

        // Stubs für Cloudstream Klassen
        cloudstream("com.lagradost:cloudstream3:pre-release")

        implementation(kotlin("stdlib")) 
        implementation("com.github.Blatzar:NiceHttp:0.4.11") 
        implementation("org.jsoup:jsoup:1.18.3") 
        
        // WICHTIG: Jackson bei 2.13.1 belassen für Abwärtskompatibilität
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1") 
    }
}

task<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
