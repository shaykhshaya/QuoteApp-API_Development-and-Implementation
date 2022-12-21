package com.shaya.quotes.response

import com.shaya.quotes.model.QuoteModel

data class GetQuoteResponse(
    val status: String,
    val message: String,
    val data: List<QuoteModel>
)
