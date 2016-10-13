package model

import model.Tables._
import play.api.Play
import play.api.data.Forms._
import play.api.data._

import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play.current
import model.Tables.{UsersRow}

import play.api.libs.json._


import scala.concurrent.Future

import scala.math.BigDecimal

object User {
	
  val users = Users
  val db = Database.forConfig("mysql");

    implicit object UserFormat extends Format[UsersRow] {

    // convert from Tweet object to JSON (serializing to JSON)
    def writes(user: UsersRow): JsValue = {
      //  tweetSeq == Seq[(String, play.api.libs.json.JsString)]
      val usersSeq = Seq(
        "id" -> JsNumber(user.id),
        "code" -> JsString(user.nom.get),
        "title" -> JsString(user.prenom.get)
     
      )
      JsObject(usersSeq)
    }

    // convert from JSON string to a Tweet object (de-serializing from JSON)
    // (i don't need this method; just here to satisfy the api)
    def reads(userInfo: JsValue): JsResult[UsersRow] = {
      val id = (userInfo \ "id").asOpt[Int].getOrElse(0)
      val prenom = (userInfo \ "prenom").asOpt[String]
      val nom = (userInfo \ "nom").asOpt[String]
     
      var userRow = UsersRow(id = id, nom = nom, prenom = prenom)

      JsSuccess(userRow)
    }
  }

  //find by id 
  def findById(id : Int) = {
  	db.run(users.filter(_.id === id).result.headOption)
  }

  // find by firstname and lastname

  def findByNames(prenom : String, nom : String) = {
    db.run(users.filter(user => user.nom === nom && user.prenom === prenom).result.headOption)
  }

  //add New User

   def add(user: UsersRow) = {
    val userId = users returning users.map(_.id) += user
    db.run(userId)
  }

//delete a user by id
   def delete(id: Int) = {
    db.run(users.filter(_.id === id).delete)
  }

  // get all users 

  def getAll = {
  	db.run(users.result)
  }
}