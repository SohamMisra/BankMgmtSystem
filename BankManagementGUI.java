//Java program to create Bank Management System using Swings
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Bank {
    String name;
    long accountNo;
    double balance, amount;

    void create(String name, long accountNo, double initialAmount) {
        this.name = name;
        this.accountNo = accountNo;
        this.balance = initialAmount;
        JOptionPane.showMessageDialog(null, "Account with name " + name + " created successfully!"); //showMessageDialog(Component,Object,String,int)
    }

    void withdraw(double amount) {
        double tempBal = balance;
        balance = balance - amount;

        if (balance < 100) {
            balance = tempBal;
            JOptionPane.showMessageDialog(null, "Minimum limit reached!");
        }
    }

    void deposit(double amount) {
        balance = balance + amount;
    }

    String displayBalance() {
        return "Name: " + name + "\nAccount Number: " + accountNo + "\nBalance: " + balance;
    }
}

class BankManagementGUI extends JFrame {
    private Bank bank;

    public BankManagementGUI() {
        setTitle("Bank Management System");
        setSize(400, 300);  //setting width=300 and height=400
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //Frame is set at centre of screen

        createLoginUI();
        bank = new Bank();
    }

    private void createLoginUI() {
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));  // Creating a panel 
		/* Creating a Grid Layout with 3 rows, 2 columns,10 horizontal gap and 10 vertical gap*/

        JLabel usernameLabel = new JLabel("Username:"); //Creating an instance of JLabel
        JLabel passwordLabel = new JLabel("Password:"); 

        JTextField usernameField = new JTextField();   //Creating an instance of JTextField
        JPasswordField passwordField = new JPasswordField(); //Creating an instance of JPasswordField

        JButton loginButton = new JButton("Login"); //Creating an instance of JButton
        loginButton.setPreferredSize(new Dimension(50, 10)); // Set preferred size for the button

        loginButton.addActionListener(new ActionListener() { //registering action listener to the event
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();

                if (username.equals("nmamit") && String.valueOf(password).equals("9110")) {
                    remove(loginPanel);
                    createMainUI();
                    revalidate();  //cleans dirty pane
                    repaint();     //new pane is created
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }

                // Clear the password field after checking
                passwordField.setText("");
            }
        });

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel()); // Empty label for spacing
        loginPanel.add(loginButton);

        add(loginPanel, BorderLayout.CENTER);
    }

    private void createMainUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton createButton = new JButton("Create Account"); //Creating an instance of JButton
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton exitButton = new JButton("Exit");

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //Terminate from the program on clicking Exit
            }
        });

        panel.add(createButton);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(checkBalanceButton);
        panel.add(exitButton);

        add(panel, BorderLayout.CENTER); //arranging and resizing panel in center
    }

    private void createAccount() {
        JTextField nameField = new JTextField();
        JTextField accountNoField = new JTextField();
        JTextField initialAmountField = new JTextField();

        Object[] message = {        //serializable object in Java
                "Name:", nameField,
                "Account Number:", accountNoField,
                "Initial Amount:", initialAmountField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Create Account", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            long accountNo = Long.parseLong(accountNoField.getText());
            double initialAmount = Double.parseDouble(initialAmountField.getText());
            bank.create(name, accountNo, initialAmount);
        }
    }

    private void deposit() {
        JTextField amountField = new JTextField();
        Object[] message = {
                "Enter Amount to Deposit:", amountField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Deposit", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            double amount = Double.parseDouble(amountField.getText());
            bank.deposit(amount);
            JOptionPane.showMessageDialog(null, "Deposited: " + amount);
        }
    }

    private void withdraw() {
        JTextField amountField = new JTextField();
        Object[] message = {
                "Enter Amount to Withdraw:", amountField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Withdraw", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            double amount = Double.parseDouble(amountField.getText());
            bank.withdraw(amount);
        }
    }

    private void checkBalance() {
        String balanceInfo = bank.displayBalance();
        JOptionPane.showMessageDialog(null, balanceInfo, "Check Balance", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { //Execution happens at Event Dispatch Thread
            @Override
            public void run() {
                new BankManagementGUI().setVisible(true); //GUI is made visible
            }
        });
    }
}
