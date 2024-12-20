package com.wrathenn.fieldhash.model

/**
 * Class used to create Geohash conditions to use them in databases.
 *
 * See [HashConditionInterval] for example
 *
 * For example [HashConditionInterval] with start="bb" and end="bc":

 */
interface HashCondition {
    fun renderCondition(column: String): String
}

data class HashConditionInterval(val start: String, val end: String) : HashCondition {
    private fun renderConditionImpl(column: String, start: String, end: String): String {
        return "$column between '$start' and '$end|'"
    }

    override fun renderCondition(column: String): String {
        return renderConditionImpl(column, start, end)
    }
}

fun Iterable<HashCondition>.collectQueryCondition(column: String): String {
    return this.joinToString( " or ", "(", ")" ) { it.renderCondition(column) }
}
