import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WelcomePage {

    JFrame frame = new JFrame();

    WelcomePage(JFrame mainFrame) {

        // Create the main frame for the application
        frame = new JFrame("كلشي فدا كرشي ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(new BorderLayout());
        ImageIcon ic = new ImageIcon("1.jpg");
        frame.setIconImage(ic.getImage());
        frame.getContentPane().setBackground(Color.BLUE);

        // Create column names for the table
        String[] columnNames = {"Meal Name", "Meal Ingredients", "Meal Number", "Meal Price"};

        // Create table model and populate it with meal data
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        List<Meal> mealsList = Meal.GetMealsList();
        if (mealsList.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No Meals Available In the System!", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Meal meal : mealsList) {
                Object[] rowData = {
                        meal.get_MealName(),
                        meal.get_Ingredients(),
                        meal.get_MealNumberInDataFile(),
                        meal.get_MealPrice()
                };
                tableModel.addRow(rowData);
            }
        }

        // Create a JTable and add it to a JScrollPane
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setGridColor(new Color(0x923456));

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0x123456));
        buttonPanel.setLayout(new FlowLayout());

        // Create buttons for Add, Update, Delete, and Back actions
        JButton addButton = new JButton("Add New Meal");
        JButton updateButton = new JButton("Update Meal");
        JButton deleteButton = new JButton("Delete Meal");
        JButton backButton = new JButton("Back");

        addButton.setBackground(new Color(0x6538));
        deleteButton.setBackground(new Color(0x1754231));
        backButton.setBackground(new Color(0xFF5733)); // Custom color for the Back button
        backButton.setForeground(Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Action for Add New Meal button
        addButton.addActionListener(e -> {
            addNewMeal();
            refreshTable(table, tableModel);
        });

        // Action for Update Meal button
        updateButton.addActionListener(e -> {
            updateMeal();
            refreshTable(table, tableModel);
        });

        // Action for Delete Meal button
        deleteButton.addActionListener(e -> {
            deleteMeal();
            refreshTable(table, tableModel);
        });

        // Action for Back button
        backButton.addActionListener(e -> {
            frame.dispose(); // Close current frame
            mainFrame.setVisible(true); // Show the main frame
        });

        // Display the frame
        frame.setVisible(true);
    }

    private static void refreshTable(JTable table, DefaultTableModel tableModel) {
        List<Meal> mealsList = Meal.GetMealsList();
        tableModel.setRowCount(0);  // Clear the existing rows
        for (Meal meal : mealsList) {
            Object[] rowData = {
                    meal.get_MealName(),
                    meal.get_Ingredients(),
                    meal.get_MealNumberInDataFile(),
                    meal.get_MealPrice()
            };
            tableModel.addRow(rowData);  // Add updated data
        }
    }

    private static void addNewMeal() {
        String mealAccount;
        do {
            mealAccount = JOptionPane.showInputDialog("Enter the Meal Account of Your New Meal: ");
        } while (Meal.IsMealExist(mealAccount));

        Meal meal = Meal.GetAddNewMealObject(mealAccount);
        updateMealInfo(meal);

        Meal.SaveResult saveResult = meal.Save();
        if (saveResult == Meal.SaveResult.SavedSuccessfully) {
            JOptionPane.showMessageDialog(null, "Meal Added Successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to Add Meal.");
        }
    }

    private static void updateMeal() {
        String mealAccount;
        do {
            mealAccount = JOptionPane.showInputDialog("Enter the Meal Account of the Meal to Update: ");
        } while (!Meal.IsMealExist(mealAccount));


        Meal meal = Meal.FindMeal(mealAccount);

        meal.delete();
        meal = Meal.GetAddNewMealObject(mealAccount);
        updateMealInfo(meal);


        Meal.SaveResult saveResult = meal.Save();
        if (saveResult == Meal.SaveResult.SavedSuccessfully) {
            JOptionPane.showMessageDialog(null, "Customer Updated Successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to Update Customer.");
        }
    }

    private static void deleteMeal() {
        String mealAccount;
        do {
            mealAccount = JOptionPane.showInputDialog("Enter the Meal Account of the Meal to Delete: ");
        } while (!Meal.IsMealExist(mealAccount));

        Meal meal = Meal.FindMeal(mealAccount);
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this meal?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (meal.delete()) {
                JOptionPane.showMessageDialog(null, "Meal Deleted Successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to Delete Meal.");
            }
        }
    }

    private static void updateMealInfo(Meal meal) {
        String mealName = JOptionPane.showInputDialog("Enter the Meal Name: ");
        meal.set_MealName(mealName);

        String ingredients = JOptionPane.showInputDialog("Enter the Meal Ingredients: ");
        meal.set_Ingredients(ingredients);

        String price = JOptionPane.showInputDialog("Enter the Meal Price: ");
        meal.set_MealPrice(Double.parseDouble(price));
    }
}
