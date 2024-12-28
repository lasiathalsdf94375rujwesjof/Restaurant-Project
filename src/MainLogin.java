import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLogin {
    JFrame frame;

    public MainLogin(JFrame framee) {
        frame = new JFrame("Main Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3, 1)); // تحديث الشبكة لتتسع 3 صفوف
        Font font = new Font("Arial", Font.BOLD, 16);


        JButton LoginButton = new JButton("LogIn");
        JButton SignInButton = new JButton("Sign In");
        JButton GoBack = new JButton("Back");

        IDandPasswords iDandPasswords = new IDandPasswords();
        // Action for Manage Meals
        SignInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close main page
                new SignUp(frame); // Open Manage Meals page
            }
        });

        // Action for Manage Customers
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close main page
                new LoginPage(iDandPasswords.getLoginInfo() , frame); // Open Manage Meals page
            }
        });



        GoBack.setFont(font);
        GoBack.setBackground(new Color(220, 53, 66)); // لون الزر
        GoBack.setForeground(Color.WHITE);
        GoBack.setFocusPainted(false);
        GoBack.addActionListener(e -> {
            frame.dispose();
            framee.setVisible(true); // إعادة عرض الصفحة الرئيسية
        });

        // إضافة العناصر إلى الإطار



        frame.add(LoginButton);
        frame.add(SignInButton);
        GoBack.setBounds(20 , 100 , 40 , 79);
        frame.add(GoBack);


        frame.setVisible(true);
    }
}