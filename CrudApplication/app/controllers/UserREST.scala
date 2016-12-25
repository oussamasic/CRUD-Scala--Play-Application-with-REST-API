package controllers




  import model.Tables.{UsersRow}
  import model._
  import model.User.UserFormat
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
  import scala.util.{Failure, Success}
  


class  UserREST extends Controller {


// create a new user

//get all users
  def kolchi = Action.async {request =>
    
    User.getAll.map {
        users => Ok(JsArray(users.map {
          user => UserFormat.writes(user)
        }))
      }
    }
  
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

 // add new user 

 def addUser = Action.async(parse.json) { 
  implicit request =>

      val userForm = Form(tuple(
        "prenom" -> optional(text), //1
        "nom" -> optional(text) //2
        ))

      userForm.bindFromRequest.fold(
        formWithErrors => {
          Future.successful(BadRequest(Json.toJson(Map(
            "error" -> "missing parameters"
          ))))
        },
        userData => {
          val newUser = UsersRow(id = 0, prenom = Some(userData._1.get.capitalize),
            nom = Some(userData._2.get.toUpperCase))

        User.add(newUser).map { user_id =>
         // val timestamp = Some(System.currentTimeMillis())
          //val tokenGenerated = Generator.generateToken(Some(newUser.email),timestamp,user_id)
         // val userStatus = AccountstatusRow(idaccountStatus = 0, status = validUser,token= Some(tokenGenerated) ,flag = Some("RG"),timestamp= Some(System.currentTimeMillis()),usersId = user_id)
         // StatusObj.add(userStatus)

          
            Ok(Json.toJson(Map(
              "message" -> "user created",
              "user_id" -> user_id.toString
            )))
          }

         

        }
      )
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
  // example of login or find by names

 def login(prenom : String, nom : String)=Action.async {
  User.findByNames(prenom,nom).map {userOpt => userOpt match {
    case Some(user) => {
      Ok(Json.toJson(Map(
        "Success" -> "mer7ba biik al m3aleeem" 
        )))
    } 
    case None => {
      NotFound(Json.toJson(Map(
            "error" -> "user doesn't exist sir f7aleek"
          )))
    } 
  }
  

  }
 }




}