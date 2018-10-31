package com.example.mylibrary

/**
 */
data class CardInformation(var cardNumber: String) {

    var cardIssuer: String? = "default_cardIssuer"
    var isValid: Boolean? = false
    var error: String? = "default_error"

    constructor(cardNumber: String, mCardIssuer: String, mIsValid: Boolean, mError: String) : this(cardNumber) {

        cardIssuer = mCardIssuer
        isValid = mIsValid
        error = mError
    }

}
