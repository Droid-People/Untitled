package people.droid.ads.ui.screen

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import people.droid.ads.AdsId.INTERSTITIAL_AD_ID
import people.droid.ads.AdsId.INTERSTITIAL_TEST_ID
import people.droid.ads.BuildConfig
import people.droid.ads.R
import people.droid.ads.ui.component.LoadingIndicator
import people.droid.common.theme.YellowBackground

@Composable
fun InterstitialAdScreen(navigateBack: () -> Unit) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        showInterstitialAd(context, navigateBack) {
            isLoading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(YellowBackground)
    )
    if (isLoading) {
        LoadingIndicator()
    }
}

fun showInterstitialAd(context: Context, navigateBack: () -> Unit, updateIsLoading: () -> Unit) {
    InterstitialAd.load(
        context,
        getInterstitialAdId(),
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Toast.makeText(context, R.string.fail_to_load, Toast.LENGTH_SHORT).show()
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                updateIsLoading()
                interstitialAd.show(context as Activity)
                interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        navigateBack()
                    }
                }
            }
        }
    )
}

private fun getInterstitialAdId() = if (BuildConfig.DEBUG) INTERSTITIAL_TEST_ID else INTERSTITIAL_AD_ID

@Preview(showBackground = true)
@Composable
fun InterstitialAdScreenPreview() {
    InterstitialAdScreen(navigateBack = {})
}