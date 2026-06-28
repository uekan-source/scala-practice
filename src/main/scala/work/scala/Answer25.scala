package work.scala

object Answer25:
  case class Department(
    id:   Department.Id, // 部署ID
    name: String         // 部署名
  )

  object Department:
    opaque type Id = Long
    object Id:
      def apply(value: Long): Id = value

  case class Employee(
    id:             Employee.Id,             // 社員Id
    name:           String,                  // 社員名
    departmentId:   Department.Id,           // 部署Id
    salary:         Int,                     // 給与
    employmentType: Employee.EmploymentType  // 雇用形態
  )

  object Employee:
    opaque type Id = Long
    object Id:
      def apply(value: Long): Id = value

    enum EmploymentType:
      case FullTime // 正社員
      case Contract // 契約
      case PartTime  // パート

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
        Employee(Employee.Id(1005), "Eve",   Department.Id(2), 55, Employee.EmploymentType.PartTime)
      )

    println(employeesById(employees))
    println(averageSalary(employees))
    println(serchDepartmentNotEmployees(employees, departments))

  def employeesById(employees: Seq[Employee]): Map[Department.Id, Seq[Employee]] =
    employees
      .groupBy(_.departmentId)

  def averageSalary(employees: Seq[Employee]): Map[Department.Id, Int] =
    employeesById(employees)
      .view.mapValues(emp => emp.map(_.salary).sum / emp.size )
      .toMap

  def serchDepartmentNotEmployees(employees: Seq[Employee], departments: Seq[Department]): Set[String] =
    val empIds =
      employees
        .map(_.departmentId)
        .toSet

    departments
      .filterNot(emp => empIds.contains(emp.id))
      .map(_.name)
      .toSet












































































