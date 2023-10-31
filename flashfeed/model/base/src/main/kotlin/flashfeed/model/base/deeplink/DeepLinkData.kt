package flashfeed.model.base.deeplink

import android.net.Uri

data class DeepLinkData(
    val path: String,
    val fullPath: String,
    val queries: HashMap<String, String> = hashMapOf(),
) {
    companion object {
        fun from(uri: Uri): DeepLinkData {
            val fullPath = uri.toString()
            val queries = hashMapOf<String, String>()
            uri.queryParameterNames.forEach { queryName ->
                uri.getQueryParameter(queryName)?.let { queryValue ->
                    queries[queryName] = queryValue
                }
            }
            return DeepLinkData(uri.path.orEmpty(), fullPath, queries)
        }

        fun from(url: String) = from(Uri.parse(url))
    }
}
