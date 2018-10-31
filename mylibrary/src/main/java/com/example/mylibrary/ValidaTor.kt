package com.example.mylibrary

import android.graphics.Color
import android.graphics.Color.parseColor
import org.json.JSONException
import org.json.JSONArray
import org.json.JSONObject
import android.text.TextUtils
import java.util.regex.Pattern


/**
 */


class ValidaTor {

    /**
     * The Regex matcher.
     */
    private val regexMatcher: RegexMatcher = RegexMatcher()

    /**
     * The Card validator.
     */
    private val cardValidator: CardValidator = CardValidator()


    /**
     * check if the string contains the seed.
     *
     * @param str  the str
     * @param seed the seed
     * @return the boolean
     */
    fun containsSubstring(str: String, seed: String): Boolean {
        return Pattern.compile(Pattern.quote(seed), Pattern.CASE_INSENSITIVE).matcher(str).find()
    }


    /**
     * check if the string contains only letters.
     *
     * @param str the str
     * @return the boolean
     */
    fun isAlpha(str: String): Boolean {
        return regexMatcher.validate(str, RegexPresetPattern.ALPHA)

    }

    /**
     * check if the string contains only letters and numbers.
     *
     * @param str the str
     * @return the boolean
     */
    fun isAlphanumeric(str: String): Boolean {
        return regexMatcher.validate(str, RegexPresetPattern.ALPHANUMERIC)
    }


    /**
     * check if a string is a boolean.
     *
     * @param str the str
     * @return the boolean
     */
    fun isBoolean(str: String): Boolean {
        return str.toLowerCase() == "true" || str.toLowerCase() == "false"
    }

    /**
     * check if the string is a ip address.
     *
     * @param str the str
     * @return the boolean
     */
    fun isIPAddress(str: String): Boolean {
        return regexMatcher.validate(str, RegexPresetPattern.IP_ADDRESS)
    }

    /**
     * check if the string is a email address
     *
     * @param str the str
     * @return the boolean
     */
    fun isEmail(str: String): Boolean {
        return regexMatcher.validate(str, RegexPresetPattern.EMAIL)
    }

    /**
     * check if the string is a US phone number
     *
     * @param str the str
     * @return the boolean
     */
    fun isPhoneNumber(str: String): Boolean {
        return regexMatcher.validate(str, RegexPresetPattern.PHONE)
    }

    /**
     * check if the string has a length of zero.
     *
     * @param str the str
     * @return the boolean
     */
    fun isEmpty(str: String?): Boolean {
        return str == null || TextUtils.isEmpty(str)
    }

    /**
     * check if a string is base64 encoded.
     *
     * @param str the str
     * @return the boolean
     */
    fun isBase64(str: String): Boolean {
        try {
            android.util.Base64.decode(str, android.util.Base64.DEFAULT)
            return true
        } catch (e: IllegalArgumentException) {
            return false
        }

    }


    /**
     * check if the string represents a decimal number, such as 0.1, .3, 1.1, 1.00003, 4.0, etc.
     *
     * @param str the str
     * @return the boolean
     */
    fun isDecimal(str: String): Boolean {

        return regexMatcher.validate(str, RegexPresetPattern.DECIMAL_NUMBER)
    }


    /**
     * check if the string is of atleast the specified length.
     *
     * @param str the str
     * @param len the len
     * @return the boolean
     */
    fun isAtleastLength(str: String, len: Int): Boolean {

        return str.length >= len
    }

    /**
     * check if the string is of atmost the specified length.
     *
     * @param str the str
     * @param len the len
     * @return the boolean
     */
    fun isAtMostLength(str: String, len: Int): Boolean {

        return str.length <= len
    }


    /**
     * check if the string is all lowercase.
     *
     * @param str the str
     * @return the boolean
     */
    fun isLowercase(str: String): Boolean {
        return str == str.toLowerCase()
    }

    /**
     * check if the string is all uppercase.
     *
     * @param str the str
     * @return the boolean
     */
    fun isUppercase(str: String): Boolean {
        return str == str.toUpperCase()
    }


    /**
     * check if the string is a valid MD5 hash.
     *
     * @param str the str
     * @return the boolean
     */
    fun isValidMD5(str: String): Boolean {

        return regexMatcher.validate(str, RegexPresetPattern.MD5)
    }

    /**
     * check if the string contains only numbers.
     *
     * @param str the str
     * @return the boolean
     */
    fun isNumeric(str: String): Boolean {

        return regexMatcher.validate(str, RegexPresetPattern.NUMERIC)
    }

    /**
     * check if the string is a MAC address
     *
     * @param str the str
     * @return the boolean
     */
    fun isMACAddress(str: String): Boolean {

        return regexMatcher.validate(str, RegexPresetPattern.MAC_ADDRESS)
    }

    /**
     * check if the string is valid JSON.
     *
     * @param str the str
     * @return the boolean
     */
    fun isJSON(str: String): Boolean {
        try {
            // check against JSONObject
            JSONObject(str)
        } catch (ex: JSONException) {
            // check against JSONArray
            try {
                JSONArray(str)
            } catch (ex1: JSONException) {
                return false
            }

            return false
        }

        return true
    }

    /**
     * check if the string is an integer.
     *
     * @param str the str
     * @return the boolean
     */
    fun isInteger(str: String?): Boolean {

        if (str == null) {
            return false
        }
        val length = str.length
        if (length == 0) {
            return false
        }
        var i = 0
        if (str[0] == '-') {
            if (length == 1) {
                return false
            }
            i = 1
        }
        while (i < length) {
            val c = str[i]
            if (c < '0' || c > '9') {
                return false
            }
            i++
        }
        return true
    }


    /**
     * check if the string is present in an array of allowed values.
     *
     * @param str    the str
     * @param values the values
     * @return the boolean
     */
    fun isIn(str: String, values: Array<String>): Boolean {
        for (`val` in values) {
            if (`val` == str) {
                return true
            }
        }
        return false
    }


    /**
     * check if the string is a hexadecimal number.
     *
     * @param str the str
     * @return the boolean
     */
    fun isHexadecimal(str: String): Boolean {

        return regexMatcher.validate(str, RegexPresetPattern.HEXADECIMAL)
    }

    /**
     * check if the string is a pincode.
     *
     * @param str the str
     * @return the boolean
     */
    fun isPinCode(str: String): Boolean {

        return regexMatcher.validate(str, RegexPresetPattern.PINCODE)
    }


    /**
     * check if the string is a hexadecimal color.
     *
     * @param str the str
     * @return the boolean
     */
    fun isHexColor(str: String): Boolean {
        try {
            Color.parseColor(str)
            return true
        } catch (iae: IllegalArgumentException) {
            return false
        }

    }


    /**
     * check if the string has atleast one digit
     *
     * @param str the str
     * @return the boolean
     */
    fun hasAtleastOneDigit(str: String): Boolean {
        return regexMatcher.find(str, RegexPresetPattern.ATLEAST_ONE_DIGIT)
    }

    /**
     * check if the string has atleast one letter
     *
     * @param str the str
     * @return the boolean
     */
    fun hasAtleastOneLetter(str: String): Boolean {
        return regexMatcher.find(str, RegexPresetPattern.ATLEAST_ONE_LETTER)
    }


    /**
     * check if the string has atleast one lowercase character
     *
     * @param str the str
     * @return the boolean
     */
    fun hasAtleastOneLowercaseCharacter(str: String): Boolean {
        return regexMatcher.find(str, RegexPresetPattern.ATLEAST_ONE_LOWERCASE_CHARACTER)
    }


    /**
     * check if the string has atleast one uppercase character
     *
     * @param str the str
     * @return the boolean
     */
    fun hasAtleastOneUppercaseCharacter(str: String): Boolean {
        return regexMatcher.find(str, RegexPresetPattern.ATLEAST_ONE_UPPERCASE_CHARACTER)
    }


    /**
     * check if the string has atleast one special character
     *
     * @param str the str
     * @return the boolean
     */
    fun hasAtleastOneSpecialCharacter(str: String): Boolean {
        return regexMatcher.find(str, RegexPresetPattern.ATLEAST_ONE_SPECIAL_CHARACTER)
    }

    /**
     * check if the string is a valid credit card number
     *
     * @param str the str
     * @return the boolean
     */
    fun validateCreditCard(str: String): Boolean {
        return cardValidator.validateCreditCardNumber(str)
    }

    /**
     * Get CreditCard information from string
     *
     * @param str the str
     * @return the credit card info
     */
    fun getCreditCardInfo(str: String): CardInformation {
        return cardValidator.getCardInformation(str)
    }

}