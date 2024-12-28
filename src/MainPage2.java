import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage2 {
    JFrame frame;

    public MainPage2(String UserId , JFrame framme) {
        frame = new JFrame("Main Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3, 1)); // تحديث الشبكة لتتسع 3 صفوف
        Font font = new Font("Arial", Font.BOLD, 16);

        JButton OrderMealButton = new JButton("Order Meal");
        JButton ATMButton = new JButton("ATM ");


        // Action for Manage Meals
        OrderMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close main page
                new WelcomePage5(UserId,frame); // Open Manage Meals page
            }
        });

        // Action for Manage Customers
        ATMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close main page
                new WelcomePage6(UserId,frame); // Open Manage Customers page
            }
        });
        JButton closeButton = new JButton("Back");
        closeButton.setFont(font);

        closeButton.setBackground(new Color(220, 53, 69)); // لون الزر
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> {
            frame.dispose();
            framme.setVisible(true); // إعادة عرض الصفحة الرئيسية
        });

        // إضافة العناصر إلى الإطار




        frame.add(OrderMealButton);
        frame.add(ATMButton);
        frame.add(closeButton);
        frame.add(closeButton, BorderLayout.SOUTH);


        frame.setVisible(true);
    }
}