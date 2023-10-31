// package flashfeed.data.core.datasource
//
// import flashfeed.base.abstraction.LanguageRepository
// import android.content.Context
// import dagger.hilt.android.qualifiers.ApplicationContext
// import flashfeed.data.base.ApplicationId
// import javax.inject.Inject
//
//
// class LanguageRepositoryImpl @Inject constructor(
//    @ApplicationContext private val context: Context,
//    @ApplicationId private val appId: String,
// ) : flashfeed.base.abstraction.LanguageRepository {
//
//    private val resource by lazy { context.resources }
//
//    override fun getString(id: Int, vararg extraArgs: Any): String {
//        return resource.getString(id, *extraArgs)
//    }
//
//    override fun getString(key: String, vararg extraArgs: Any): String {
//        val resID = resource.getIdentifier(key.replace("R.id", ""), "string", appId)
//        return resource.getString(resID, *extraArgs)
//    }
// }
