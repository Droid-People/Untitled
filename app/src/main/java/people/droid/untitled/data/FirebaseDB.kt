package people.droid.untitled.data

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseDB : DataSource {
    private val db = Firebase.firestore
    private val feedbackCollectionPath = "feedback"

    override suspend fun postFeedback(feedback: String): Flow<ResponseState> {
        return callbackFlow {
            trySend(ResponseState.Loading)
            db.collection(feedbackCollectionPath)
                .add(hashMapOf(
                    "feedback" to feedback,
                    "timestamp" to FieldValue.serverTimestamp()
                ))
                .addOnSuccessListener { documentReference ->
                    Log.d("Firebase DB", "DocumentSnapshot added with ID: ${documentReference.id}")
                    trySend(ResponseState.Success)
                }.addOnFailureListener { e ->
                    Log.w("Firebase DB", "Error adding document", e)
                    trySend(ResponseState.Failed)
                }
            awaitClose()
        }

    }
}