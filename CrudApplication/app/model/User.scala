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

  // get all users 

  def getAll = {
  	db.run(users.result)
  }
}