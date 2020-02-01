package com.test.lenderapp.data.model

import com.squareup.moshi.Json

data class AccountsItem(

	@Json(name="months")
	val months: List<MonthsItem?>? = null,

	@Json(name="name")
	val name: String? = null
)