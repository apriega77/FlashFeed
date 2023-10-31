//3
package modules

object UsecaseModules {

	object Base : Usecase(":flashfeed:usecase:base","base")
	object Core : Usecase(":flashfeed:usecase:core","core")
	object Home : Usecase(":flashfeed:usecase:home","home")
}
