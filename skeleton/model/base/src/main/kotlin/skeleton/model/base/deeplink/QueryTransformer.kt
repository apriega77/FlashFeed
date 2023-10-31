package skeleton.model.base.deeplink

import android.os.Parcelable

fun interface QueryTransformer {
    fun transformToArgs(queries: HashMap<String, String>): Parcelable?
}
