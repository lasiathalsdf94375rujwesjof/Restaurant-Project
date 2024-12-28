import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WelcomePage2 {

    JFrame frame = new JFrame();
    JLabel welcomeLabel = new JLabel("Hello!");

    WelcomePage2(JFrame mainFrame) { // تمرير نافذة الصفحة الرئيسية
        frame = new JFrame("Customer List");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        // Set background color for the main frame
        frame.getContentPane().setBackground(new Color(245, 245, 245));

        // Column names for the table
        String[] columnNames = {"Customer Name", "Customer Pin Code", "Customer Account", "Customer Balance"};

        // Create table model and populate it with customer data
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        refreshTableData(tableModel);

        // Create JTable and customize its appearance
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setBackground(new Color(230, 230, 250));
        table.setForeground(new Color(50, 50, 50));
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Add table to JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(245, 245, 245)); // Table background
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(230, 230, 250)); // Button panel background

        // Create buttons for Add, Update, Delete, and Back actions
        JButton addButton = new JButton("Add New Customer");
        JButton updateButton = new JButton("Update Customer");
        JButton deleteButton = new JButton("Delete Customer");
        JButton backButton = new JButton("Back"); // زر الرجوع

        // Customize button appearance
        customizeButton(addButton);
        customizeButton(updateButton);
        customizeButton(deleteButton);
        customizeButton(backButton);

        // Add buttons to the panel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton); // إضافة زر الرجوع
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add button action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReadNewCustomer();
                refreshTableData(tableModel);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateCustomer();
                refreshTableData(tableModel);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCustomer();
                refreshTableData(tableModel);
            }
        });

        // Back button action listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // إغلاق النافذة الحالية
                mainFrame.setVisible(true); // عرض نافذة الصفحة الرئيسية
            }
        });

        // Display the frame
        frame.setVisible(true);
    }

    private static void customizeButton(JButton button) {
        button.setBackground(new Color(135, 206, 250));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(150, 40));
    }

    private static void refreshTableData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Clear the table

        List<Customer> customers = Customer.GetCutomersList();
        for (Customer customer : customers) {
            Object[] rowData = {
                    customer.getCustomerName(),
                    customer.getCustomerPinCode(),
                    customer.getCustomerAccount(),
                    customer.getBalance()
            };
            tableModel.addRow(rowData); // Add rows to the table
        }
    }

    private static void ReadNewCustomer() {
        String customerAccount;
        do {
            customerAccount = JOptionPane.showInputDialog("Enter the Customer Account of Your New Customer:");
        } while (Customer.IsCustomerExist(customerAccount));

        Customer customer = Customer.GetAddNewCustomerObject(customerAccount);
        updateCustomerInfo(customer);

        Customer.SaveResult saveResult = customer.Save();
        if (saveResult == Customer.SaveResult.SavedSuccessfully) {
            JOptionPane.showMessageDialog(null, "Customer Added Successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to Add Customer.");
        }
    }

    private static void UpdateCustomer() {
        String customerAccount;
        do {
            customerAccount = JOptionPane.showInputDialog("Enter the Customer Account of the Customer to Update:");
        } while (!Customer.IsCustomerExist(customerAccount));

        Customer customer = Customer.FindCustomer(customerAccount);

        customer.delete();
        customer = Customer.GetAddNewCustomerObject(customerAccount);
        updateCustomerInfo(customer);


        Customer.SaveResult saveResult = customer.Save();
        if (saveResult == Customer.SaveResult.SavedSuccessfully) {
            JOptionPane.showMessageDialog(null, "Customer Updated Successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to Update Customer.");
        }
    }

    private static void deleteCustomer() {
        String customerAccount;
        do {
            customerAccount = JOptionPane.showInputDialog("Enter the Customer Account of the Customer to Delete:");
        } while (!Customer.IsCustomerExist(customerAccount));

        Customer customer = Customer.FindCustomer(customerAccount);
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this customer?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (customer.delete()) {
                JOptionPane.showMessageDialog(null, "Customer Deleted Successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to Delete Customer.");
            }
        }
    }

    private static void updateCustomerInfo(Customer customer) {
        String customerName = JOptionPane.showInputDialog("Enter the Customer Name:");
        customer.setCustomerName(customerName);

        String pinCode = JOptionPane.showInputDialog("Enter the Customer Pin Code:");
        customer.setCustomerPinCode(pinCode);

        String balance = JOptionPane.showInputDialog("Enter the Customer Balance:");
        customer.setBalance(Double.parseDouble(balance));
    }
}
