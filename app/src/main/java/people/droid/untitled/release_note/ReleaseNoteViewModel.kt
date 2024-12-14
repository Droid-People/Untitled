package people.droid.untitled.release_note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class ReleaseNoteViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val releaseNotesFlow = MutableStateFlow(ReleaseNoteState())
    val releaseNotes = releaseNotesFlow.asStateFlow()

    fun loadReleaseNotes() {
        // 문서 번호대로 내림차순 정렬하여 내용 가져오기
        db.collection("release_note")
            .orderBy("number", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val temp = mutableListOf<ReleaseNote>()
                for (document in result) {
                    Log.d(RELEASE_NOTES_ROUTE, "${document.id} => ${document.data}")
                    temp.add(
                        ReleaseNote(
                            title = document.data["title"] as String,
                            content = getCurrentLocaleDescription(document.data)
                        )
                    )
                }
                viewModelScope.launch { releaseNotesFlow.emit(ReleaseNoteState(releaseNotes = temp)) }
            }
            .addOnFailureListener { exception ->
                Log.w(RELEASE_NOTES_ROUTE, "Error getting documents.", exception)
                viewModelScope.launch {
                    releaseNotesFlow.emit(
                        ReleaseNoteState(
                            errorMessage = exception.message ?: "Unknown error"
                        )
                    )
                }
            }
    }

    // 현재 언어 설정에 따라 다른 데이터 불러오기
    private fun getCurrentLocaleDescription(data: Map<String, Any>): String {
        return (data[Locale.getDefault().language] ?: data["en"]) as String
    }
}