package skeleton.data.core.repository

import android.content.Context
import android.os.Build
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import skeleton.abstraction.core.CoreRepository
import skeleton.data.base.AppVersion
import skeleton.data.base.AppVersionCode
import skeleton.data.base.BasePreference
import skeleton.model.core.Channel
import javax.inject.Inject

class CoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @AppVersion private val appVersion: String,
    @AppVersionCode private val appVersionCode: Int,
    private val pref: BasePreference,
) : CoreRepository {
    override fun getChannel(): Channel {
        val uuid = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        val version = appVersion
        val model = "${Build.MANUFACTURER} ${Build.MODEL}"
        val operatingSystem = Build.VERSION.SDK_INT.toString()
        return Channel(
            uuid,
            version,
            model,
            operatingSystem,
        )
    }

    override fun getVersionName(): String {
        return appVersion
    }

    override fun getVersion(): String {
        return "v $appVersion ($appVersionCode)"
    }
}
