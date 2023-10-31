package flashfeed.usecase.base

abstract class BaseUseCaseSync<in Args, out Result> {
    protected abstract fun build(args: Args): Result

    operator fun invoke(args: Args): Result {
        return build(args)
    }
}
