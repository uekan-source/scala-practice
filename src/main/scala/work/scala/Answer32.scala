package work.scala

object Answer31:

  /**
   * 部署表クラス
   */
  case class Department(
    id:   Department.Id, // 部署ID
    name: String         // 部署名
  )

  /**
   * 部署オブジェクト
   *
   * opaque typeを用いて顧客Idを指定
   */
  object Department:
    opaque type Id = Long
    object Id:
      def apply(value: Long): Id = value

  /**
   * 社員表クラス
   */
  case class Employee(
    id:             Employee.Id,             // 社員Id
    name:           String,                  // 社員名
    departmentId:   Department.Id,           // 部署Id
    salary:         Int,                     // 給与
    employmentType: Employee.EmploymentType  // 雇用形態
  )

  /**
   * 社員表オブジェクト
   *
   * opaque typeを用いてIdを指定
   * enumを用いて雇用形態を表示
   */
  object Employee:
    opaque type Id = Long
    object Id:
      def apply(value: Long): Id = value

    enum EmploymentType:
      case
        FullTime, // 正社員
        Contract, // 契約
        PartTime  // パート

  /**
   * main関数
   *
   * 各設問の出力結果表示
   * 顧客クラスと注文クラスのデータ保持
   */
  def main(args: Array[String]): Unit =

    val departments: Seq[Department] =
      Seq(
        Department(Department.Id(1), "営業部"),
        Department(Department.Id(2), "開発部"),
        Department(Department.Id(3), "人事部"),
        Department(Department.Id(4), "広報部")
      )

    val employees: Seq[Employee] =
      Seq(
        Employee(Employee.Id(1001), "Alice", Department.Id(2), 50, Employee.EmploymentType.FullTime),
        Employee(Employee.Id(1002), "Bob",   Department.Id(1), 40, Employee.EmploymentType.Contract),
        Employee(Employee.Id(1003), "Carol", Department.Id(2), 60, Employee.EmploymentType.FullTime),
        Employee(Employee.Id(1004), "Dave",  Department.Id(1), 45, Employee.EmploymentType.FullTime),
        Employee(Employee.Id(1005), "Eve",   Department.Id(2), 55, Employee.EmploymentType.PartTime),
      )

    //println(departments)
    //println(employees)
    //println(employeesById(employees))
    println(averageSalarybyId(employees))
    //println(neverEmployeeDepartment(departments, employees))

  /**
   * 問2 部署ごとに社員を振り分ける
   */
  def employeesById(employees: Seq[Employee]): Map[Department.Id, Seq[Employee]] =
    employees
      .groupBy(employee => employee.departmentId)

  /**
   * 問3 部署ごとの平均給与を出す
   */
  def averageSalaryById(employees: Seq[Employee]): Map[Department.Id, Int] =
    employees
      .groupMap(_.id)(_.salary)
      .view.mapValues(t => t.salary.sum / t.size)
      .toMap

