public class Example2
{
    public static void main(String[] args)
    {
        int         i, ratio;
        int[]       numbers = {100,10,0,5,2,8,0,30};


        for (i = 0; i < numbers.length - 1; i++)
        {
            try {
                ratio = numbers[i] / numbers[i+1];
                System.out.println(numbers[i]+"/"+numbers[i+1]+"="+ratio);
            }
            catch (ArithmeticException ae) {
                System.out.println("Couldn't calculate "+
                        numbers[i]+"/"+numbers[i+1]);
            }
        }


//        i=0;
//        try {
//            for (; i < numbers.length-1; i++)
//            {
//                ratio = numbers[i] / numbers[i+1];
//                System.out.println(numbers[i]+"/"+numbers[i+1]+"="+ratio);
//            }
//        }
//        catch (ArithmeticException ae)
//        {
//            System.out.println("Couldn't calculate "+
//                    numbers[i]+"/"+numbers[i+1]);
//        }

    }
}


// 1. What error was generated? --> java: variable i might not have been initialized
// 2. What error was generated? -->  java: variable i might not have been initialized
// 2. Why is this error generated anyway? --> because the compiler cannot guarantee that variable i is initialized before it is used in the catch block
// 3. What output is generated? --> 100/10=10
                                    //Couldn't calculate 10/0
// 3. Why aren't all of the divisions even attempted? --> because the exception breaks out of the try block and transfers control to the catch block
// What did you change? What has happened? --> try-catch block was moved inside the loop, now each division is handled separately



