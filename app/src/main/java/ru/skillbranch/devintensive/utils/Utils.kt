package ru.skillbranch.devintensive.utils

import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue
import ru.skillbranch.devintensive.R
import java.lang.StringBuilder

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {

        when (fullName.isNullOrBlank()) {
            true -> {
                val firstName = null
                val lastName = null
                return firstName to lastName
            }
            false -> {
                val parts: List<String>? = fullName.split(" ")
                val firstName = parts?.getOrNull(0)
                val lastName = parts?.getOrNull(1)
                return firstName to lastName
            }
        }


    }

    private val translitMap = mapOf(
        'а' to "a", 'б' to "b", 'в' to "v", 'г' to "g", 'д' to "d", 'е' to "e", 'ё' to "e", 'ж' to "zh", 'з' to "z",
        'и' to "i", 'й' to "i", 'к' to "k", 'л' to "l", 'м' to "m", 'н' to "n", 'о' to "o", 'п' to "p", 'р' to "r",
        'с' to "s", 'т' to "t", 'у' to "u", 'ф' to "f", 'х' to "h", 'ц' to "c", 'ч' to "ch", 'ш' to "sh", 'щ' to "sh",
        'ъ' to "", 'ы' to "i", 'ь' to "", 'э' to "e", 'ю' to "yu", 'я' to "ya"
    )

    fun transliteration(payload: String, divider: String = " "): String {
        var result = ""
        payload.forEach {
            result += when {
                it == ' ' -> divider
                it.isUpperCase() -> translitMap[it.toLowerCase()]?.capitalize() ?: it.toString()
                else -> translitMap[it] ?: it.toString()
            }
        }
        return result
    }


    fun toInitials(firstName: String?, lastName: String?): String? {
        var fullName = ""
        if(!firstName.isNullOrBlank()){
            fullName += firstName.capitalize().first()
        }
        if(!lastName.isNullOrBlank()){
            fullName += lastName.capitalize().first()
        }
        if(fullName.isBlank())
            return null
        return fullName
    }

    fun isRepositoryValid(repository: String): Boolean {
        if (repository.isEmpty()) return true
        var repo = repository

        if (repo.startsWith("https://")) {
            repo = repo.replace("https://", "")
        }
        if (repo.startsWith("www.")) {
            repo = repo.replace("www.", "")
        }

        if (!repo.startsWith("github.com/")) {
            return false
        }

        repo = repo.replace("github.com/", "")

        repoExcludeSet.forEach {
            if (repo.startsWith(it) && (repo.length == it.length || repo[it.length] == '/')) return false
        }

        return Regex("^[A-Za-z0-9]+(-[A-Za-z0-9]+)*/?$").matches(repo)
    }

    private val repoExcludeSet = setOf(
        "enterprise",
        "features",
        "topics",
        "collections",
        "trending",
        "events",
        "marketplace",
        "pricing",
        "nonprofit",
        "customer-stories",
        "security",
        "login",
        "join"
    )

    fun getThemeAccentColor(context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.colorAccent, value, true)
        return value.data
    }

    fun getCurrentModeColor(context: Context, attrColor: Int): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(attrColor, value, true)
        return value.data
    }

    fun isNightModeActive(context: Context) : Boolean {
        return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }
    }






}