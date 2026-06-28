package work.scala

object Answer26:
  import scala.concurrent.{Future, Await}
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration.*

  def heavyProcess[A](block: => A): Future[A] =
    Future { Thread.sleep(1000); block }
  
  case class Application(name: String, email: String, phone: String)
  
  val db: Map[Int, Application] = Map(
    1 -> Application("田中", "tanaka@example.com", "090-1111-2222"),
    2 -> Application("佐藤", "sato@example.com", "08033334444"),
  )
  val ok       = Application("鈴木", "suzuki@example.com", "070-5555-6666")  // 正常
  val dupEmail = Application("重複", "tanaka@example.com", "09000000000")    // 既存と同じメール
  val badEmail = Application("無効", "bad-email-no-at",    "09000000000")    // @ がない
  val badPhone = Application("無効", "ok@example.com",     "090-abcd")       // 数字でない

  enum ValidationError:
    case IncorrectEmail
    case IncorrectPhone
    case duppleEmail(email: String)

  def main(args: Array[String]): Unit =
    //println(confirmEmail(ok))
    //println(confirmEmail(badEmail))
    //println(confirmPhone(ok))
    //println(confirmPhone(badPhone))
    //println(Await.result(confirmDupple(ok),       Duration.Inf)) 
    //println(Await.result(confirmDupple(dupEmail), Duration.Inf))
    //println(Await.result(registerId(ok), Duration.Inf))
    println(Await.result(processRegistration(ok),      Duration.Inf))
    println(Await.result(processRegistration(badEmail),Duration.Inf))
    println(Await.result(processRegistration(badPhone),Duration.Inf))
    println(Await.result(processRegistration(dupEmail),Duration.Inf))
  
  def confirmEmail(application: Application): Either[ValidationError, Application] =
    application.email.contains("@") match
      case true  => Right(application)
      case false => Left(ValidationError.IncorrectEmail)

  def confirmPhone(application: Application): Either[ValidationError, Application] =
    application.phone
      .replace("-", "")
      .forall(_.isDigit) match
        case true  => Right(application)
        case false => Left(ValidationError.IncorrectPhone)
     
  def confirmDupple(application: Application): Future[Either[ValidationError, Application]] =
    heavyProcess { db.values.find(_.email == application.email) }
      .map:
        case None      => Right(application)
        case Some(apl) => Left(ValidationError.duppleEmail(apl.email))

  def registerId(application: Application): Future[Either[ValidationError, Int]] =
    heavyProcess { db.keys.maxOption.getOrElse(0) + 1 }
      .map(id => Right(id))

  def processRegistration(application: Application): Future[Either[ValidationError, Int]] = 
    
    val confirmFormat =
      confirmEmail(application) match
        case Left(e)    => Left(e)
        case Right(apl) => confirmPhone(apl)

    confirmFormat match{
        case Left(e)    => Future.successful(Left(e))
        case Right(apl) => confirmDupple(apl)
          .flatMap:
            case Left(e)   => Future.successful(Left(e))
            case Right(id) => registerId(id)
    }






