import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Customer extends IDandPasswords {
    public Mode mode;
    String CustomerName;
    String CustomerAccount;
    String CustomerPinCode;
    Double Balance;
    private Boolean _MarkedForDelete = false;
    final static String FileNameRelatedToCustomers = "Customers.txt";
    static enum Mode {EmptyMode, UpdateMode, NewMode}
    static  enum SaveResult{SavedFaild  , SavedSuccessfully ,svFaildAccountMailExists  };

    public Customer(Mode mode, String customerName, String customerAccount, String customerPinCode, Double balance) {
        this.mode = mode;
        CustomerName = customerName;
        CustomerAccount = customerAccount;
        CustomerPinCode = customerPinCode;
        Balance = balance;
    }

    public Customer(String customerAccount, Double balance) {
        this.mode = Mode.NewMode;
        IDandPasswords iDandPasswords = new IDandPasswords();
        iDandPasswords.addNewUser();
        CustomerName = iDandPasswords.newUsername;
        CustomerPinCode = iDandPasswords.newPassword;
        CustomerAccount = customerAccount;
        Balance = balance                ;
    }




    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerPinCode() {
        return CustomerPinCode;
    }

    public void setCustomerPinCode(String customerPinCode) {
        CustomerPinCode = customerPinCode;
    }

    public Double getBalance() {
        return Balance;
    }

    public void setBalance(Double balance) {
        Balance = balance;
    }

    public String getCustomerAccount() {
        return CustomerAccount;
    }

    public Boolean get_MarkedForDelete() {
        return _MarkedForDelete;
    }

    public void set_MarkedForDelete(Boolean _MarkedForDelete) {
        this._MarkedForDelete = _MarkedForDelete;
    }

    public static Customer GetEmptyCustomerObject()
    {
        return new Customer(Customer.Mode.EmptyMode , "" , "" , "" , (double) 0);
    }
    public void AddNewCustomer()
    {
        addDataLineToFile(convertCustomerObjectToLine(this , "#//#"));
    }
    public static void addDataLineToFile(String stDataLine) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileNameRelatedToCustomers, true))) {
            writer.write(stDataLine);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    static String convertCustomerObjectToLine(Customer customer, String separator) {
        return String.join(separator,
                customer.CustomerName, customer.CustomerAccount,
                customer.CustomerPinCode,
                String.valueOf(customer.Balance));
    }
    static public void UpdateCustomer()
    {
        List<Customer> lDataCustomerscls;
        lDataCustomerscls = loadDataFromFile(FileNameRelatedToCustomers);
        SaveCustomersDataToFile(lDataCustomerscls);

    }
    public static List<Customer> loadDataFromFile(String fileName) {
        List<Customer> lDataCustomers = new ArrayList<>();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String dataLine;
                while ((dataLine = br.readLine()) != null) {
                    lDataCustomers.add(ConvertLineToCustomerObject(dataLine, "#//#"));
                }
            }
        }
        // هون بدنا نكتب جوات الاكسبشن شي يعبر عن الخطا او فينا من دون ما نفصل نعطي خطا وبس منتناقش ومنقرر بعدين
        catch (IOException e) {

            System.err.println("Error Reading File: " + e.getMessage());

        } catch (Exception e) {

            System.err.println("Unexpected Error Due To: " + e.getMessage());
        }
        return lDataCustomers;
    }
    public static Customer ConvertLineToCustomerObject(String line, String separator) {
        List<String> lDataCustomers = clsString.split(line, separator);

        return new Customer(
                Customer.Mode.UpdateMode,
                lDataCustomers.get(0),
                lDataCustomers.get(1),
                lDataCustomers.get(2),
                Double.parseDouble(lDataCustomers.get(3))
        );
    }
    public static void SaveCustomersDataToFile(List<Customer> lDataCustomers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileNameRelatedToCustomers))) {
            for (Customer customer : lDataCustomers) {
                if (customer.IsMarkedForDelete() == false) {
                    // We only write records that are not marked for delete.
                    String dataLine = convertCustomerObjectToLine(customer, "#//#");
                    writer.write(dataLine);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while saving client data: " + e.getMessage());
        }
    }
    public Boolean IsMarkedForDelete() {
        return _MarkedForDelete;
    }
    Boolean IsEmpty()
    {
        return(mode == Customer.Mode.EmptyMode);
    }
    public static Customer FindCustomer(String CustomerAccount) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FileNameRelatedToCustomers))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Customer customer = ConvertLineToCustomerObject(line , "#//#");
                if (customer.getCustomerAccount().equals(CustomerAccount)) {

                    return customer;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return GetEmptyCustomerObject();
    }
    public static Customer FindCustomerByName(String CustomerName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FileNameRelatedToCustomers))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Customer customer = ConvertLineToCustomerObject(line , "#//#");
                if (customer.getCustomerName().equals(CustomerName)) {

                    return customer;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return GetEmptyCustomerObject();
    }
    public static Customer GetAddNewCustomerObject(String CustomerAccount)
    {
        return new Customer(Customer.Mode.NewMode , " " ,CustomerAccount, " " , (double) 0);

    }
    public static Customer GetAddNewCustomerObjectEdit(String CustomerAccount , double Balance)
    {
        return new Customer( CustomerAccount, (double) Balance);

    }
    public static Customer GetAddNewCustomerObjectEdit(String CustomerAccount ,String Name , String PinCode , Double balance)
    {
        return new Customer(Customer.Mode.NewMode , Name ,CustomerAccount, PinCode , (double) balance);

    }
    public static List<Customer> GetCutomersList()
    {
        return loadDataFromFile(FileNameRelatedToCustomers);
    }
    static Boolean IsCustomerExist(String CustomerAccount)
    {
        Customer customer = Customer.FindCustomer( CustomerAccount);
        return(!customer.IsEmpty());
    }
    static Boolean IsCustomerExistEdit(String CustomerName)
    {
        Customer customer = Customer.FindCustomerByName( CustomerName);
        return(!customer.IsEmpty());
    }
    public Customer.SaveResult Save()
    {
        switch(mode)
        {
            case EmptyMode : {
                if (IsEmpty()) {
                    return Customer.SaveResult.SavedFaild;
                }
            }
            case UpdateMode : {
                UpdateCustomer();
                return Customer.SaveResult.SavedSuccessfully;


            }
            case NewMode : {
                if(Customer.IsCustomerExist(getCustomerAccount()))
                {
                    return Customer.SaveResult.svFaildAccountMailExists;


                }
                else
                    AddNewCustomer();
                mode = Customer.Mode.UpdateMode;
                return Customer.SaveResult.SavedSuccessfully;


            }

        }
        return Customer.SaveResult.SavedFaild;
    }
    public boolean delete() {
        List <Customer> lCustomerDatacls = loadDataFromFile(FileNameRelatedToCustomers);

        for (Customer customer : lCustomerDatacls) {
            if (customer.getCustomerAccount().equals(this.CustomerAccount)) {
                customer.set_MarkedForDelete(true);
                break;
            }
        }

        SaveCustomersDataToFile(lCustomerDatacls);

        // Reset the current object to an empty state
        this.CustomerName = "";
        this.CustomerAccount = "";
        this.CustomerPinCode = "";
        this.Balance = 0.0;


        return true;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "CustomerName='" + CustomerName + '\'' +
                ", CustomerAccount='" + CustomerAccount + '\'' +
                ", CustomerPinCode='" + CustomerPinCode + '\'' +
                ", Balance=" + Balance +
                '}';
    }
}
