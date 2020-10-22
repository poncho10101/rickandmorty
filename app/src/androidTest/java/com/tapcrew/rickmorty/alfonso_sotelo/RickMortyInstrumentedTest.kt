package com.tapcrew.rickmorty.alfonso_sotelo

import androidx.paging.LoadState
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.NetworkConstants
import com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.response.ErrorResponse
import com.tapcrew.rickmorty.alfonso_sotelo.utils.getMessageString
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.ResponseBody.Companion.toResponseBody

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
class RickMortyInstrumentedTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var retrofit: Retrofit

    @Before
    fun init() {
        hiltRule.inject()
    }


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.tapcrew.rickmorty.alfonso_sotelo", appContext.packageName)
    }

    @Test
    fun loadStateError_getMessageString() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val gson = Gson()

        // Checks that an IOException returns error_network string
        assertEquals(appContext.getString(R.string.error_network), LoadState.Error(IOException()).getMessageString(appContext))

        // Checks that a Server error with an error message returns the body error message
        assertEquals(
            "Error Message",
            LoadState.Error(
                HttpException(
                    Response.error<ErrorResponse>(404, gson.toJson(ErrorResponse("Error Message")).toResponseBody())
                )
            ).getMessageString(appContext)
        )

        // Checks that a Server error without an error message returns error_unknown string
        assertEquals(
            appContext.getString(R.string.error_unknown),
            LoadState.Error(
                HttpException(Response.error<Nothing>(404, "".toResponseBody()))
            ).getMessageString(appContext)
        )

        // Checks that another kind of exception returns error_unknown string
        assertEquals(
            appContext.getString(R.string.error_unknown),
            LoadState.Error(
                Exception("Another kind of exception")
            ).getMessageString(appContext)
        )
    }

    @Test
    fun checkApiBaseUrl() {
        assertEquals("https://rickandmortyapi.com", NetworkConstants.SERVER_BASE_URL)

        assertEquals(NetworkConstants.SERVER_BASE_URL + "/", retrofit.baseUrl().toString())
    }
}