package com.example.pokeapp.infrastructure.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokeapp.entity.PokemonSummaryInfo
import com.example.pokeapp.infrastructure.PokeApi

private const val POKEAPI_STARTING_PAGE_INDEX = 0L

class PokePagingSource(
    private val api: PokeApi
) : PagingSource<Long, PokemonSummaryInfo>() {
    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, PokemonSummaryInfo> {
        val offset = params.key ?: POKEAPI_STARTING_PAGE_INDEX
        val response = api.getPokemonList(offset, params.loadSize.toLong())
        return if (response.isSuccessful) {
            val info = response.body()!!
            LoadResult.Page(
                data = info.summaryInfoList.map { it.toPokemonInfoSummary() },
                prevKey = if (offset == POKEAPI_STARTING_PAGE_INDEX) null else offset,
                nextKey = info.pagingInfo?.offset
            )
        } else LoadResult.Error(Exception())

    }

    override fun getRefreshKey(state: PagingState<Long, PokemonSummaryInfo>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1L)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1L)
        }
    }
}