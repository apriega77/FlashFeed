import org.gradle.api.JavaVersion
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AppConfig {
    const val COMPILE_SDK = 33
    const val APPLICATION_ID = "co.id.apriega77.skeleton"
    const val MIN_SDK = 24
    const val VERSION_CODE = 1
    private const val VERSION_NAME = "1.0.0"
    const val ENABLE_SECURITY = false
    val JVM_TARGET = JavaVersion.VERSION_17

    fun generateVersionBuild(): String {
        val time = Date(System.currentTimeMillis())
        val format = SimpleDateFormat("ddMMHH", Locale.ENGLISH)
        val extraVer = format.format(time)
        return "$VERSION_NAME.$extraVer"
    }
}
