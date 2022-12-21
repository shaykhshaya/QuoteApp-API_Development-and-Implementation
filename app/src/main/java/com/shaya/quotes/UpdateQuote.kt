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
import retrofit2.*

class UpdateQuote : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var quoteData: EditText
    lateinit var updateButton: AppCompatButton
     var quoteId: Int = 0
    lateinit var quoteDataString: String
    lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_quote)

        initializaion()
        //val quoteId = intent.getIntExtra("q_id", 0)//primitive is not allowed for lateinit
        setupToolbar()

        updateButton.setOnClickListener {

            val updatedData:String = quoteData.text.toString()


            if(updatedData.equals("")){
                quoteData.setError("Field is Empty")

            }
            else{

                updateApi(updatedData, quoteId.toString() )
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                //Toast.makeText(this@UpdateQuote, "Call Api", Toast.LENGTH_LONG).show()

            }

        }


    }


    private fun initializaion(){
        toolbar = findViewById(R.id.update_quote_toolbar)
        quoteData = findViewById(R.id.update_quote_data_edittext)
        updateButton = findViewById(R.id.update_data_button)

        quoteId = intent.getIntExtra("q_id", 0)
        quoteDataString = intent.getStringExtra("q_data").toString()
        quoteData.setText(quoteDataString)

        val retrofit = ApiClient.getclient()
        if (retrofit != null) {
            apiInterface = retrofit.create(ApiInterface::class.java)
        }
    }


    private fun setupToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }


     private fun updateApi(qData: String, qid: String){

        apiInterface.updatequote(qData, qid).enqueue(object : Callback<DeleteResponse>{
            override fun onResponse(
                call: Call<DeleteResponse>,
                response: Response<DeleteResponse>
            ) {
                try{
                    if(response.body()?.status.equals("1")){
                        Toast.makeText(this@UpdateQuote, "Updated Successfully", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@UpdateQuote, "Not Updated Successfully", Toast.LENGTH_SHORT).show()

                    }

                }
                catch (e:Exception){
                    e.localizedMessage?.let { Log.e("exp", it) }
                }
            }

            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                t.localizedMessage?.let { Log.e("Update Fail", it) }
            }
        })
    }


    }


