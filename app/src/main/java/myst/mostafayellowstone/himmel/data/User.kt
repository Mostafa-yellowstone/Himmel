package myst.mostafayellowstone.himmel.data


data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    var imgPath: String = ""
){
    constructor():this("" , "" , "" , "")
}
