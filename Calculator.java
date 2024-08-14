// Faisal  a1730744
/*
Java interface, Calculator, seems to define a Remote Method Invocation (RMI) service for a stack-based calculator. Here's an overview of each method in the interface:

void pushValue(int val) throws RemoteException;

Pushes an integer value onto the calculator's stack.
void pushOperation(String operator) throws RemoteException;

Pushes an operation onto the stack. The operation could be something like "+", "-", "*", or "/". The implementation would handle applying the operation to the values on the stack.
int pop() throws RemoteException;

Pops the top value off the stack and returns it.
boolean isEmpty() throws RemoteException;

Checks if the stack is empty and returns a boolean value.

 */
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
    void pushValue(int val) throws RemoteException;
    void pushOperation(String operator) throws RemoteException;
    int pop() throws RemoteException;
    boolean isEmpty() throws RemoteException;
}
