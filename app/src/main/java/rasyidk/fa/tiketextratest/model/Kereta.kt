package rasyidk.fa.tiketextratest.model

data class Kereta(val success: String, val message: String, val data: Datas) {

    data class Datas(val go: ArrayList<Go>, val back: ArrayList<Back>)

    data class Go(val arrivalStationCode: String, val arrivalTime: String, val arrivalDate: String, val departureDate: String,
                  val departureStationCode: String, val departureTime: String, val trainName: String, val trainNo: String,
                  val seatAvail: String, val classCategory: String, val code: String, val fare: String, val departStationName: String,
                  val departCity: String, val arrivalStationName: String, val arrivalCity: String)

    data class Back(val arrivalStationCode: String, val arrivalTime: String, val arrivalDate: String, val departureDate: String,
                  val departureStationCode: String, val departureTime: String, val trainName: String, val trainNo: String,
                  val seatAvail: String, val classCategory: String, val code: String, val fare: String, val departStationName: String,
                  val departCity: String, val arrivalStationName: String, val arrivalCity: String)
}