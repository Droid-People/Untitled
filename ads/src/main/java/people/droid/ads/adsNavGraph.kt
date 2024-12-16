package people.droid.ads

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import people.droid.ads.ui.screen.BannerAdScreen
import people.droid.ads.ui.screen.InterstitialAdScreen
import people.droid.ads.ui.screen.NativeAdScreen
import people.droid.ads.viewmodel.NativeAdViewModel

const val ADS_ROUTE = "ads"
const val BANNER_AD_ROUTE = "bannerAd"
const val INTERSTITIAL_ROUTE = "interstitialAd"
const val NATIVE_ROUTE = "nativeAd"

fun NavGraphBuilder.adsNavGraph(navigateBack: () -> Unit, nativeAdViewModel: NativeAdViewModel) {
    composable(route = BANNER_AD_ROUTE) {
        BannerAdScreen(navigateBack)
    }
    composable(route = INTERSTITIAL_ROUTE) {
        InterstitialAdScreen(navigateBack)
    }
    composable(route = NATIVE_ROUTE) {
        NativeAdScreen(nativeAdViewModel, navigateBack)
    }
}