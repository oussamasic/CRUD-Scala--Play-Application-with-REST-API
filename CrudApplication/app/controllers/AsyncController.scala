package controllers

import akka.actor.ActorSystem
import javax.inject._
import play.api._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.concurrent.duration._

/**
 * This controller creates an `Action` that demonstrates how to write
 * simple asynchronous code in a controller. It uses a timer to
 * asynchronously delay sending a response for 1 second.
 *
 * @param actorSystem We need the `ActorSystem`'s `Scheduler` to
 * run code after a delay.
 * @param exec We need an `ExecutionContext` to execute our
 * asynchronous code.
 */
@Singleton
class AsyncController @Inject() (actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller {

  /**
   * Create an Action that returns a plain text message after a delay
   * of 1 second.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/message`.
   */
  def message = Action.async {
    getFutureMessage(1.second).map { msg => Ok(msg) }
  }

// to inject the database using Slick
  def setupSlick = Action {implicit request =>
    println("***" + Array("slick.driver.MySQLDriver", "com.mysql.jdbc.Driver", Play.configuration.getString("mysql.url").getOrElse("URL NOT FOUND"), Play.current.path + "/app", "model", Play.configuration.getString("mysql.properties.user").getOrElse("UserName NOT FOUND"), Play.configuration.getString("mysql.properties.password").getOrElse("password NOT FOUND")).toList)
    slick.codegen.SourceCodeGenerator.main(
      Array("slick.driver.MySQLDriver", "com.mysql.jdbc.Driver", Play.configuration.getString("mysql.url").getOrElse("URL NOT FOUND"), Play.current.path + "/app", "model", Play.configuration.getString("mysql.properties.user").getOrElse("UserName NOT FOUND"), Play.configuration.getString("mysql.properties.password").getOrElse("password NOT FOUND"))
    )
    
    // add
    Ok("success Setup : good Job")
  }
// say hello to everybody
   def Hello = Action {
   
    Ok("Hello my name is Oussama Zerouali. Welcome to my World")
  }

  private def getFutureMessage(delayTime: FiniteDuration): Future[String] = {
    val promise: Promise[String] = Promise[String]()
    actorSystem.scheduler.scheduleOnce(delayTime) { promise.success("Hi!") }
    promise.future
  }

}
