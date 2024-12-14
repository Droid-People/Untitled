package people.droid.ads.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.nativead.NativeAd

class NativeAdViewModel : ViewModel() {
    private val _nativeAds = mutableStateListOf<NativeAd>()
    val nativeAds: List<NativeAd> get() = _nativeAds

    fun addNativeAd(nativeAd: NativeAd) {
        _nativeAds.add(nativeAd)
    }

    fun destroyAds() {
        _nativeAds.forEach { it.destroy() }
        _nativeAds.clear()
    }
}