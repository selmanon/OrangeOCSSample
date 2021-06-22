package com.test.orangeocssample.data.api

/*
 void addAll(Collection<? extends E> items);
 The wildcard type argument "? extends E" indicates that this method accepts a collection of objects of E or some subtype of E,
 not just E itself. This means that we can safely read E's from items (elements of this collection are instances of a subclass of E),
 but cannot write to it since we do not know what objects comply to that unknown subtype of E.
 In return for this limitation, we have the desired behaviour: Collection<String> is a subtype of Collection<? extends Object>.
 In "clever words", the wildcard with an extends-bound (upper bound) makes the type covariant.
 */
@Suppress("UNCHECKED_CAST")
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Empty : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            else -> "Empty"
        }
    }
}