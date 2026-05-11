dependencies {
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
}

version = 1

cloudstream {
    // Hier lag der Fehler: 'name' muss ohne Gleichheitszeichen oder als 'pluginName' geschrieben werden
    pluginName = "Filmpalast" 
    description = "Filmpalast Provider für CloudStream"
    authors = listOf("bikbakboo")

    status = 1 

    tvTypes = listOf("Movie", "TvSeries")

    requiresResources = true
    language = "de"

    iconUrl = "https://upload.wikimedia.org/wikipedia/commons/2/2f/Korduene_Logo.png"
}

android {
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}
