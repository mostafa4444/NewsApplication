package com.task.news.ui.usecases

import com.task.news.model.response.news.HeadlineResponse
import com.task.news.network.DataRepository
import com.task.news.network.coroutines.BaseUseCase
import javax.inject.Inject

class HeadlineUseCase @Inject constructor(dataRepository: DataRepository): BaseUseCase<HeadlineResponse>(dataRepository) {

    override suspend fun executeOnBackground(map: MutableMap<String, Any>, headerMap: MutableMap<String, String>): HeadlineResponse {
        return dataRepository.fetchHeadlineNews(headerMap, map)
    }

}