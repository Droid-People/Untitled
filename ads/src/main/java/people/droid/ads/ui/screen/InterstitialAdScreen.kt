package people.droid.ads.ui.screen

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import people.droid.ads.AdsId.ADS_ID
import people.droid.ads.AdsId.INTERSTITIAL_TEST_ID
import people.droid.ads.BuildConfig
import people.droid.ads.R
import people.droid.ads.ui.theme.backgroundYellow

@Composable
fun InterstitialAdScreen(navigateBack: () -> Unit) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        showInterstitialAd(context, navigateBack)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundYellow)
    )
}

fun showInterstitialAd(context: Context, navigateBack: () -> Unit) {
    InterstitialAd.load(
        context,
        getInterstitialAdId(),
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Toast.makeText(context, R.string.fail_to_load, Toast.LENGTH_SHORT).show()
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
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

private fun getInterstitialAdId() = if (BuildConfig.DEBUG) INTERSTITIAL_TEST_ID else ADS_ID

@Preview(showBackground = true)
@Composable
fun InterstitialAdScreenPreview() {
    InterstitialAdScreen(navigateBack = {})
}