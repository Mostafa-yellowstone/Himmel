package myst.mostafayellowstone.himmel.util

import android.util.Patterns
import org.intellij.lang.annotations.Pattern

fun validateEmail(email: String): RegisterValidation{
    if (email.isEmpty()){
        return RegisterValidation.Failed("Email can not be empty")

    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        return RegisterValidation.Failed("Wrong e-mail format")
    }
    }
    return RegisterValidation.Success
}

fun validatePassword(password: String): RegisterValidation{
    if (password.isEmpty()){
        return RegisterValidation.Failed("Password can not be empty")
    }
    if (password.length <6){
        return RegisterValidation.Failed("Password should contain more than 6 characters")
    }
    return RegisterValidation.Success
}