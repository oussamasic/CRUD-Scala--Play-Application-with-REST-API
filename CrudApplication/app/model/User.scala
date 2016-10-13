package model

import model.Tables._
import play.api.Play
import play.api.data.Forms._
import play.api.data._

import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play.current

object User {
	
  val users = Users
  val db = Database.forConfig("mysql");

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