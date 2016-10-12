package model
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema = Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Users
   *  @param id Database column id SqlType(INT)
   *  @param nom Database column nom SqlType(VARCHAR), Length(45,true), Default(None)
   *  @param prenom Database column prenom SqlType(VARCHAR), Length(45,true), Default(None) */
  case class UsersRow(id: Int, nom: Option[String] = None, prenom: Option[String] = None)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Int], <<?[String], <<?[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends Table[UsersRow](_tableTag, "users") {
    def * = (id, nom, prenom) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), nom, prenom).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT) */
    val id: Rep[Int] = column[Int]("id")
    /** Database column nom SqlType(VARCHAR), Length(45,true), Default(None) */
    val nom: Rep[Option[String]] = column[Option[String]]("nom", O.Length(45,varying=true), O.Default(None))
    /** Database column prenom SqlType(VARCHAR), Length(45,true), Default(None) */
    val prenom: Rep[Option[String]] = column[Option[String]]("prenom", O.Length(45,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
