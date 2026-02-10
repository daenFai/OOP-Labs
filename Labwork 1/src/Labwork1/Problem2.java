package Labwork1;

public class Problem2 {
    private static int nextID = 1;
    private final int accountId;

    private double balance;
    private String accountType;

    {
        accountId = nextID++;
    }

    public Problem2(String type) {
        this(type, 0.0);
    }

    public Problem2(String type, double balance) {
        this.accountType = type;
        this.balance = balance;
    }

    public void deposit(double value){
        balance += value;
    }

    public void deposit(double value, String comment){
        balance += value;
        System.out.println("Comment: " + comment);
    }

    public int getAccountId() {
        return accountId;
    }

    public static int getNextId(){
        return nextID;
    }

    public String toString(){
        return "Account #" + accountId + "\nBalance: " + balance + "\nAccount Type: " + accountType;
    }

    public static void main(String[] args){
        Problem2 acc1 = new Problem2("Savings");
        Problem2 acc2 = new Problem2("Checking", 100);

        acc1.deposit(50);
        System.out.println(acc1);
        System.out.println();

        acc2.deposit(30, "Monthly bonus");
        System.out.println(acc2);

        System.out.println();
        System.out.println("Next account ID: " + Problem2.getNextId());
    }
}
