package com.deepeye.musicpro.benchmark

import android.graphics.Point
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.SdkSuppress
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Baseline Profile Generator for DeepEyeMusicPro.
 *
 * Walks through all critical user journeys to capture hot code paths
 * that ART should AOT-compile at install time. This eliminates JIT
 * compilation stalls during first use.
 *
 * Run with:
 *   ./gradlew :benchmark:connectedBenchmarkAndroidTest \
 *       -Pandroid.testInstrumentationRunnerArguments.class=com.deepeye.musicpro.benchmark.BaselineProfileGenerator
 *
 * Output: app/src/main/baseline-prof.txt (merge with manual entries)
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
@SdkSuppress(minSdkVersion = 28) // Baseline profiles require API 28+
class BaselineProfileGenerator {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() {
        baselineProfileRule.collect(
            packageName = TARGET_PACKAGE,
            stableIterations = 3,
            maxIterations = 8,
            includeInStartupProfile = true,
        ) {
            // ═══════════════════════════════════════════════════════
            // Journey 1: Cold Start → Home Screen
            // Captures: Application.onCreate, Activity init, 
            //           Fragment inflation, RecyclerView layout,
            //           Coil image loading, Navigation setup
            // ═══════════════════════════════════════════════════════
            startActivityAndWait()
            device.wait(Until.hasObject(By.res(TARGET_PACKAGE, "bottom_nav")), 5_000)
            device.waitForIdle()

            // ═══════════════════════════════════════════════════════
            // Journey 2: Search Flow
            // Captures: SearchFragment, NewPipe init, OkHttp,
            //           JSON parsing, RecyclerView item binding
            // ═══════════════════════════════════════════════════════
            device.findObject(By.res(TARGET_PACKAGE, "nav_search"))?.click()
            device.wait(Until.hasObject(By.res(TARGET_PACKAGE, "search_edit_text")), 3_000)
            device.waitForIdle()

            // Type a search query to trigger extraction pipeline
            device.findObject(By.res(TARGET_PACKAGE, "search_edit_text"))?.let { searchBox ->
                searchBox.click()
                searchBox.text = "lofi beats"
            }
            device.waitForIdle()
            Thread.sleep(2_000) // Let search results load

            // ═══════════════════════════════════════════════════════
            // Journey 3: Start Playback → Now Playing
            // Captures: Media3 ExoPlayer init, AudioProcessor,
            //           Native DSP JNI bridge, MediaSession,
            //           NowPlayingFragment, Compose visualizer
            // ═══════════════════════════════════════════════════════
            val firstResult = device.findObject(By.res(TARGET_PACKAGE, "track_item"))
            firstResult?.click()
            device.wait(Until.hasObject(By.res(TARGET_PACKAGE, "playPauseButtonM3")), 5_000)
            device.waitForIdle()
            Thread.sleep(3_000) // Let audio pipeline stabilize

            // ═══════════════════════════════════════════════════════
            // Journey 4: DSP / Equalizer Panel
            // Captures: V4A bottom sheet, DSP preset loading,
            //           Slider interactions, NativeDSP JNI calls
            // ═══════════════════════════════════════════════════════
            device.findObject(By.res(TARGET_PACKAGE, "v4aButtonM3"))?.click()
            device.wait(Until.hasObject(By.res(TARGET_PACKAGE, "eq_band_0")), 3_000)
            device.waitForIdle()

            // Interact with EQ sliders to trigger native DSP path
            device.findObject(By.res(TARGET_PACKAGE, "eq_band_0"))?.let { slider ->
                val bounds = slider.visibleBounds
                slider.drag(Point(bounds.centerX(), bounds.top + 30))
            }
            device.waitForIdle()

            // Close bottom sheet
            device.pressBack()
            device.waitForIdle()

            // ═══════════════════════════════════════════════════════
            // Journey 5: Queue Management
            // Captures: QueueBottomSheet, RecyclerView with
            //           drag-to-reorder, LazyStreamResolver
            // ═══════════════════════════════════════════════════════
            device.findObject(By.res(TARGET_PACKAGE, "queueButtonM3"))?.click()
            device.wait(Until.hasObject(By.res(TARGET_PACKAGE, "queue_list")), 3_000)
            device.waitForIdle()
            device.pressBack()
            device.waitForIdle()

            // ═══════════════════════════════════════════════════════
            // Journey 6: Background → Foreground
            // Captures: Service reconnection, MediaSession resume,
            //           Activity re-creation, state restoration
            // ═══════════════════════════════════════════════════════
            pressHome()
            device.waitForIdle()
            Thread.sleep(1_500)

            startActivityAndWait()
            device.wait(Until.hasObject(By.res(TARGET_PACKAGE, "bottom_nav")), 5_000)
            device.waitForIdle()

            // ═══════════════════════════════════════════════════════
            // Journey 7: Library / Downloads Tab
            // Captures: Room database queries, LibraryFragment,
            //           DataStore reads
            // ═══════════════════════════════════════════════════════
            device.findObject(By.res(TARGET_PACKAGE, "nav_library"))?.click()
            device.waitForIdle()
            Thread.sleep(1_000)

            // Return to home
            device.findObject(By.res(TARGET_PACKAGE, "nav_home"))?.click()
            device.waitForIdle()
        }
    }

    companion object {
        private const val TARGET_PACKAGE = "com.deepeye.musicpro"
    }
}
