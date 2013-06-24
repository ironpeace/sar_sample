import play.api._
import com.github.aselab.activerecord._
import com.github.aselab.activerecord.dsl._
import models._

object Global extends GlobalSettings {
	override def onStart(app: Application) {
		Tables.initialize

		if(User.count == 0){
			val abe = User("shinzo_abe", "abe_pass").create
			val matz = User("yukihiro_matsumoto", "matz_pass").create
			val ichi = User("ichiro_suzuki", "ichiro_pass").create

			val whoMatzFollows = Follow.where(_.userid === matz.id).head

			println("******************************************* 0")
			User.findBy("name", "shinzo_abe") match {
				case Some(u) => {
					whoMatzFollows.users << u
				}
				case None => //
			}

			println("******************************************* 1")
			User.findBy("name", "ichiro_suzuki") match {
				case Some(u) => {
					whoMatzFollows.users << u
				}
				case None => //
			}

			println("******************************************* 2")
			whoMatzFollows.users << abe

			println("******************************************* 3")
			whoMatzFollows.users << ichi
		}
	}

	override def onStop(app: Application) {
		Tables.cleanup
	}
}