package com.yur.retrofittest

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface UserApiInterface {

    @GET("api/users/{id}")
    fun getSingleUser(@Path("id") userId :Int): Call<UserModel>


    @GET("api/users")
    fun getListOfUsers(@Query("page") page : Int,
                        @Query("total") total: Int) : Call<UserListModel>

    @POST("api/users")
    fun createNewUser(@Body user: PostUserModel): Call<PostUserModel>
}

object UserRetrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://reqres.in/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UserApiInterface::class.java)

}