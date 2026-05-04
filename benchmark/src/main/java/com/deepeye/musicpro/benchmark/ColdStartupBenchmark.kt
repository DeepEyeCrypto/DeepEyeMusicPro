package com.deepeye.musicpro.benchmark

import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.SdkSuppress
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Cold startup benchmark for DeepEyeMusicPro.
 *
 * Run on a physical device with:
 *   ./gradlew :benchmark:connectedBenchmarkAndroidTest \
 *       -Pandroid.testInstrumentationRunnerArguments.class=com.deepeye.musicpro.benchmark.ColdStartupBenchmark
 *
 * Target: < 400ms on Pixel 7-class device (currently ~900ms unoptimized).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
@SdkSuppress(minSdkVersion = 24)
class ColdStartupBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun coldStartup() {
        benchmarkRule.measureRepeated(
            packageName = TARGET_PACKAGE,
            metrics = listOf(StartupTimingMetric()),
            iterations = 5,
            startupMode = StartupMode.COLD,
            setupBlock = {
                pressHome()
                killProcess()
            }
        ) {
            startActivityAndWait()
            // Wait for the main UI to render
            device.wait(Until.hasObject(By.res(TARGET_PACKAGE, "bottom_nav")), 5_000)
        }
    }

    @Test
    fun warmStartup() {
        benchmarkRule.measureRepeated(
            packageName = TARGET_PACKAGE,
            metrics = listOf(StartupTimingMetric()),
            iterations = 5,
            startupMode = StartupMode.WARM,
            setupBlock = {
                pressHome()
            }
        ) {
            startActivityAndWait()
            device.wait(Until.hasObject(By.res(TARGET_PACKAGE, "bottom_nav")), 5_000)
        }
    }

    @Test
    fun hotStartup() {
        benchmarkRule.measureRepeated(
            packageName = TARGET_PACKAGE,
            metrics = listOf(StartupTimingMetric()),
            iterations = 5,
            startupMode = StartupMode.HOT,
            setupBlock = {
                pressHome()
            }
        ) {
            startActivityAndWait()
        }
    }

    companion object {
        private const val TARGET_PACKAGE = "com.deepeye.musicpro"
    }
}
