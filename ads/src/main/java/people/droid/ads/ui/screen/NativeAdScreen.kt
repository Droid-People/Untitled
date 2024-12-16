package people.droid.ads.ui.screen

import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import people.droid.ads.AdsId.ADS_ID
import people.droid.ads.AdsId.NATIVE_TEST_ID
import people.droid.ads.BuildConfig
import people.droid.ads.R
import people.droid.ads.databinding.NativeAdLayoutBinding
import people.droid.ads.ui.component.BackButton
import people.droid.ads.ui.component.LoadingIndicator
import people.droid.ads.viewmodel.NativeAdViewModel
import people.droid.common.theme.backgroundYellow

@Composable
fun NativeAdScreen(nativeAdViewModel: NativeAdViewModel, navigateBack: () -> Unit) {
    val adsCount = 5
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    val binding = NativeAdLayoutBinding.inflate(LayoutInflater.from(context))
    val adLoader = AdLoader.Builder(context, getNativeAdId())
        .forNativeAd { nativeAd: NativeAd ->
            nativeAdViewModel.addNativeAd(nativeAd)
            setNativeAdView(nativeAd, binding)
        }
        .withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Toast.makeText(context, R.string.fail_to_load, Toast.LENGTH_SHORT).show()
            }

            override fun onAdLoaded() {
                isLoading = false
            }
        })
        .withNativeAdOptions(NativeAdOptions.Builder().build())
        .build()

    if (isLoading) {
        LoadingIndicator()
    }
    LaunchedEffect(Unit) {
        adLoader.loadAds(AdRequest.Builder().build(), adsCount)
    }
    DisposableEffect(Unit) {
        onDispose {
            nativeAdViewModel.destroyAds()
        }
    }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .background(backgroundYellow)
                .padding(innerPadding)
        ) {
            BackButton(modifier = Modifier.align(Alignment.Start), navigateBack = navigateBack)
            NativeAdList(nativeAds = nativeAdViewModel.nativeAds)
        }
    }
}

@Composable
fun NativeAdList(nativeAds: List<NativeAd>) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        itemsIndexed(nativeAds) { idx, nativeAd ->
            NativeAdItem(nativeAd)
            if (idx < nativeAds.size - 1) {
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun NativeAdItem(nativeAd: NativeAd) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            val binding = NativeAdLayoutBinding.inflate(LayoutInflater.from(context))
            setNativeAdView(nativeAd, binding)
            binding.root
        }
    )
}

private fun getNativeAdId() = if (BuildConfig.DEBUG) NATIVE_TEST_ID else ADS_ID

fun setNativeAdView(nativeAd: NativeAd, binding: NativeAdLayoutBinding) {
    with(binding) {
        nativeAd.icon?.drawable?.let { binding.adAppIcon.setImageDrawable(it) }
        adHeadline.text = nativeAd.headline
        adBody.text = nativeAd.body
        adAdvertiser.text = nativeAd.advertiser
        adCallToAction.visibility = View.VISIBLE
        adCallToAction.text = nativeAd.callToAction

        // admob와 연결
        binding.root.headlineView = adHeadline
        binding.root.iconView = adAppIcon
        binding.root.bodyView = adBody
        binding.root.advertiserView = adAdvertiser
        binding.root.callToActionView = adCallToAction
        binding.root.setNativeAd(nativeAd)
    }
}