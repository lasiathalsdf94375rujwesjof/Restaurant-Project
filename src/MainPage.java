import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage {
    JFrame frame;

    public MainPage(JFrame frameee) {
        frame = new JFrame("Main Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 1)); // تحديث الشبكة لتتسع 3 صفوف
        Font font = new Font("Arial", Font.BOLD, 16);

        JButton manageMealsButton = new JButton("Manage Meals");
        JButton manageCustomersButton = new JButton("Manage Customers");
        JButton dailyReportsButton = new JButton("Daily Reports"); // زر جديد للتقارير اليومية
        JButton CurrentOrders = new JButton("Current Orders");


        // Action for Manage Meals
        manageMealsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close main page
                new WelcomePage(frame); // Open Manage Meals page
            }
        });

        // Action for Manage Customers
        manageCustomersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close main page
                new WelcomePage2(frame); // Open Manage Customers page
            }
        });

        // Action for Daily Reports
        dailyReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close main page
                new WelcomePage3(frame); // Open Daily Reports page
            }
        });

        CurrentOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close main page
                new WelcomePage4(frame); // Open Daily Reports page
            }
        });
        JButton closeButton = new JButton("Back");
        closeButton.setFont(font);
        closeButton.setBackground(new Color(220, 53, 69)); // لون الزر
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> {
            frame.dispose();
            frameee.setVisible(true); // إعادة عرض الصفحة الرئيسية
        });

        // إضافة العناصر إلى الإطار

        frame.add(closeButton, BorderLayout.SOUTH);



        frame.add(manageMealsButton);
        frame.add(manageCustomersButton);
        frame.add(dailyReportsButton);
        frame.add(CurrentOrders);
        frame.add(closeButton);

        frame.setVisible(true);
    }
}