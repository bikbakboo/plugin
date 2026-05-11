package com.example

import com.lagradost.cloudstream3.MainAPI
import com.lagradost.cloudstream3.TvType
import com.lagradost.cloudstream3.SearchResponse

class FilmpalastProvider : MainAPI() {
    override var mainUrl = "https://filmpalast.to"
    override var name = "Filmpalast"
    override val supportedTypes = setOf(TvType.Movie)

    override suspend fun search(query: String): List<SearchResponse> {
        return emptyList()
    }
}
