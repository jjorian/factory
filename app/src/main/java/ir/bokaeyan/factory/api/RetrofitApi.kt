package ir.bokaeyan.factory.api


import ir.bokaeyan.factory.supporthelper.AppPreferences
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

/**
 * API communication setup
 */
interface RetrofitApi {
    //User

    @GET("api/admin/Login/{username}/{password}")
    fun Login(@Path("username") username: String,@Path("password") password: String):Call<PAResponse>

    @GET("api/Mobile/AddData/{uid}/{did}")
    fun GetDeviceUrl( @Path("uid") uid: Int,@Path("did") did: Int): Call<PAResponse>
/*
    //Shop
    @GET("api/v1/Shop/Get/{UID}/{type}")
     fun GetUserShops( @Path("UID") UID: Int,@Path("type") type: Int): Call<List<ShopListView>>
    @GET("api/v1/Shop/Get/find/{query}")
     fun SearchShop( @Path("query") query: String): Call<List<ShopListView>>
    @GET("api/v1/Shop/Get/{ShopID}")
     fun GetShopInfo( @Path("ShopID") ShopID: Int):Call<ShopView>
    @POST("api/v1/Shop/Member")
     fun memberToSHop(@Body member:ShopMemberView):Call<Short>
*/
    companion object {
        //private const val BASE_URL = "http://shops.parsianandroid.ir/"
        private val BASE_URL = AppPreferences.BaseUrl
        const val BASE_ParsianAndroid_URL = "http://app.parsianandroid.ir/"
        fun create(): RetrofitApi = create(BASE_URL?.toHttpUrlOrNull()!!)
        fun create(url:String): RetrofitApi = create(url.toHttpUrlOrNull()!!)
        fun create(httpUrl: HttpUrl): RetrofitApi {
            //val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            //  Log.d("Customer", it)
            //})
            val client = OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                                .addHeader("Content-Type", "application/json;charset=utf-8")
                                .addHeader("UID", AppPreferences.ID.toString())
                                .addHeader("DeviceId", "123456")
                                .addHeader("AppID", "ir.parsianandroid.customers")
                                .addHeader("Version", "123456")
                                .addHeader("Client", "Android")
                                .build()
                        val response = chain.proceed(request)
                        response.newBuilder().addHeader("Content-Type", "application/json;charset=utf-8").addHeader("Content-encoding","gzip").build()
                    }
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .build()
            return Retrofit.Builder()
                    .baseUrl(httpUrl)
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RetrofitApi::class.java)
        }
    }
}