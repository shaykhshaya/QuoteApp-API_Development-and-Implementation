package com.shaya.quotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import com.shaya.quotes.api.ApiClient
import com.shaya.quotes.api.ApiClient.Companion.retrofit
import com.shaya.quotes.api.ApiInterface
import com.shaya.quotes.response.DeleteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class AddQuote : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var quoteName: EditText
    lateinit var quoteData: EditText
    lateinit var apiInterface: ApiInterface
    lateinit var submitButton: AppCompatButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_quote)
        initialization()
        setuptoolbar()

        submitButton.setOnClickListener {

           val quoteDataString: String = quoteData.text.toString()
            val quoteNameString: String = quoteName.text.toString()



            if(quoteDataString.equals("")){
                quoteData.setError("Field is Empty")
            }
            else{
                if(quoteNameString.equals("")){
                    quoteName.setError("Field is Empty")
                }
                else{
                    //Toast.makeText(this, "Call Api", Toast.LENGTH_SHORT ).show()
                    callapi(quoteDataString, quoteNameString)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                }

            }




        }


    }



    private fun initialization(){
        toolbar = findViewById(R.id.add_quote_toolbar)
        quoteName = findViewById(R.id.add_quote_name_edittext)
        quoteData = findViewById(R.id.add_quote_data_edittext)
        submitButton = findViewById(R.id.submit_data_button)

        val retrofit = ApiClient.getclient()
        if (retrofit != null) {
            apiInterface = retrofit.create(ApiInterface::class.java)
        }
    }



    private fun setuptoolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
           finish()
        }

    }

    fun callapi(quote: String, quoteName: String){

        apiInterface.addquote(quote, quoteName).enqueue(object: Callback<DeleteResponse>{
            override fun onResponse(
                call: Call<DeleteResponse>,
                response: Response<DeleteResponse>
            ) {
                try{
                    if(response.body()?.status.equals("1")){

                    Toast.makeText(this@AddQuote, "Added Successfully", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@AddQuote, "Not Added Successfully", Toast.LENGTH_SHORT).show()

                    }



                }
                catch (e:Exception){
                    e.localizedMessage?.let { Log.e("exp", it) }
                }
            }

            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                t.localizedMessage?.let { Log.e("failure", it) }
            }
        })

    }



}