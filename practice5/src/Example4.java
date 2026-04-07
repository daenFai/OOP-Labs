import java.util.*;

public class Example4
{
    public static void main(String[] args)
    {
//        double                 leftOperand, result, rightOperand;
//        String                 leftString, operator, rightString;
//        StringTokenizer        tokenizer;
//        Scanner in = new Scanner(System.in);
//
//        tokenizer = new StringTokenizer(in.nextLine(), "+", true);

//        try
//        {
//            leftString   = tokenizer.nextToken();
//            operator     = tokenizer.nextToken();
//            rightString  = tokenizer.nextToken();
//
//            leftOperand  = Double.parseDouble(leftString);
//            rightOperand = Double.parseDouble(rightString);
//
//            if (operator.equals("+"))
//                result = leftOperand + rightOperand;
//            else
//                result = 0.0;
//
//            System.out.println("Result: " + result);
//        }
//        catch (NoSuchElementException nsee)
//        {
//            System.out.println("Invalid syntax");
//        }
//        catch (NumberFormatException nfe)
//        {
//            System.out.println("One or more operands is not a number");
//        }


        //new version
        Scanner in = new Scanner(System.in);
        System.out.println("Expressions separated by spaces: ");
        String input = in.nextLine();

        String[] expressions = input.split(" ");

        for(String expression : expressions){
            StringTokenizer st = new StringTokenizer(expression, "+-*/", true);

            try{
                String left = st.nextToken();
                String operator = st.nextToken();
                String right = st.nextToken();

                double leftOperand = 0, rightOperand = 0;
                boolean validOperands = true;

                try{
                    leftOperand = Double.parseDouble(left);
                }
                catch(NumberFormatException e){
                    System.out.println("Invalid left operand: " + left);
                    validOperands = false;
                }

                try{
                    rightOperand = Double.parseDouble(right);
                }
                catch (NumberFormatException e){
                    System.out.println("Invalid right operand: " + right);
                    validOperands = false;
                }

                if(validOperands){
                    double result = 0;
                    switch(operator){
                        case "+": result = leftOperand + rightOperand; break;
                        case "-": result = leftOperand - rightOperand; break;
                        case "*": result = leftOperand * rightOperand; break;
                        case "/":
                            if(rightOperand == 0){
                                System.out.println("Division by zero");
                                continue;
                            }
                            result = leftOperand / rightOperand; break;
                        default:
                            System.out.println("Unknown operator: " + operator);
                            continue;
                    }
                    System.out.println(expression + " = " + result);
                }
            }
            catch(NoSuchElementException e){
                System.out.println("Invalid expression: " + expression);
            }
        }


    }
}

// 1. What functionality does a StringTokenizer object provide? Give example --> it divides string to tokens (some parts) by delimiter
                    // e.g: StringTokenizer st = new StringTokenizer("5.3+9.5", "+") --> sout(st.nextToken()) --> "5.3"
                    //                                                               --> sout(st.nextToken()) --> "9.5"
// 1. What are the three formal parameters of the explicit value  constructor in the StringTokenizer class? Give example
                    // e.g: StringTokenizer st = new StringTokenizer("5.3+9.5", "+", true)
                    //      (String, delimiter, boolean returnDelims)
                    //       Outputs: "5.3", "+", "9.5"

// 2. What output is generated? --> 5.3+9.2
                                    //Result: 14.5
// 3. What output is generated? --> 5.3+
                                    //Invalid syntax
// 3. Why?  In particular, what exception is thrown and why? --> NoSuchElementException, because we are trying to get element which is missing
// 4. What output is generated? --> 5.3+a
                                    //One or more operands is not a number
// 4. Why?  In particular, what exception is thrown and why? --> NumberFormatException, because we cannot parse "a" to the double

