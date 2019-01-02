package rasyidk.fa.tiketextratest.model

data class Users(val success: Boolean, val message: String, val data: User) {
    data class User(val name: String, val mobile: String, val email: String, val user_image: String)
}