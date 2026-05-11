package com.example

import com.lagradost.cloudstream3.*
import org.jsoup.Jsoup

class FilmpalastProvider : MainAPI() {
    override var mainUrl = "https://filmpalast.to"
    override var name = "Filmpalast"
    override val supportedTypes = setOf(TvType.Movie)

    override suspend fun search(query: String): List<SearchResponse> {
        val document = app.get("$mainUrl/search/story/$query").document
        return document.select("article.item").mapNotNull {
            val title = it.selectFirst("h2")?.text() ?: return@mapNotNull null
            val href = it.selectFirst("a")?.attr("href") ?: return@mapNotNull null
            newMovieSearchResponse(title, fixUrl(href), TvType.Movie)
        }
    }
}
