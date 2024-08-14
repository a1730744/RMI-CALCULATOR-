//Define a Remote Interface: This interface will declare the methods
// that can be invoked remotely.
//Implement the Remote Interface: Implement this interface in the
// MathOperations
// class.
//Create and Export the Remote Object: Create an instance of the MathOperations
// class and export it using the RMI registry.
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Stack;

public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {
    private Stack<Integer> stack;

    protected CalculatorImplementation() throws RemoteException {
        stack = new Stack<>();
    }

    public void pushValue(int val) throws RemoteException {
        stack.push(val);
    }

    /*

    pushOperation method for handling different operations on a stack looks good,
     but it assumes the existence of the lcm() and gcd() methods that operate on
     the stack. I'll provide an implementation that defines these methods to
     compute the LCM and GCD for all elements in the stack.
     Description of the Method
     The pushOperation method applies a specified operation (min, max, lcm, or gcd)
      on the elements of a stack and then replaces the contents of the stack with
      the result of the operation.
     */
    public void pushOperation(String operator) throws RemoteException {
        if (stack.isEmpty()) return;

        int result;
        switch (operator) {
            case "min":
                result = stack.stream().min(Integer::compareTo).get();
                break;
            case "max":
                result = stack.stream().max(Integer::compareTo).get();
                break;
            case "lcm":
                result = lcm();
                break;
            case "gcd":
                result = gcd();
                break;
            default:
                throw new RemoteException("Invalid operator");
        }
        stack.clear();
        stack.push(result);
    }

    /*

    pop and isEmpty methods for handling stack operations with remote exceptions
    look good. Here’s the full implementation of your MathOperations class,
     including these methods along with the previously defined pushOperation, gcd,
      and lcm methods.

     */

    public int pop() throws RemoteException {
        if (stack.isEmpty()) throw new RemoteException("Stack is empty");
        return stack.pop();
    }

    public boolean isEmpty() throws RemoteException {
        return stack.isEmpty();
    }

    private int gcd() {
        return stack.stream().reduce(this::gcd).get();
    }

    private int lcm() {
        return stack.stream().reduce(1, this::lcm);
    }
    /*
    GCD stands for Greatest Common Divisor. It is the largest positive
      integer that divides two or more integers without leaving a remainder
    The gcd method calculates the greatest common divisor of two integers,
     a and b. The GCD of two numbers is the largest positive integer that
     divides both a and b without leaving a remainder.
     Parameters
     a: The first integer.
       Returns
      The greatest common divisor of a and b.

     */

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /*
    LCM stands for Least Common Multiple. It is the smallest positive integer
    that is divisible by both numbers in question.
    The lcm method calculates the least common multiple (LCM) of two integers,
     a and b. The LCM of two numbers is the smallest positive integer that is
     divisible by both a and b. This method relies on the mathematical
     relationship between the greatest common divisor (GCD) and the LCM, which
     is given by:LCM(𝑎,𝑏)=∣𝑎×𝑏∣GCD(𝑎,𝑏)LCM(a,b)= GCD(a,b)∣a×b∣
    In the method, integer division is used to prevent overflow that might occur
    with large numbers when multiplying a and b directly.
     */

    private int lcm(int a, int b) {
        return a * (b / gcd(a, b));
    }
}
