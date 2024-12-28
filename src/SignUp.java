import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp {
    private JFrame frame;
    private JPanel panel;
    private CardLayout cardLayout;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField accountField;
    private JTextField balanceField;

    private IDandPasswords iDandPasswords = new IDandPasswords();

    public SignUp(JFrame frame) {
        createAndShowGUI(frame);
    }

    private void createAndShowGUI(JFrame ff) {
        frame = new JFrame("Customer Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        Font font = new Font("Arial", Font.BOLD, 16);


        cardLayout = new CardLayout();
        panel = new JPanel(cardLayout);


        JButton closeButton = new JButton("Back to Main Page");
        closeButton.setFont(font);
        closeButton.setBackground(new Color(220, 53, 69)); // لون الزر
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> {
            frame.dispose();
            ff.setVisible(true); // إعادة عرض الصفحة الرئيسية
        });
        frame.add(closeButton, BorderLayout.SOUTH);
        createInputForm();
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
}


    private void createInputForm() {

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        JLabel usernameLabel = new JLabel("New Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("New Password:");
        passwordField = new JPasswordField();

        JLabel accountLabel = new JLabel("Customer Account:");
        accountField = new JTextField();

        JLabel balanceLabel = new JLabel("Initial Balance:");
        balanceField = new JTextField();

        JButton submitButton = new JButton("Sign Up");
        JButton resetButton = new JButton("Reset");

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(accountLabel);
        formPanel.add(accountField);
        formPanel.add(balanceLabel);
        formPanel.add(balanceField);
        formPanel.add(submitButton);
        formPanel.add(resetButton);
        panel.add(formPanel, "Form");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = usernameField.getText().trim();
                String newPassword = new String(passwordField.getPassword()).trim();
                String customerAccount = accountField.getText().trim();
                String balanceText = balanceField.getText().trim();
                double balance;

                if (newUsername.isEmpty() || newPassword.isEmpty() || customerAccount.isEmpty() || balanceText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (iDandPasswords.getLoginInfo().containsKey(newUsername)) {
                    JOptionPane.showMessageDialog(frame, "Username already exists. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (Customer.IsCustomerExist(customerAccount)) {
                    JOptionPane.showMessageDialog(frame, "Customer account already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    balance = Double.parseDouble(balanceText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid balance!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (balance <= 9999) {
                    JOptionPane.showMessageDialog(frame, "Balance must be at least 10,000!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                iDandPasswords.newUsername = newUsername;
                iDandPasswords.newPassword = newPassword;
               iDandPasswords.addNewUserEd();
                Customer customer = new Customer(Customer.Mode.NewMode, newUsername, customerAccount, newPassword, balance);
                Customer.SaveResult saveResult = customer.Save();

                if (saveResult == Customer.SaveResult.SavedSuccessfully) {
                    JOptionPane.showMessageDialog(frame, "Customer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to add customer!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");
                accountField.setText("");
                balanceField.setText("");
            }
        });}}


/*
ibrahim:32434
Ahmad:43563
jamel:324
nada:32344
adel:234
nadia:3242
lona:343
 */