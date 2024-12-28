import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class WelcomePage3 {
    JFrame frame;

    WelcomePage3(JFrame parentFrame) {
        frame = new JFrame("Restaurant Daily Reports");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        // خلفية للنافذة
        JLabel background = new JLabel(new ImageIcon("background.jpg")); // استخدم صورة الخلفية
        background.setLayout(new BorderLayout());
        frame.setContentPane(background);

        // لوحة لعرض التقارير
        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(new GridLayout(0, 1, 5, 5));
        reportPanel.setOpaque(false); // لجعل اللوحة شفافة
        reportPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // استدعاء دالة التقارير
        ArrayList<String> regularCustomer;
        String fileName = "Orders.txt";
        String dailyRevenues = Reports.CalculateDailyRevenues(fileName);
        regularCustomer = Reports.getRepeatedNames(Reports.CalculateARegularCustomerOfthRestaurant(fileName));
        String mostOrderedMeal = Reports.getMostFrequentName(Reports.MealsInList(fileName));
        int dailyOrderNum = Reports.CalculateDailyOrdersNumber(fileName);

        // إعداد خط مخصص
        Font font = new Font("Arial", Font.BOLD, 16);
        Color textColor = Color.WHITE;

        // إضافة التقارير إلى اللوحة
        reportPanel.add(createStyledLabel("Daily Revenues: " + dailyRevenues, font, textColor));
        reportPanel.add(createStyledLabel("Most Ordered Meal: " + mostOrderedMeal, font, textColor));
        reportPanel.add(createStyledLabel("Daily Orders Number: " + dailyOrderNum, font, textColor));

        // قائمة العملاء المنتظمين
        reportPanel.add(createStyledLabel("Regular Customers:", font, textColor));
        JList<String> customerList = new JList<>(regularCustomer.toArray(new String[0]));
        customerList.setFont(font);
        customerList.setBackground(new Color(255, 255, 255, 200)); // خلفية شفافة قليلاً
        JScrollPane scrollPane = new JScrollPane(customerList);
        reportPanel.add(scrollPane);

        // زر الإغلاق
        JButton closeButton = new JButton("Back to Main Page");
        closeButton.setFont(font);
        closeButton.setBackground(new Color(220, 53, 69)); // لون الزر
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> {
            frame.dispose();
            parentFrame.setVisible(true); // إعادة عرض الصفحة الرئيسية
        });

        // إضافة العناصر إلى الإطار
        frame.add(reportPanel, BorderLayout.CENTER);
        frame.add(closeButton, BorderLayout.SOUTH);

        // عرض النافذة
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }
}
