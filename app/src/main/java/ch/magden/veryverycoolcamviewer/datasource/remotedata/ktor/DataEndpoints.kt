package ch.magden.veryverycoolcamviewer.datasource.remotedata.ktor

object DataEndpoints {
    const val BASE_URL = "http://cars.cprogroup.ru/api/rubetek"
    const val GET_CAMERAS_AND_ROOMS = "$BASE_URL/cameras"
    const val GET_DOORPHONES = "$BASE_URL/doors"
}
