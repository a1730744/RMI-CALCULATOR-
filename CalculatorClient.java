// Faisal  a1730744

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.io.IOException;
import java.rmi.Naming;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class CalculatorClient extends JFrame {
    private JTextField valueField1, valueField2; // Text fields for user input
    private JTextArea outputArea; // Area to display output from server
    private JLabel assignmentInfoLabel; // Label to display assignment info
    private JButton pushValuesButton, minButton, maxButton, lcmButton, gcdButton, popButton, isEmptyButton, assignmentInfoButton;
    private Calculator calculator; // RMI Calculator interface reference
    private Timer blinkTimer; // Timer for blinking assignment info label
    private boolean isBlinking; // Flag to track blinking state of assignment info label

    public CalculatorClient(String serverIp) {
        setTitle("RMI Calculator Client Assignment 1 2024 Distributed System");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the application icon
        try {
            Image icon = ImageIO.read(getClass().getResource("/adelaideuni.png"));
            // Resize the image to a larger size
            Image resizedIcon = icon.getScaledInstance(128, 128, Image.SCALE_SMOOTH); // Adjust size as needed
            setIconImage(resizedIcon);
        } catch (IOException e) {
            System.err.println("Icon file not found.");
            e.printStackTrace();
        }

        // Initialize components
        valueField1 = new JTextField(5);
        valueField2 = new JTextField(5);
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        assignmentInfoLabel = new JLabel("Student ID: A1730744 | Faisal Sabeeh Falih Al-sigar");
        assignmentInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        assignmentInfoLabel.setForeground(Color.BLUE);
        assignmentInfoLabel.setVisible(false);  // Initially not visible

        pushValuesButton = new JButton("Push Values");
        minButton = new JButton("min");
        maxButton = new JButton("max");
        lcmButton = new JButton("lcm");
        gcdButton = new JButton("gcd");
        popButton = new JButton("Pop");
        isEmptyButton = new JButton("Is Empty?");
        assignmentInfoButton = new JButton("Assignment1");

        // Add components to the frame
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Value 1:"));
        inputPanel.add(valueField1);
        inputPanel.add(new JLabel("Value 2:"));
        inputPanel.add(valueField2);
        inputPanel.add(pushValuesButton);
        inputPanel.add(minButton);
        inputPanel.add(maxButton);
        inputPanel.add(lcmButton);
        inputPanel.add(gcdButton);
        inputPanel.add(popButton);
        inputPanel.add(isEmptyButton);
        inputPanel.add(assignmentInfoButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(inputPanel);
        mainPanel.add(scrollPane);
        mainPanel.add(assignmentInfoLabel);

        add(mainPanel);

        // Set up RMI (Remote Method Invocation) connection
        try {
            // Use the provided IP address to look up the server
            calculator = (Calculator) Naming.lookup("rmi://" + serverIp + "/CalculatorService");
            outputArea.append("Connected to RMI server at " + serverIp + "\n");
            outputArea.append("Make sure to include the RMI package when compiling.\n" +
                    "javac *.java\n" +
                    "Start the RMI Registry:\n" +
                    "rmiregistry 1099 &\n" +
                    "Start the CalculatorServer:\n" +
                    "java CalculatorServer\n" +
                    "Run the Client:\n" +
                    "java CalculatorClient\n");
        } catch (Exception e) {
            outputArea.append("Failed to connect to RMI server at " + serverIp + "\n");
            e.printStackTrace();
        }

        // Button actions
        pushValuesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int value1 = Integer.parseInt(valueField1.getText());
                    int value2 = Integer.parseInt(valueField2.getText());
                    calculator.pushValue(value1);
                    calculator.pushValue(value2);
                    SwingUtilities.invokeLater(() -> outputArea.append("Pushed values: " + value1 + ", " + value2 + "\n"));
                } catch (NumberFormatException ex) {
                    SwingUtilities.invokeLater(() -> outputArea.append("Invalid input. Please enter integers.\n"));
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> outputArea.append("Error pushing values.\n"));
                    ex.printStackTrace();
                }
            }
        });

        minButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyOperation("min");
            }
        });

        maxButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyOperation("max");
            }
        });

        lcmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyOperation("lcm");
            }
        });

        gcdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyOperation("gcd");
            }
        });

        popButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = calculator.pop();
                    SwingUtilities.invokeLater(() -> outputArea.append("Popped value: " + value + "\n"));
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> outputArea.append("Error popping value.\n"));
                    ex.printStackTrace();
                }
            }
        });

        isEmptyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean empty = calculator.isEmpty();
                    SwingUtilities.invokeLater(() -> outputArea.append("Is stack empty? " + (empty ? "Yes" : "No") + "\n"));
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> outputArea.append("Error checking if stack is empty.\n"));
                    ex.printStackTrace();
                }
            }
        });

        assignmentInfoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleAssignmentInfo();
            }
        });
    }

    private void applyOperation(String operation) {
        try {
            calculator.pushOperation(operation);
            SwingUtilities.invokeLater(() -> outputArea.append("Performed operation: " + operation + "\n"));
        } catch (Exception ex) {
            SwingUtilities.invokeLater(() -> outputArea.append("Error performing operation.\n"));
            ex.printStackTrace();
        }
    }

    private void toggleAssignmentInfo() {
        if (isBlinking) {
            blinkTimer.stop();
            assignmentInfoLabel.setVisible(false);
            isBlinking = false;
        } else {
            isBlinking = true;
            assignmentInfoLabel.setVisible(true);
            blinkTimer = new Timer(5000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    assignmentInfoLabel.setVisible(!assignmentInfoLabel.isVisible());
                }
            });
            blinkTimer.start();
        }
    }

    public static void main(String[] args) {
        // Prompt for the server IP address
        String serverIp = JOptionPane.showInputDialog("Enter the server IP address:");
        if (serverIp != null && !serverIp.trim().isEmpty()) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new CalculatorClient(serverIp.trim()).setVisible(true);
                }
            });
        } else {
            System.err.println("Server IP address is required For Connect to the server.");
        }
    }
}
