package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME){
    fun askQuestion() = question.question

    fun listenAnswer(answer: String) : Pair<String, Triple<Int, Int, Int>>{
        val validationError = question.validate(answer)
        if (validationError != null){
            return (validationError + question.question) to status.color
        }
        return if(question.answers.contains(answer)){
            question = question.nextQuestion()

            "Отлично - ты справился\n${question.question}" to status.color
        } else{
            status = status.nextStatus()
            if (status.ordinal != 0){
                "Это неправильный ответ\n${question.question}" to status.color
            } else{
                question = Question.NAME

                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }
        }
    }


    enum class Status(val color: Triple<Int, Int, Int>){
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status{
            return if(ordinal < values().lastIndex){
                values()[ordinal + 1]
            }else{
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>){
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun validate(answer: String): String? =
                if (!answer[0].isUpperCase())
                    "Имя должно начинаться с заглавной буквы\n"
                else null
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun validate(answer: String): String? =
                if(!answer[0].isLowerCase())
                    "Профессия должна начинаться со строчной буквы\n"
                else null
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")){
            override fun nextQuestion(): Question = BDAY
            override fun validate(answer: String): String? =
                if (answer.contains(Regex("\\d")))
                    "Материал не должен содержать цифр\n"
                else null
        },
        BDAY("Когда меня создали?", listOf("2993")){
            override fun nextQuestion(): Question = SERIAL
            override fun validate(answer: String): String? =
                if (answer.contains(Regex("\\D")))
                    "Год моего рождения должен содержать только цифры\n"
                else null
        },
        SERIAL("Мой серийный номер?", listOf("2716057")){
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String): String? =
                if (!Regex("\\d{7}").matches(answer))
                    "Серийный номер содержит только цифры, и их 7\n"
                else null
        },
        IDLE("На этом все, вопросов больше нет", listOf()){
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String): String? = null
        };

        abstract fun nextQuestion(): Question
        abstract fun validate(answer: String): String?

        }

}