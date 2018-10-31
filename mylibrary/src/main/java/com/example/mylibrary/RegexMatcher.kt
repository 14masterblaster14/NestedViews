package com.example.mylibrary

import java.util.regex.Pattern

/**
 */
class RegexMatcher {

    /**
     * Validate string against a regex.
     *
     * @param dataStr the data str
     * @param regex   the regex
     * @return the boolean
     */
    fun validate(dataStr: String, regex: String?): Boolean {
        if (regex == null || regex == "") {
            throw IllegalArgumentException("regex field cannot is be null or empty!")
        } else {
            val p = Pattern.compile(regex)
            return validate(dataStr, p)
        }
    }


    /**
     * Find in string against a regex.
     *
     * @param dataStr the data str
     * @param regex   the regex
     * @return the boolean
     */
    fun find(dataStr: String, regex: String?): Boolean {
        if (regex == null || regex == "") {
            throw IllegalArgumentException("regex field cannot is be null or empty!")
        } else {
            val p = Pattern.compile(regex)
            return find(dataStr, p)
        }
    }

    /**
     * Validate string against a pattern.
     *
     * @param dataStr the data str
     * @param pattern the pattern
     * @return the boolean
     */
    fun validate(dataStr: String?, pattern: Pattern): Boolean {
        return !(dataStr == null || dataStr == "") && pattern.matcher(dataStr).matches()
    }

    /**
     * Find in string against a pattern.
     *
     * @param dataStr the data str
     * @param pattern the pattern
     * @return the boolean
     */
    fun find(dataStr: String?, pattern: Pattern): Boolean {
        return !(dataStr == null || dataStr == "") && pattern.matcher(dataStr).find()
    }
}