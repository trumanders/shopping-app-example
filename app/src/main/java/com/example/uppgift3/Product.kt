package com.example.uppgift3

import java.io.Serializable
import java.math.BigDecimal

class Product (
    var name: String = "",
    var image: Int = -1,
    var price: BigDecimal = BigDecimal(0)
) : Serializable