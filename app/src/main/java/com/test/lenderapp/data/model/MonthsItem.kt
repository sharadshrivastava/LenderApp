package com.test.lenderapp.data.model

import com.squareup.moshi.Json

data class MonthsItem(

	@Json(name="total")
	val total: Double? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="household")
	val household: Double? = null,

	@Json(name="transport")
	val transport: Double? = null,

	@Json(name="groceries")
	val groceries: Double? = null,

	@Json(name="food")
	val food: Double? = null
)