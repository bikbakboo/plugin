package com.example

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*

class FilmpalastProvider : MainAPI() {
    override var mainUrl = "https://filmpalast.to"
    override var name = "Filmpalast"
    override val supportedTypes = setOf(TvType.Movie, TvType.TvSeries)

    override suspend fun search(query: String): List<SearchResponse> {
        val url = "$mainUrl/search/story/$query"
        val response = app.get(url).text
        val document = org.jsoup.Jsoup.parse(response)

        return document.select("article.item").mapNotNull {
            val title = it.selectFirst("h2")?.text() ?: return@mapNotNull null
            val href = it.selectFirst("a")?.attr("href") ?: return@mapNotNull null
            val poster = it.selectFirst("img")?.attr("src")
            
            newMovieSearchResponse(title, fixUrl(href), TvType.Movie) {
                this.posterUrl = fixUrl(poster ?: "")
            }
        }
    }
}
