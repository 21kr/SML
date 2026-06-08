package com.mrp.sml.core.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.sample

fun <T> Flow<T?>.filterNotNullValues(): Flow<T> = filterNotNull()

fun <T> Flow<T>.throttleFirst(periodMs: Long): Flow<T> = sample(periodMs)

fun <T> Flow<T>.debounced(periodMs: Long): Flow<T> = debounce(periodMs)

fun <T> Flow<T>.distinct(): Flow<T> = distinctUntilChanged()

inline fun <reified T> Flow<*>.ofType(): Flow<T> = filterIsInstance<T>()

inline fun <reified T, reified R> Flow<T>.mapTo(): Flow<R> = map { it as R }
