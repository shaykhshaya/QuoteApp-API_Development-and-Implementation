package com.shaya.quotes.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.shaya.quotes.R
import com.shaya.quotes.UpdateQuote
import com.shaya.quotes.api.ApiClient
import com.shaya.quotes.api.ApiInterface
import com.shaya.quotes.model.QuoteModel
import com.shaya.quotes.response.DeleteResponse
import com.shaya.quotes.response.GetQuoteResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class QuoteAdapter(private val context: Context, private val quoteModels: MutableList<QuoteModel> ):RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {


    private val retrofit: Retrofit? = ApiClient.getclient()
    val apiInterface = retrofit!!.create(ApiInterface::class.java)


    class QuoteViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val textView_quote_data:TextView = view.findViewById(R.id.textview_quote_data)
        val textView_quote_name: TextView = view.findViewById(R.id.textview_quote_name)
        val textView_quote_dateandtime: TextView = view.findViewById(R.id.textview_quote_dateandtime)
        val update_button: androidx.appcompat.widget.AppCompatButton = view.findViewById(R.id.update_button)
        val delete_button: androidx.appcompat.widget.AppCompatButton = view.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.quote_list_design, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = quoteModels[position]
        holder.textView_quote_data.text = item.quote_data
        holder.textView_quote_name.text = item.quote_name
        holder.textView_quote_dateandtime.text = item.date_time

        holder.delete_button.setOnClickListener {
        deletequote(item.id, position)
        }

        holder.update_button.setOnClickListener {
            //Toast.makeText(context, quoteModels[position].id+ quoteModels[position].quote_data, Toast.LENGTH_LONG).show()
            val intent = Intent(context, UpdateQuote::class.java)
            intent.putExtra("q_id", quoteModels[position].id.toInt())
            intent.putExtra("q_data", quoteModels[position].quote_data)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return quoteModels.size
    }


    private fun deletequote(id: String, pos: Int){
        apiInterface.deletequote(id).enqueue(object:retrofit2.Callback<DeleteResponse>
        {
            override fun onResponse(
                call: Call<DeleteResponse>,
                response: Response<DeleteResponse>
            ) {
                try {
                    if(response!=null) {
                        Toast.makeText(context, response.body()?.message, Toast.LENGTH_LONG).show()
                        if(response.body()?.status.equals("1")){
                            quoteModels.removeAt(pos)
                            notifyDataSetChanged()
                        }
                    }
                }catch (e: Exception){
                    e.localizedMessage?.let { Log.e("exp", it) }
                }
            }
            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                t.localizedMessage?.let { Log.e("failure", it) }
            }
        })
    }
}