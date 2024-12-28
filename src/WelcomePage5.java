import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class WelcomePage5 {

    volatile static boolean isCancellationAllowed = true;
    WelcomePage5(String userId , JFrame mainFrame){
        JFrame frame = new JFrame("Meal Ordering System");
        Font font = new Font("Arial", Font.BOLD, 16);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        // Table for meals
        String[] columnNames = {"Meal Name", "Meal Ingredients", "Meal Number", "Meal Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        // Populate table with meals data
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

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel for input and actions
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblMealNumber = new JLabel("Enter Meal Number:");
        JTextField txtMealNumber = new JTextField();

        JLabel lblMealQuantity = new JLabel("Enter Quantity:");
        JTextField txtMealQuantity = new JTextField();

        JLabel lblSpecialOrder = new JLabel("Special Order (Y/N):");
        JTextField txtSpecialOrder = new JTextField();

        JLabel lblTip = new JLabel("Tip Amount:");
        JTextField txtTip = new JTextField();

        JLabel lblOrderType = new JLabel("Order Type (1: Inside, 2: Delivery):");
        JTextField txtOrderType = new JTextField();

        inputPanel.add(lblMealNumber);
        inputPanel.add(txtMealNumber);
        inputPanel.add(lblMealQuantity);
        inputPanel.add(txtMealQuantity);
        inputPanel.add(lblSpecialOrder);
        inputPanel.add(txtSpecialOrder);
        inputPanel.add(lblTip);
        inputPanel.add(txtTip);
        inputPanel.add(lblOrderType);
        inputPanel.add(txtOrderType);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Panel for actions
        JPanel actionPanel = new JPanel();
        JButton btnSubmitOrder = new JButton("Submit Order");
        JButton btnCancelOrder = new JButton("Cancel Order");

        actionPanel.add(btnSubmitOrder);
        actionPanel.add(btnCancelOrder);
        frame.add(actionPanel, BorderLayout.SOUTH);

        // Action listener for submitting the order
        btnSubmitOrder.addActionListener(e -> {
            try {
                String mealNumber = txtMealNumber.getText().trim();
                String mealQuantityStr = txtMealQuantity.getText().trim();
                String specialOrder = txtSpecialOrder.getText().trim().toUpperCase();
                String tipStr = txtTip.getText().trim();
                String orderTypeStr = txtOrderType.getText().trim();
                String FileNameRelatedToMeals = "Meals.txt";
                if (mealNumber.isEmpty() || mealQuantityStr.isEmpty() || tipStr.isEmpty() || orderTypeStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!((orderTypeStr.equals("1")) || (orderTypeStr.equals("2") ))) {
                    JOptionPane.showMessageDialog(frame, "Please Order Type Should Be 1 Or 2 Only!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int num = Integer.parseInt(mealNumber);
                if (!(num > 0 && num <= Meal.HowManyMealsExists( FileNameRelatedToMeals) )) {
                    JOptionPane.showMessageDialog(frame, "Invalid Meal Number Please Enter Meal Number From The List !", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }



                int quantity = Integer.parseInt(mealQuantityStr);
                double tip = Double.parseDouble(tipStr);
                int orderType = Integer.parseInt(orderTypeStr);

                Meal meal = Meal.FindMeal(mealNumber);
                if (meal == null) {
                    JOptionPane.showMessageDialog(frame, "Invalid Meal Number!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double pricePerMeal = meal.get_MealPrice();
                double specialOrderCost = specialOrder.equals("Y") ? quantity * 2000 : 0;
                double totalPrice = (quantity * pricePerMeal) + specialOrderCost + tip;

                String orderTypeString = (orderType == 1) ? "Inside Restaurant" : "Delivery";

                // Retrieve customer information to check balance
                Customer customer = Customer.FindCustomerByName(userId);
                if (customer == null) {
                    JOptionPane.showMessageDialog(frame, "Error: Customer not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create order object but delay saving until payment is completed
                CuOrder order = new CuOrder(
                        CuOrder.Mode.NewMode,
                        userId,
                        meal.get_MealName(),
                        quantity,
                        totalPrice,
                        orderTypeString
                );

                JOptionPane.showMessageDialog(frame, "Processing your order. Please wait...");

                // Perform processing in a separate thread
                new SwingWorker<Void, Void>() {
                    private boolean paymentCompleted = false; // Flag to track if payment was successful

                    @Override
                    protected Void doInBackground() throws Exception {
                        if (orderType == 1) { // Inside Restaurant
                            for (int i = 0; i < 5; i++) {
                                Thread.sleep(1000);
                                System.out.print("\rPreparing" + ".".repeat(i % 3 + 1) + "   ");
                            }
                            System.out.println("\nMeal has been prepared.");

                            // Payment method selection
                            while (!paymentCompleted) { // Loop until payment is completed or cancelled
                                String[] options = {"Cash", "Credit Card"};
                                int paymentChoice = JOptionPane.showOptionDialog(frame,
                                        "How would you like to pay?",
                                        "Payment Method",
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.INFORMATION_MESSAGE,
                                        null,
                                        options,
                                        options[0]);

                                if (paymentChoice == 1) { // Credit Card Payment
                                    if (customer.getBalance() < totalPrice) {
                                        // Insufficient balance - Show error and retry
                                        JOptionPane.showMessageDialog(frame,
                                                "Your balance is insufficient. Please recharge your account and try again.",
                                                "Insufficient Balance",
                                                JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        Money.WithDrawSaveFile(userId, totalPrice);
                                        JOptionPane.showMessageDialog(frame, "Payment through credit card completed.");
                                        paymentCompleted = true; // Mark payment as completed
                                    }
                                } else if (paymentChoice == 0) { // Cash Payment
                                    JOptionPane.showMessageDialog(frame, "Please pay cash at the cashier.");
                                    paymentCompleted = true; // Mark payment as completed
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Payment cancelled. Order not saved.");
                                    break; // Exit the payment loop
                                }
                            }
                        } else { // Delivery
                            if (customer.getBalance() < totalPrice) {
                                JOptionPane.showMessageDialog(frame,
                                        "You do not have enough balance. Please recharge your account and try again.",
                                        "Insufficient Balance",
                                        JOptionPane.ERROR_MESSAGE);
                                return null; // Stop further processing
                            }

                            for (int i = 0; i < 5; i++) {
                                Thread.sleep(1000);
                                System.out.print("\rDelivering" + ".".repeat(i % 3 + 1) + "   ");
                            }
                            System.out.println("\nMeal has been delivered.");
                            Money.WithDrawSaveFile(userId, totalPrice);
                            paymentCompleted = true; // Mark payment as completed
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
                            if (paymentCompleted) { // Save order only if payment was successful
                                CuOrder.SaveResult saveResult = order.Save();
                                if (saveResult == CuOrder.SaveResult.SavedSuccessfully) {
                                    JOptionPane.showMessageDialog(frame, "Order Submitted Successfully! Total Price: " + totalPrice);
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Failed to Submit Order!", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(frame, "Order was not completed. It has not been saved.", "Order Cancelled", JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }
                    }
                }.execute();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        // Action listener for cancelling the order
        btnCancelOrder.addActionListener(e -> {
            if (isCancellationAllowed) {
                JOptionPane.showMessageDialog(frame, "Order has been canceled successfully!");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Cancellation period has expired!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton closeButton = new JButton("Back to Main Page");
        closeButton.setFont(font);
        closeButton.setBackground(new Color(220, 53, 69)); // لون الزر
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> {
            frame.dispose();
            mainFrame.setVisible(true); // إعادة عرض الصفحة الرئيسية
        });

        // إضافة العناصر إلى الإطار

        frame.add(closeButton, BorderLayout.EAST);
        // Display the frame
        frame.setVisible(true);
    }
    }

