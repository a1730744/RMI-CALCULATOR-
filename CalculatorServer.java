// Faisal  a1730744
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/*
Linux
Firewall: Ensure the firewall allows traffic on port 1099.
You can use iptables or firewalld to open the port.

sudo iptables -A INPUT -p tcp --dport 1099 -j ACCEPT
sudo iptables-save
SELinux: If SELinux is enabled, configure it to allow RMI traffic.


sudo setsebool -P java_can_network_connect 1
Windows
Firewall: Allow port 1099 through the Windows Firewall.

Open Control Panel.
Go to "System and Security" > "Windows Defender Firewall" > "Advanced settings".
Create a new inbound rule to allow TCP traffic on port 1099.
Run as Administrator: Ensure the command prompt or terminal is run as an administrator to avoid permission issues.

macOS
Firewall: Allow port 1099 through the macOS firewall.

Go to "System Preferences" > "Security & Privacy" > "Firewall".
Click the lock to make changes and enter your password.
Click "Firewall Options..." and add a rule to allow incoming connections for the Java application.
Network Security: Ensure that the macOS security settings do not block Java applications.

Running the Server and Client
Compile the Java Classes:



javac MathOperationsInterface.java
MathOperations.java MathOperationsServer.java MathOperationsClient.java
Run the Server:

java MathOperationsServer
Run the Client:

java MathOperationsClient
 */

public class CalculatorServer {
    public static void main(String[] args) {
        try {
            // Start RMI registry programmatically on port 1099
            LocateRegistry.createRegistry(1099);

            // Create and export a Calculator object
            Calculator calculator = new CalculatorImplementation();

            // Register the object with the RMI registry
            Naming.rebind("CalculatorService", calculator);
           // Naming.rebind("rmi://localhost/CalculatorService", calculator);

            System.out.println("Calculator server is ready.");
        } catch (RemoteException e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
