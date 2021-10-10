package dev.shaarawy.voyage

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * @author Mohamed Elshaarawy on Oct 10, 2021.
 */
suspend fun readTextFile(fileName: String): String =
    withContext(Dispatchers.IO) {
        File(javaClass.classLoader!!.getResource(fileName).file).readText()
    }

suspend inline fun <reified T> readJSONFile(fileName: String): T =
    withContext(Dispatchers.Default) {
        Json.decodeFromString(readTextFile(fileName))
    }