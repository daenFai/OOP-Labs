public class Student {

    private String studentName;
    private int studentID;
    private int studentYearOfStudy;

    public Student(String name, int id, int yos) {
        this.studentName = name;
        this.studentID = id;
        this.studentYearOfStudy = yos;
    }

    public void displayDetails() {
        System.out.println("Name - " + this.studentName);
        System.out.println("ID - " + this.studentID);
        System.out.println("Year of Study - " + this.studentYearOfStudy);
    }

    public void incrementID() {
        studentID+=1;
    }

    public static void main(String[] args) {
        Student stud = new Student("Blabla", 1, 2024);

        stud.displayDetails();
        stud.incrementID();
        stud.displayDetails();
    }
}
