package people.droid.untitled.data

import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun postFeedback(feedback: String):Flow<ResponseState>
}