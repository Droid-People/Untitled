package people.droid.untitled.release_note

data class ReleaseNote(
    val title: String,
    val content: String
)

data class ReleaseNoteState(
    val releaseNotes: MutableList<ReleaseNote> = mutableListOf(),
    val errorMessage: String = ""
)
