package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    val lastVisit: Date? = null,
    val isOnline: Boolean = false

) {
//    val introBit: String

    constructor(id: String, firstName: String?, lastName: String?) : this(
        id, firstName, lastName, null
    )

    constructor(id: String) : this(id, "John", "Doe")

    init {
//        introBit = getIntro()

        println("It' s Alive!!!\n ${if (lastName === "Doe") "His name id $firstName $lastName" else "And his name is $firstName $lastName!!!"}\n"
                )
    }

    private fun getIntro() = """
        tu tu tu tututu
        tu tu tu tututututut

        tu tu tu tututu
        tu tu tu tututututut
        ${"\n\n\n"}
        $firstName $lastName

    """.trimIndent()

    fun printMe() = println(
        """
            id: $id
            firstName: $firstName
            lastName: $lastName
            avatar: $avatar
            rating: $rating
            respect: $respect
            lastVisit: $lastVisit
            isOnline: $isOnline
        """.trimIndent()
    )

    companion object Factory{
        private var lastId: Int = -1
        fun makeUser(fullName:String?) : User{
            lastId++

            val (firstName, lastName) = Utils.parseFullName(fullName)

            return User(id = "$lastId", firstName = firstName, lastName = lastName)
        }
    }


}