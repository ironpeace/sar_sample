package models

import com.github.aselab.activerecord._
import com.github.aselab.activerecord.dsl._

import java.security.MessageDigest

case class User(
	@Required
	name: String, 

	@Transient 
	@Required
	password: String

	) extends ActiveRecord {

		var hashedPassword:String = _
		override def beforeSave() {
			hashedPassword = User.hash(password)
		}

		val followId:Option[Long] = None
		lazy val follow = belongsTo[Follow]

		override def afterSave() {
			Follow(this.id).save
		}
}

object User extends ActiveRecordCompanion[User]{

	def hash(target : String):String = {
		MessageDigest
			.getInstance("SHA1")
			.digest(target.getBytes)
			.map(_ & 0xFF)
			.map(_.toHexString)
			.mkString
	}
}
