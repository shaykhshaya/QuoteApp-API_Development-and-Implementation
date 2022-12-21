package com.shaya.quotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shaya.quotes.adapter.QuoteAdapter
import com.shaya.quotes.api.ApiClient
import com.shaya.quotes.api.ApiInterface
import com.shaya.quotes.model.QuoteModel
import com.shaya.quotes.response.GetQuoteResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var quoteAdapter: QuoteAdapter
    lateinit var apiInterface: ApiInterface
    lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialization()
        setSupportActionBar(toolbar)
        getdata()


    }

    override fun onResume() {
        getdata()
        super.onResume()
    }

    fun initialization() {
        recyclerView = findViewById(R.id.quote_recyclerView)
        val retrofit: Retrofit? = ApiClient.getclient()
        apiInterface = retrofit!!.create(ApiInterface::class.java)
        toolbar = findViewById(R.id.main_toolbar)
    }


    private fun setadapter(quoteModels: List<QuoteModel>) {
        quoteAdapter = QuoteAdapter(this, quoteModels as MutableList<QuoteModel>)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = quoteAdapter

    }

    private fun getdata() {
        apiInterface.getquote().enqueue(object : retrofit2.Callback<GetQuoteResponse> {
            override fun onResponse(
                call: Call<GetQuoteResponse>,
                response: Response<GetQuoteResponse>
            ) {
                try {
                    if (response != null) {
                        if (response.body()?.status.equals("1")) {
                            response.body()?.let {
                                setadapter(it.data)
                            }

                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                response.body()?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                } catch (e: Exception) {
                    e.localizedMessage?.let { Log.e("exp", it) }

                }
            }

            override fun onFailure(call: Call<GetQuoteResponse>, t: Throwable) {
                t.localizedMessage?.let { Log.e("failure", it) }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.add_quote -> {
                val intent = Intent(this, AddQuote::class.java)
                        startActivity(intent)

                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }


        }

    }


}












