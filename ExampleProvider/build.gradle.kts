dependencies {
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
}

// Höhere Version, damit CloudStream merkt, dass es ein Update gab
version = 1

cloudstream {
    // Der Name des Plugins, wie er in der App angezeigt wird
    name = "Filmpalast"
    description = "Filmpalast Provider für CloudStream"
    authors = listOf("bikbakboo")

    /**
    * Status int: 1 = Ok
    **/
    status = 1 

    tvTypes = listOf("Movie", "TvSeries")

    requiresResources = true
    language = "de"

    // Optional: Ein Logo-Link
    iconUrl = "https://upload.wikimedia.org/wikipedia/commons/2/2f/Korduene_Logo.png"
}

android {
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}
