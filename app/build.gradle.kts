import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Properties
import java.util.TimeZone


val myDirName = SimpleDateFormat("yyyyMMdd")
myDirName.timeZone = TimeZone.getDefault()
val copyDir = File("../../../Release/", myDirName.format(Date()))


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.services)
}

android {
    namespace = "people.droid.untitled"
    compileSdk = 35

    defaultConfig {
        applicationId = "people.droid.untitled"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            val keystorePropertiesFile = rootProject.file("app/keystore/keystore.properties")
            val keystoreProperties = Properties().apply {
                load(keystorePropertiesFile.reader())
            }
            storeFile = file(keystoreProperties["storePath"].toString())
            storePassword = keystoreProperties["storePassword"].toString()
            keyAlias = keystoreProperties["keyAlias"].toString()
            keyPassword = keystoreProperties["keyPassword"].toString()
        }
    }

    applicationVariants.configureEach {
        outputs.all { variant ->
            if (variant.outputFile.name.contains("release")) {
                val taskSuffix =
                    name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                val assembleTaskName = "bundle$taskSuffix"
                val task = tasks.findByName(assembleTaskName)
                if (task != null) {
                    val copyTask = tasks.create("archive${taskSuffix}Copy", Copy::class) {
                        description = "앱번들 복사하기"
                        println(description)
                        from("build/outputs/bundle/release/app-release.aab")
                        into(copyDir.absolutePath)
                        include("*.aab")
                        includeEmptyDirs = false
                        rename { fileName ->
                            fileName.replace(
                                "app-release.aab",
                                "Untitled_${versionName}.aab"
                            )
                        }
                    }
                    tasks.getByName(assembleTaskName).finalizedBy(copyTask)
                }
            }
            return@all true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation(project(":pixelart"))
    implementation(project(":puzzle"))
    implementation(project(":roulette"))

    implementation(libs.androidx.navigation.compose)
    implementation(libs.play.services.ads)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)

    implementation(platform(libs.firebase.bom))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}