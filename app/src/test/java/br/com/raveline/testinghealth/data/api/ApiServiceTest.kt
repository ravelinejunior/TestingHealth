package br.com.raveline.testinghealth.data.api

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {
    private lateinit var service: BreedsService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BreedsService::class.java)
    }

    private fun enqueueMockResponse(fileName: String) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val source = inputStream?.source()?.buffer()
        val mockResponse = MockResponse()

        mockResponse.setBody(source!!.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getTopHeadlines_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("newsResponse.json")
            val responseBody = service.getDogsBreed(0).body()
            val request = server.takeRequest()

            print(responseBody)

            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/breeds?page=0&api_key=46f710bb-bf9e-4f1f-9f19-35af4206b43b")
        }
    }

    @Test
    fun getTopHeadlines_sentRequest_receivedExpected_bySearch() {
        runBlocking {
            enqueueMockResponse("search.json")
            val responseBody = service.getDogsBreedBySearch("dog").body()
            val request = server.takeRequest()

            print(responseBody)

            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/breeds/search?q=dog&api_key=46f710bb-bf9e-4f1f-9f19-35af4206b43b")
        }
    }



    @After
    fun tearDown() {
        server.shutdown()
    }
}