public class Example3
{
    public static void main(String[] args)
    {
        int         i;
        int[]       data = {50, 320, 97, 12, 2000};


        try
        {
            for (i=0; i < data.length; i++)
            {
                System.out.println(data[i]);
            }
        }
        catch (ArrayIndexOutOfBoundsException aioobe)
        {
            System.out.println("Done");
        }
    }
}

// outputs all of the values followed by the word "Done" : 50; 320; 97; 12; 2000; "Done"
// What did you change? -->  i < 10 ---> i < data.length



