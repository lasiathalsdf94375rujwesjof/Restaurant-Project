import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage6 {
    private JFrame frame;
    private JTextField balanceField;
    private JTextField amountField;
    private JLabel statusLabel;

    public WelcomePage6(String userId , JFrame mainFrame) {
        // إعداد واجهة المستخدم
        frame = new JFrame("Welcome Page");
        frame.setLayout(new BorderLayout(10, 10));
        Font font = new Font("Arial", Font.BOLD, 16);

        // إضافة عنوان
        JLabel welcomeLabel = new JLabel("Welcome, " + userId, JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(welcomeLabel, BorderLayout.NORTH);

        // محتويات واجهة العمليات
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // تعديل لتتناسب مع المكونات

        // زر عرض الرصيد
        JButton showBalanceButton = new JButton("Show My Balance");
        showBalanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Customer customer = Customer.FindCustomerByName(userId);
                balanceField.setText(String.valueOf(customer.getBalance()));
            }
        });
        panel.add(showBalanceButton);

        // زر سحب المال
        JButton withdrawButton = new JButton("Withdraw Money");
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    Customer customer = Customer.FindCustomerByName(userId);
                    if (customer.getBalance() >= amount) {

                        Money.WithDrawSaveFile(userId, amount);
                        balanceField.setText(String.valueOf(customer.getBalance()));
                        statusLabel.setText("Successfully withdrew " + amount);
                    } else {
                        statusLabel.setText("Insufficient balance.");
                    }
                } catch (NumberFormatException ex) {
                    statusLabel.setText("Please enter a valid amount.");
                }
            }
        });
        panel.add(withdrawButton);

        // زر إيداع المال
        JButton depositButton = new JButton("Deposit Money");
        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());

                    Money.DepositSaveFile(userId, amount);
                    Customer customer = Customer.FindCustomerByName(userId);
                    balanceField.setText(String.valueOf(customer.getBalance()));
                    statusLabel.setText("Successfully deposited " + amount);
                } catch (NumberFormatException ex) {
                    statusLabel.setText("Please enter a valid amount.");
                }
            }
        });
        panel.add(depositButton);

        // إدخال المبلغ
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        JLabel amountLabel = new JLabel("Enter Amount: ");
        amountField = new JTextField(10);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        panel.add(inputPanel);

        // إضافة خانة الرصيد
        JPanel balancePanel = new JPanel();
        balancePanel.setLayout(new FlowLayout());
        JLabel balanceLabel = new JLabel("Balance: ");
        balanceField = new JTextField(10);
        balanceField.setEditable(false); // الرصيد لا يمكن تعديله من قبل المستخدم
        balancePanel.add(balanceLabel);
        balancePanel.add(balanceField);
        panel.add(balancePanel);

        // إضافة الإشعارات (Status)
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setForeground(Color.RED);


        JButton closeButton = new JButton("Back to Main Page");
        closeButton.setFont(font);
        closeButton.setBackground(new Color(220, 53, 69)); // لون الزر
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> {
            frame.dispose();
            mainFrame.setVisible(true); // إعادة عرض الصفحة الرئيسية
        });


        // إضافة كل شيء للنافذة
        frame.add(panel, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);
        frame.add(closeButton, BorderLayout.SOUTH);
        // إعدادات النافذة
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
