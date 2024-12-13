package people.droid.untitled.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import people.droid.untitled.data.ResponseState
import people.droid.untitled.domain.FeedbackRepository

data class FeedbackUiState(
    val isSuccess: Boolean? = null,
    val result: String = ""
)

class FeedbackViewModel(private val repository: FeedbackRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<FeedbackUiState> = MutableStateFlow(FeedbackUiState())
    val uiState: StateFlow<FeedbackUiState> = _uiState.asStateFlow()

    fun postFeedback(feedback: String) {
        viewModelScope.launch {
            repository.postFeedback(feedback).collect { result ->
                when (result) {
                    ResponseState.Failed -> _uiState.update { it.copy(isSuccess = false) }

                    ResponseState.Loading -> _uiState.update { it.copy(isSuccess = null) }

                    ResponseState.Success -> _uiState.update { it.copy(isSuccess = true) }
                }
            }
        }
    }

    fun resetUiState(){
        _uiState.update { FeedbackUiState() }
    }

    companion object {
        val FEEDBACK_REPOSITORY_KEY = object : CreationExtras.Key<FeedbackRepository> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val feedbackRepository = this[FEEDBACK_REPOSITORY_KEY] as FeedbackRepository
                FeedbackViewModel(feedbackRepository)
            }
        }
    }
}