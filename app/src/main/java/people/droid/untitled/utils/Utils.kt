package people.droid.untitled.utils

// 현재 Preview 모드인지 확인하는 함수
fun isInEditMode(): Boolean {
    return Thread.currentThread().stackTrace.any {
        it.className.contains("ComposeViewAdapter") || it.methodName == "buildPreview"
    }
}