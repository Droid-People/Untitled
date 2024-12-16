package people.droid.ads.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import people.droid.ads.AdsId.ADS_ID
import people.droid.ads.AdsId.BANNER_TEST_ID
import people.droid.ads.BuildConfig
import people.droid.ads.ui.component.BackButton
import people.droid.ads.ui.component.LoadingIndicator
import people.droid.common.theme.backgroundYellow

@Composable
fun BannerAdScreen(navigateBack: () -> Unit) {
    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics
    val adWidthPixels = displayMetrics.widthPixels
    val density = displayMetrics.density
    val adWidth = (adWidthPixels / density).toInt()
    val adSize: AdSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
    var isLoading by remember { mutableStateOf(true) }

    if (isLoading) {
        LoadingIndicator()
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundYellow)
                .padding(innerPadding)
        ) {
            BackButton(modifier = Modifier.align(Alignment.TopStart), navigateBack = navigateBack)
            AndroidView(
                modifier = Modifier.align(Alignment.Center),
                factory = { context ->
                    AdView(context).apply {
                        adUnitId = getBannerAdId()
                        setAdSize(adSize)
                        loadAd(AdRequest.Builder().build())
                        adListener = object : AdListener() {
                            override fun onAdLoaded() {
                                isLoading = false
                            }
                        }
                    }
                },
                update = { adView ->
                    adView.loadAd(AdRequest.Builder().build())
                }
            )
        }
    }
}

private fun getBannerAdId() = if (BuildConfig.DEBUG) BANNER_TEST_ID else ADS_ID

@Preview(showBackground = true)
@Composable
fun BannerAdScreenPreview() {
    BannerAdScreen(navigateBack = {})
}