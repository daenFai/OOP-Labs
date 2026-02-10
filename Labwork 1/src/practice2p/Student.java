package practice2p;

public class Student {

    private String studentName;
    private int studentID;
    private int studentYOS;
    private double studentGPA;


    public Student(String name, int id, int yos, double gpa) {
        this.studentName = name;
        this.studentID = id;
        this.studentYOS = yos;
        this.studentGPA = gpa;
    }

    public void displayDetails() {
        System.out.println("Name - " + studentName);
        System.out.println("ID - " + studentID);
        System.out.println("Year of Study - " + studentYOS);
    }
    public void incrementID() {
        studentID+=1;
    }

    public double getStudentGPA() {
        return studentGPA;
    }

    @Override
    public String toString() {
        return studentName + " (GPA: " + studentGPA + ")";
    }


//    public static void main(String[] args) {
//        Student stud = new Student("Blabla", 1, 2024, 3);
//
//        stud.displayDetails();
//        stud.incrementID();
//        stud.displayDetails();
//    }

}
