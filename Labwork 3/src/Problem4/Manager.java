package Problem4;

import java.util.Vector;

public class Manager extends Employee {

    private Vector<Employee> team = new Vector<>();
    private double bonus;

    public Manager() {}

    public Manager(String name, double salary, java.util.Date hireDate, String insuranceNumber, double bonus) {
        super(name, salary, hireDate, insuranceNumber);
        this.bonus = bonus;
    }

    public void addEmployee(Employee e) {
        team.add(e);
    }

    public double getBonus() { return bonus; }
    public void setBonus(double bonus) { this.bonus = bonus; }

    @Override
    public String toString() {
        return super.toString() +
                ", bonus=" + bonus +
                ", teamSize=" + team.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manager)) return false;

        Manager m = (Manager) o;

        return super.equals(m) &&
                bonus == m.bonus &&
                team.equals(m.team);
    }

    @Override
    public int compareTo(Employee e) {
        int salaryCompare = Double.compare(this.getSalary(), e.getSalary());

        if (salaryCompare != 0) return salaryCompare;

        if (e instanceof Manager) {
            Manager m = (Manager) e;
            return Double.compare(this.bonus, m.bonus);
        }

        return salaryCompare;
    }

    @Override
    public Manager clone() {
        Manager copy = (Manager) super.clone();
        copy.team = new Vector<>(team); // shallow copy of list
        return copy;
    }
}
