import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WelcomePage4 {
    WelcomePage4(JFrame mainframe)
    {
        // إنشاء النافذة الرئيسية
        JFrame frame = new JFrame("Orders.txt");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        Font font = new Font("Arial", Font.BOLD, 16);


        // الحصول على قائمة الطلبات
        List<CuOrder> orders = CuOrder.GetCuOrdersList();

        // إعداد جدول لعرض البيانات
        String[] columnNames = {"Customer Name", "Meal Name", "Number of Meals", "Price of Meals", "Type of Order"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (CuOrder order : orders) {
            Object[] rowData = {
                    order.getCustomerName(),
                    order.getMealName(),
                    order.getNumOfMeals(),
                    order.PriceOfMeals,
                    order.getTypeOfOrder()
            };
            tableModel.addRow(rowData);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // إنشاء أزرار التحكم
        JButton closeButton = new JButton("Back to Main Page");



        // حدث إعادة تعيين


        // حدث حذف الطلب المحدد

        closeButton.setFont(font);
        closeButton.setBackground(new Color(220, 53, 69)); // لون الزر
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> {
            frame.dispose();
            mainframe.setVisible(true); // إعادة عرض الصفحة الرئيسية
        });
        frame.add(closeButton, BorderLayout.SOUTH);

        //frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);


        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
