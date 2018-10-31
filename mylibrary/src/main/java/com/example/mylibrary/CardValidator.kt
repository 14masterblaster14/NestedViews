package com.example.mylibrary

/**
 */
class CardValidator {

    fun validateCreditCardNumber(number: String): Boolean {
        return (checkIfNumberContainsOnlyDigits(number)
                && validateLengthOfCardNumber(number)
                && validateAndGetStartingSixDigits(number) > 0
                && validateCardNumberWithLuhnAlgo(number))
    }

    private fun getErrorInfo(number: String): String {
        if (!checkIfNumberContainsOnlyDigits(number)) {
            return "Number should be composed of only digits!"
        }

        if (!validateLengthOfCardNumber(number)) {
            return "Card number should be of length > 12 and < 19 digits!"
        }

        if (validateAndGetStartingSixDigits(number) == 0L) {
            return "Number contains leading zeros!"
        }

        return if (!validateCardNumberWithLuhnAlgo(number)) {
            "Number did not pass the Luhn Algo Test!"
        } else "NA"

    }

    private fun getCreditCardIssuer(number: String): String {
        return getTypeOfCard(validateAndGetStartingSixDigits(number))
    }

    private fun countDigitsInNumber(num: Long): Int {
        var num = num
        var count = 0
        while (num > 0) {
            num = num / 10
            count++
        }
        return count
    }

    private fun validateCardNumberWithLuhnAlgo(num: String): Boolean {
        var sumOfDoubleOfDigits = 0
        if (checkIfNumberContainsOnlyDigits(num)) {
            var alternateValue = false
            for (i in num.length - 1 downTo 0) {
                var digit = Integer.parseInt(num[i].toString())

                if (alternateValue) {
                    digit *= 2
                    if (digit > 9) {
                        digit -= 9
                    }
                }
                sumOfDoubleOfDigits += digit
                alternateValue = !alternateValue
            }
        }

        return sumOfDoubleOfDigits % 10 == 0
    }

    private fun checkIfNumberContainsOnlyDigits(number: String): Boolean {
        // check if number string contains only digits
        return number.matches("[0-9]+".toRegex())
    }

    private fun validateLengthOfCardNumber(number: String): Boolean {
        // check for number of digits
        return !(number.length < 12 || number.length > 19)
    }

    private fun validateAndGetStartingSixDigits(number: String): Long {
        val startSixDigitSubstring = number.substring(0, 6)
        if (checkIfNumberContainsOnlyDigits(startSixDigitSubstring)) {
            val startNumber = java.lang.Long.parseLong(startSixDigitSubstring)
            // Check for leading zeros
            return if (startNumber == 0L || countDigitsInNumber(startNumber) < 6) {
                0
            } else startNumber

        } else {
            return 0
        }
    }

    private fun getTypeOfCard(startingSixDigits: Long): String {
        return if (startingSixDigits > 400000 && startingSixDigits < 499999) {
            "Visa"
        } else if (startingSixDigits > 222100 && startingSixDigits < 272099 || startingSixDigits > 510000 && startingSixDigits < 559999) {
            "Mastercard"
        } else if (startingSixDigits > 620000 && startingSixDigits < 629999) {
            "China Union Pay"
        } else if (startingSixDigits > 500000 && startingSixDigits < 509999 || startingSixDigits > 560000 && startingSixDigits < 699999) {
            "Maestro"
        } else {
            "Unknown"
        }
    }


    /**
     * Gets card information.
     *
     * @param num the num
     * @return the card information
     */
    fun getCardInformation(num: String): CardInformation {
        val cardInformation = CardInformation(num)
        cardInformation.cardIssuer = getCreditCardIssuer(num)
        cardInformation.isValid = validateCreditCardNumber(num)
        cardInformation.error = getErrorInfo(num)
        return cardInformation
    }

}