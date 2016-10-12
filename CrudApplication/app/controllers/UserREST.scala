package controllers




  import model.Tables.{UsersRow}
  import model._
 
  import play.api.{Logger, Play}
  import play.api.Play.current
  import play.api.data.Forms._
  import play.api.data._
  import play.api.i18n.Messages
  import play.api.i18n.Messages.Implicits._
  import play.api.libs.json._
  import play.api.mvc._
  import org.mindrot.jbcrypt.BCrypt
  import scala.util.{Failure, Success}
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Future
  


class  UserREST extends Controller {
  
 def getUsers = Action.async { request =>
      
      val futureUsers = User.getAll
      //create a list of users json
      val futureUsersJson = futureUsers.map { users =>
        users.map { user =>
          JsObject(Seq(
            "id" -> JsNumber(user.id),
            "nom" -> JsString(user.nom.getOrElse("")),
            "prenom" -> JsString(user.prenom.getOrElse(""))
            
          ))
        }
      }
      futureUsersJson.map { usersJson =>
        Ok(Json.toJson(usersJson))
      }
   
 }

 //get user by Id
 def getUser(id: Int) = Action.async {

   User.findById(id).map { userOpt => userOpt match {
        case Some(user) => {
          //user exist return details
          Ok(Json.toJson(Map(
            "id" -> user.id.toString,
            "nom" -> user.nom.getOrElse(""),
            "prenom" -> user.prenom.getOrElse("")
            
          )))
        }
        case None => {
          //user doesn't exist
          NotFound(Json.toJson(Map(
            "error" -> "user doesn't exist"
          )))
        }
      }
      }

 }
}