package people.droid.untitled.domain

import kotlinx.coroutines.flow.Flow
import people.droid.untitled.data.ResponseState

interface FeedbackRepository {
    suspend fun postFeedback(feedback: String): Flow<ResponseState>
}