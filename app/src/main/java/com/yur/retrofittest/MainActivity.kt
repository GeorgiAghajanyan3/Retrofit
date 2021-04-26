package com.yur.retrofittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    lateinit var titleTxt: TextView
    lateinit var requestBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleTxt = findViewById(R.id.title)
        requestBtn = findViewById(R.id.request)

        //requests users Synchron
        requestUserWithExecute(3)


        //requests users async
//        requestUserWithEnqueue(3)

        //getListOfUsers(2, 12)


        requestBtn.setOnClickListener { createUser(PostUserModel("Jenifer", "actor")) }



    }



    fun requestUserWithExecute(id: Int) {

        GlobalScope.launch(Dispatchers.IO) {

            var call = UserRetrofit.retrofit.getSingleUser(2)

            Log.d("retro_test", "Starting request: ${call.request().url()}")

            val response = call.execute()
            var userModel = response.body()


            withContext(Dispatchers.Main) {
                titleTxt.text = "${userModel?.data?.first_name}   ${userModel?.data?.last_name}"
            }
            Log.d("retro_test", "After request")

        }
    }


    fun requestUserWithEnqueue(id: Int) {

        GlobalScope.launch(Dispatchers.IO) {

            var call = UserRetrofit.retrofit.getSingleUser(2)

            Log.d("retro_test", "Starting request")

            call.enqueue(object : Callback<UserModel> {
                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Log.d("retro_test", "Request failed : ${t.stackTraceToString()}")
                }

                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {

                    Log.d("retro_test", " Response is success ")
                    val userModel = response.body()

                    GlobalScope.launch(Dispatchers.Main) {
                        titleTxt.text = "${userModel?.data?.first_name}   ${userModel?.data?.last_name}"
                    }
                }
            })

            Log.d("retro_test", "After request")

        }


    }



    fun getListOfUsers(page: Int, total: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val call = UserRetrofit.retrofit.getListOfUsers(page, total)
            Log.d("retro_test", "Starting request: ${call.request().url()}")

            val response = call.execute()
            val text = "${response.body()?.data?.get(0)?.first_name} \n ${response.body()?.data?.get(1)?.first_name} \n ${response.body()?.data?.get(2)?.first_name}"

            withContext(Dispatchers.Main) {
                titleTxt.text = text
            }
        }
    }

    fun createUser(user: PostUserModel) {
        GlobalScope.launch(Dispatchers.IO) {
            val call = UserRetrofit.retrofit.createNewUser(user)
            Log.d("retro_test", "Starting request: ${call.request().url()}")

            val response = call.execute()

            val createdAt = response.body().toString()
            withContext(Dispatchers.Main) {
                titleTxt.text = createdAt
            }
        }
    }
}