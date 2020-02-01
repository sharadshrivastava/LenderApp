package com.test.lenderapp.data.model

import com.squareup.moshi.Json

data class ApiResponse(

	@Json(name="accounts")
	val accounts: List<AccountsItem?>? = null
)