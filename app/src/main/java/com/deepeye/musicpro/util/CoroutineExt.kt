package com.deepeye.musicpro.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.launchSafely(block: suspend CoroutineScope.() -> Unit) = launch { block() }
