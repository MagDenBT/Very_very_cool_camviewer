//package ch.magden.veryverycoolcamviewer.presentations
//
//import androidx.compose.runtime.mutableStateMapOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.toMutableStateList
//import ch.magden.veryverycoolcamviewer.presentations.cameras.CameraItem
//import ch.magden.veryverycoolcamviewer.presentations.doorphones.DoorphoneItem
//
//fun cameraItems() = mutableStateMapOf(
//    mutableStateOf("FIRST") to listOf(
//        CameraItem(
//            id = 1,
//            name = mutableStateOf("Camera 1"),
//            snapshotUrl = mutableStateOf("https://serverspace.ru/wp-content/uploads/2019/06/backup-i-snapshot.png"), // Изображение отсутствует (null)
//            isFavorite = mutableStateOf(true),
//            isRecording = mutableStateOf(false)
//        ), CameraItem(
//            id = 2,
//            name = mutableStateOf("Camera 45"),
//            snapshotUrl = mutableStateOf(null), // Изображение отсутствует (null)
//            isFavorite = mutableStateOf(false),
//            isRecording = mutableStateOf(true)
//        ), CameraItem(
//            id = 6,
//            name = mutableStateOf("Camera 89"),
//            snapshotUrl = mutableStateOf(null), // Изображение отсутствует (null)
//            isFavorite = mutableStateOf(true),
//            isRecording = mutableStateOf(false)
//        )
//    ).toMutableStateList(),
//    mutableStateOf("SECOND") to emptyList<CameraItem>().toMutableStateList(),
//    mutableStateOf("") to listOf(
//        CameraItem(
//            id = 3,
//            name = mutableStateOf("Camera 2"),
//            snapshotUrl = mutableStateOf(null), // Изображение отсутствует (null)
//            isFavorite = mutableStateOf(true),
//            isRecording = mutableStateOf(false)
//        )
//    ).toMutableStateList()
//)
//
//val cameraItemSnapshot = CameraItem(
//    id = 1,
//    name = mutableStateOf("Camera 1"),
//    snapshotUrl = mutableStateOf("https://serverspace.ru/wp-content/uploads/2019/06/backup-i-snapshot.png"), // Изображение отсутствует (null)
//    isFavorite = mutableStateOf(true),
//    isRecording = mutableStateOf(true)
//)
//
//val cameraItemNoSnapshot = CameraItem(
//    id = 2,
//    name = mutableStateOf("Camera 45"),
//    snapshotUrl = mutableStateOf(null), // Изображение отсутствует (null)
//    isFavorite = mutableStateOf(false),
//    isRecording = mutableStateOf(true)
//)
//
//
//
//fun doorphonesItems() = listOf(
//    DoorphoneItem(
//        id = 1,
//        name = mutableStateOf("Door Door"),
//        snapshotUrl = mutableStateOf(null),
//        isFavorite = mutableStateOf(true),
//        room = mutableStateOf("FIRST")
//    ), DoorphoneItem(
//        id = 3,
//        name = mutableStateOf("Door 45"),
//        snapshotUrl = mutableStateOf(null),
//        isFavorite = mutableStateOf(true),
//        room = mutableStateOf(null)
//    ), DoorphoneItem(
//        id = 2,
//        name = mutableStateOf("Door Door Door Door"),
//        snapshotUrl = mutableStateOf(null),
//        isFavorite = mutableStateOf(false),
//        room = mutableStateOf("L")
//    ), DoorphoneItem(
//        id = 6,
//        name = mutableStateOf("Door Door, Door Door"),
//        snapshotUrl = mutableStateOf("https://serverspace.ru/wp-content/uploads/2019/06/backup-i-snapshot.png"),
//        isFavorite = mutableStateOf(true),
//        room = mutableStateOf("FIRST")
//    )
//).toMutableStateList()
//
//
//val doorphoneItemSnapshot = DoorphoneItem(
//    id = 6,
//    name = mutableStateOf("Door Door, Door Door"),
//    snapshotUrl = mutableStateOf("https://serverspace.ru/wp-content/uploads/2019/06/backup-i-snapshot.png"),
//    isFavorite = mutableStateOf(true),
//    room = mutableStateOf("FIRST")
//)
//
//val doorphoneItemNoSnapshot = DoorphoneItem(
//    id = 3,
//    name = mutableStateOf("Door 45"),
//    snapshotUrl = mutableStateOf(null),
//    isFavorite = mutableStateOf(true),
//    room = mutableStateOf(null)
//)