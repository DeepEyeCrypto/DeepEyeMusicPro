package com.deepeye.musicpro.dsp.v4a

import android.content.Context
import java.io.File

object V4AAssetLoader {
    private val assetDirectories = listOf("v4a/irs", "v4a/ddc", "v4a/fet", "v4a/presets")

    fun copyAssetsToCache(context: Context) {
        val appContext = context.applicationContext
        assetDirectories.forEach { dir ->
            appContext.assets.list(dir)?.forEach { file ->
                val out = File(appContext.cacheDir, "$dir/$file")
                out.parentFile?.mkdirs()
                appContext.assets.open("$dir/$file").use { input ->
                    out.outputStream().use { output -> input.copyTo(output) }
                }
            }
        }
    }

    fun cachedFile(context: Context, category: String, fileName: String): File {
        return File(context.applicationContext.cacheDir, "v4a/$category/$fileName")
    }

    fun listCachedFiles(context: Context, category: String, extension: String): List<File> {
        val dir = File(context.applicationContext.cacheDir, "v4a/$category")
        return dir.listFiles { file -> file.isFile && file.extension.equals(extension, ignoreCase = true) }
            ?.sortedBy { it.name.lowercase() }
            .orEmpty()
    }
}
