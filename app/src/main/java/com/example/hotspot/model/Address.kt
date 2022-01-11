package com.example.hotspot.model

class Address(
    var streetName: String? = null,
    var doorNumber: Int? = null,
    var floor: String? = null,
    var postalCode: String? = null,
    var town: String? = null,
    var country: String = "Denmark",

) {
}