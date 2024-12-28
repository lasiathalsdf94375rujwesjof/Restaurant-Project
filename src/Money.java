import java.util.Scanner;

public class Money {
    public static double WithDraw(String UserID )
    {
          double Value=0;
          double balance;
          Customer customer = Customer.FindCustomerByName(UserID);
        Scanner scanner = new Scanner(System.in);
          do {
              System.out.println("Enter The Amount Which You Want To Draw It");
              balance = scanner.nextDouble();
              if (customer.getBalance() < balance) {
                  System.out.println("Your Balance Is Not Enough :-(");

              }
          } while(balance <= 0 || (customer.getBalance() < balance) );

          return balance;
    }
    public static double Deposit(String UserID )
    {
        double Value=0;
        double balance;
        Customer customer = Customer.FindCustomerByName(UserID);
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Enter The Amount Which You Want To Deposit It");
            balance = scanner.nextDouble();
            if (balance <= 0 ) {
                System.out.println("You Are Stupid :-(");
            }
        } while(balance <= 0 );

        return balance;
    }
    //

    public static void WithDrawSaveFile(String UserID , double TotalPrice)
    {

        double WithDraw;

        String  AccountNumber , Name , PinCode;
        Scanner scanner = new Scanner(System.in);

        Customer customer = Customer.FindCustomerByName(UserID);
        WithDraw = customer.getBalance()-TotalPrice;
        Name = customer.getCustomerName();
        PinCode = customer.getCustomerPinCode();
        AccountNumber = customer.getCustomerAccount();
        customer.delete();
        customer = Customer.GetAddNewCustomerObjectEdit(AccountNumber , Name , PinCode , WithDraw);



        Customer.SaveResult saveResult = customer.Save();
        if (saveResult == Customer.SaveResult.SavedSuccessfully) {
            System.out.println("Payed Successfully!");
        } else {
            System.out.println("Payed Failed");

        }
    }
    public static void DepositSaveFile(String UserID , double TotalPrice)
    {

        double WithDraw;

        String  AccountNumber , Name , PinCode;
        Scanner scanner = new Scanner(System.in);

        Customer customer = Customer.FindCustomerByName(UserID);
        WithDraw = customer.getBalance()+TotalPrice;
        Name = customer.getCustomerName();
        PinCode = customer.getCustomerPinCode();
        AccountNumber = customer.getCustomerAccount();
        customer.delete();
        customer = Customer.GetAddNewCustomerObjectEdit(AccountNumber , Name , PinCode , WithDraw);



        Customer.SaveResult saveResult = customer.Save();
        if (saveResult == Customer.SaveResult.SavedSuccessfully) {
            System.out.println("Payed Successfully!");
        } else {
            System.out.println("Payed Failed");

        }
    }
    public static void ShowMyBalance(Customer customer)
    {
        System.out.println("Your Balance Is "+ customer.getBalance());
    }
}
