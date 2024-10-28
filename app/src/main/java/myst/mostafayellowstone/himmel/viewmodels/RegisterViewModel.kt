package myst.mostafayellowstone.himmel.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import myst.mostafayellowstone.himmel.data.User
import myst.mostafayellowstone.himmel.util.Constants.USER_COLLECTION
import myst.mostafayellowstone.himmel.util.RegisterFieldsState
import myst.mostafayellowstone.himmel.util.RegisterValidation
import myst.mostafayellowstone.himmel.util.Resource
import myst.mostafayellowstone.himmel.util.validateEmail
import myst.mostafayellowstone.himmel.util.validatePassword
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth,
private val db: FirebaseFirestore): ViewModel() {

   private val _register = MutableStateFlow<Resource<User>>(Resource.Unspcified())
    val register:StateFlow<Resource<User>> = _register

    private val _validation = Channel<RegisterFieldsState>()
    val validation = _validation.receiveAsFlow()

    fun createAccountWithEmailAndPassword(user: User, password: String){
        if (checkValidation(user, password)) {
            runBlocking {
                _register.emit(Resource.Loading())
            }
            firebaseAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener {
                    it.user?.let {
                        saveUserInfo(it.uid, user)
//                        _register.value = Resource.Success(it)
                    }
                }
                .addOnFailureListener {
                    _register.value = Resource.Error(it.message.toString())
                }
        }else{
            val registerFieldState = RegisterFieldsState(validateEmail(user.email),
                validatePassword(password)
            )
            runBlocking {
                _validation.send(registerFieldState)

            }
        }
    }

    private fun saveUserInfo(userUID: String , user: User) {
        db.collection(USER_COLLECTION)
            .document(userUID)
            .set(user)
            .addOnSuccessListener {
                _register.value = Resource.Success(user)
            }
            .addOnFailureListener {
                _register.value = Resource.Error(it.message.toString())

            }

    }

    private fun checkValidation(user: User, password: String): Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidation = validatePassword(password)
        val shouldRegister =
            emailValidation is RegisterValidation.Success && passwordValidation is RegisterValidation.Success
        return shouldRegister
    }
}