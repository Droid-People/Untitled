package people.droid.untitled.data

import kotlinx.coroutines.flow.Flow
import people.droid.untitled.domain.FeedbackRepository

class RemoteFeedbackRepository(private val dataSource: DataSource) : FeedbackRepository {
    override suspend fun postFeedback(feedback: String):Flow<ResponseState> {
        return dataSource.postFeedback(feedback)
    }
}