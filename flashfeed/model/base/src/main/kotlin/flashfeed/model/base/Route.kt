package flashfeed.model.base

interface Route {
    val alias: String
        get() = this::class.simpleName.orEmpty()
}
