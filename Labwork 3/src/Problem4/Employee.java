package Problem4;

import java.util.Date;

public class Employee extends Person implements Comparable<Employee>, Cloneable {

    private double salary;
    private Date hireDate;
    private String insuranceNumber;

    public Employee() {}

    public Employee(String name, double salary, Date hireDate, String insuranceNumber) {
        super(name);
        this.salary = salary;
        this.hireDate = hireDate;
        this.insuranceNumber = insuranceNumber;
    }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public Date getHireDate() { return hireDate; }
    public void setHireDate(Date hireDate) { this.hireDate = hireDate; }

    public String getInsuranceNumber() { return insuranceNumber; }
    public void setInsuranceNumber(String insuranceNumber) { this.insuranceNumber = insuranceNumber; }

    @Override
    public String toString() {
        return "Employee{" + "name=" + name + ", salary=" + salary + ", hireDate=" + hireDate + ", insuranceNumber='" + insuranceNumber +'}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee e = (Employee) o;

        return salary == e.salary &&
                name.equals(e.name) &&
                hireDate.equals(e.hireDate) &&
                insuranceNumber.equals(e.insuranceNumber);
    }

    @Override
    public int compareTo(Employee e) {
        return Double.compare(this.salary, e.salary);
    }

    @Override
    public Employee clone() {
        try {
            Employee copy = (Employee) super.clone();
            copy.hireDate = (Date) hireDate.clone();
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}