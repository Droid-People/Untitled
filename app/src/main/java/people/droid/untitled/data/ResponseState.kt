package people.droid.untitled.data

sealed class ResponseState {
    data object Success:ResponseState()
    data object Failed:ResponseState()
    data object Loading:ResponseState()
}