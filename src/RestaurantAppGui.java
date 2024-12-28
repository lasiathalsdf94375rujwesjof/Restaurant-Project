import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestaurantAppGui {
    RestaurantAppGui()
    {
    JFrame frame = new JFrame("Welcome to Our Restaurant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

    // إضافة خلفية
    JLabel background = new JLabel();
        background.setIcon(new ImageIcon("2.jpg")); // تأكد من وضع الصورة في نفس مسار المشروع
        background.setLayout(new BorderLayout());

    // لوحة للكتابة
    JPanel textPanel = new JPanel();
        textPanel.setOpaque(false); // الشفافية للخلفية
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

    // النصوص
    JLabel welcomeLabel = new JLabel("أهلاً بك في مطعمنا", SwingConstants.CENTER);
    //welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel statsLabel = new JLabel("50 ألف مستخدم على برنامجنا", SwingConstants.CENTER);
    //statsLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        statsLabel.setForeground(Color.WHITE);
        statsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel sloganLabel = new JLabel("كلشي فدا كرشي، انشئ حساب واستمتع بطلب أشهى الأطباق", SwingConstants.CENTER);
    //sloganLabel.setFont(new Font("Arial", Font.ITALIC, 30));
        sloganLabel.setForeground(Color.WHITE);
        sloganLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 48));
        statsLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        sloganLabel.setFont(new Font("Tahoma", Font.BOLD, 25));


    // إضافة النصوص إلى اللوحة
        textPanel.add(Box.createVerticalGlue());
        textPanel.add(welcomeLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        textPanel.add(statsLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        textPanel.add(sloganLabel);
        textPanel.add(Box.createVerticalGlue());

    // زر Go
    JButton goButton = new JButton("Go");
        goButton.setFont(new Font("Arial", Font.BOLD, 20));
        goButton.setBackground(new Color(34, 139, 34)); // لون الزر أخضر
        goButton.setForeground(Color.WHITE);
        goButton.setFocusPainted(false);
        goButton.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        goButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        goButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            MainLogin mainLogin = new MainLogin(frame);
        }
    });

    // لوحة الزر
    JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(goButton);

    // إضافة العناصر إلى الخلفية
        background.add(textPanel, BorderLayout.CENTER);
        background.add(buttonPanel, BorderLayout.SOUTH);

    // إضافة الخلفية إلى الإطار
        frame.setContentPane(background);

    // ضبط الإطار وجعله مرئيًا
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
}
}
