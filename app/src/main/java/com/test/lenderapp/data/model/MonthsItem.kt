package com.test.lenderapp.data.model

import com.squareup.moshi.Json

data class MonthsItem(

	@Json(name="total")
	val total: Int? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="household")
	val household: Int? = null,

	@Json(name="transport")
	val transport: Int? = null,

	@Json(name="groceries")
	val groceries: Int? = null,

	@Json(name="food")
	val food: Int? = null
)