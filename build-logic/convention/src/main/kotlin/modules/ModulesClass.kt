package modules

abstract class Abstraction(
    override val path: String,
    override val featureName: String,
) : Modules

abstract class Usecase(
    override val path: String,
    override val featureName: String,
) : Modules

abstract class Data(
    override val path: String,
    override val featureName: String,
) : Modules

abstract class Model(
    override val path: String,
    override val featureName: String,
) : Modules

abstract class Presentation(
    override val path: String,
    override val featureName: String,
) : Modules

interface Modules {
    val path: String
    val featureName: String
}



