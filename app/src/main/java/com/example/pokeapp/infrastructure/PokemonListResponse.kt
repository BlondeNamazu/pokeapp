package com.example.pokeapp.infrastructure

import android.net.Uri
import com.example.pokeapp.entity.PagingInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    @SerialName("next") val next: String?,
    @SerialName("results") val summaryInfoList: List<PokemonSummaryInfoResponse>
) {
    val pagingInfo: PagingInfo?
        get() = next?.let {
                val uri = Uri.parse(it)
                return PagingInfo(
                    offset = uri.getQueryParameter("offset")!!.toLong(),
                    limit = uri.getQueryParameter("limit")!!.toLong()
                )
            }
}