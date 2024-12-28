import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CuOrder {
    public String MealName;
    public int NumOfMeals;
    public double PriceOfMeals;
    public  String TypeOfOrder;
    public Mode mode;
    String CustomerName;
    private Boolean _MarkedForDelete = false;
    final static String FileNameRelatedToCustomerOrders = "Orders.txt";

    static enum Mode {EmptyMode, UpdateMode, NewMode}

    ;

    static enum SaveResult {SavedFaild, SavedSuccessfully, svFaildAccountMailExists}

    ;

    public CuOrder(Mode mode, String customerName, String mealName, int numOfMeals, double priceOfMeals , String TypeOfOrder) {
        this.mode = mode;
        CustomerName = customerName;
        MealName = mealName;
        NumOfMeals = numOfMeals;
        PriceOfMeals = priceOfMeals;
        this.TypeOfOrder = TypeOfOrder;

    }



    public String getTypeOfOrder() {
        return TypeOfOrder;
    }

    public void setTypeOfOrder(String typeOfOrder) {
        TypeOfOrder = typeOfOrder;
    }

    public Boolean get_MarkedForDelete() {
        return _MarkedForDelete;
    }

    public void set_MarkedForDelete(Boolean _MarkedForDelete) {
        this._MarkedForDelete = _MarkedForDelete;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public int getNumOfMeals() {
        return NumOfMeals;
    }

    public void setNumOfMeals(int numOfMeals) {
        NumOfMeals = numOfMeals;
    }

    public String getMealName() {
        return MealName;
    }

    public void setMealName(String mealName) {
        MealName = mealName;
    }

    public static CuOrder GetEmptyCuOrderObject() {
        return new CuOrder(Mode.EmptyMode, "", "", 0 ,0.0 , "");
    }

    public void AddNewCustomerOrder() {
        addDataLineToFile(convertCuOrdersObjectToLine(this, "#//#"));
    }

    public static void addDataLineToFile(String stDataLine) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileNameRelatedToCustomerOrders, true))) {
            writer.write(stDataLine);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static String convertCuOrdersObjectToLine(CuOrder cuOrder, String separator) {
        return String.join(separator,
                cuOrder.CustomerName, cuOrder.MealName,
                String.valueOf(cuOrder.NumOfMeals),
                String.valueOf(cuOrder.PriceOfMeals),
                cuOrder.TypeOfOrder);
    }

    static public void UpdateCustomerOrder() {
        List<CuOrder> lDataCustomersOrdersCls;
        lDataCustomersOrdersCls = loadDataFromFile(FileNameRelatedToCustomerOrders);
        SaveCustomersDataToFile(lDataCustomersOrdersCls);

    }
    public static void SaveCustomersDataToFile(List<CuOrder> lDataCustomersOrdersCls) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileNameRelatedToCustomerOrders))) {
            for (CuOrder cuOrder : lDataCustomersOrdersCls) {
                if (cuOrder.get_MarkedForDelete() == false) {
                    // We only write records that are not marked for delete.
                    String dataLine = CuOrder.convertCuOrdersObjectToLine(cuOrder, "#//#");
                    writer.write(dataLine);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while saving client data: " + e.getMessage());
        }
    }

    public static List<CuOrder> loadDataFromFile(String fileName) {
        List<CuOrder> lDataCustomersOrdersCls = new ArrayList<>();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String dataLine;
                while ((dataLine = br.readLine()) != null) {
                    lDataCustomersOrdersCls.add(ConvertLineToCuOrders(dataLine, "#//#"));
                }
            }
        }
        // هون بدنا نكتب جوات الاكسبشن شي يعبر عن الخطا او فينا من دون ما نفصل نعطي خطا وبس منتناقش ومنقرر بعدين
        catch (IOException e) {

            System.err.println("Error Reading File: " + e.getMessage());

        } catch (Exception e) {

            System.err.println("Unexpected Error Due To: " + e.getMessage());
        }
        return lDataCustomersOrdersCls;
    }
    public static CuOrder ConvertLineToCuOrders(String line, String separator) {
        List<String> lDataCustomersOrdersCls = clsString.split(line, separator);

        return new CuOrder(
                Mode.UpdateMode,
                lDataCustomersOrdersCls.get(0),
                lDataCustomersOrdersCls.get(1),
                Integer.parseInt(lDataCustomersOrdersCls.get(2)),
                Double.parseDouble(lDataCustomersOrdersCls.get(3)),
                lDataCustomersOrdersCls.get(4));
    }
    Boolean IsEmpty()
    {
        return(mode == Mode.EmptyMode);
    }

    public static CuOrder FindCuOrder(String CustmerName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FileNameRelatedToCustomerOrders))) {
            String line;
            while ((line = reader.readLine()) != null) {
                CuOrder cuOrder = ConvertLineToCuOrders(line , "#//#");
                if (cuOrder.getCustomerName().equals(CustmerName)) {

                    return cuOrder;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return GetEmptyCuOrderObject();
    }
    public static List<CuOrder> GetCuOrdersList()
    {
        return loadDataFromFile(FileNameRelatedToCustomerOrders);
    }
    static Boolean IsCuOrderExist(String customerName)
    {
        CuOrder cuOrder = FindCuOrder( customerName);
        return(!cuOrder.IsEmpty());
    }
    public CuOrder.SaveResult Save()
    {
        switch(mode)
        {
            case EmptyMode :{
                if (IsEmpty()) {
                    return CuOrder.SaveResult.SavedFaild;
                }
            }
            case UpdateMode : {
                UpdateCustomerOrder();
                return SaveResult.SavedSuccessfully;


            }
            case NewMode : {

                    AddNewCustomerOrder();
                mode = Mode.UpdateMode;
                return CuOrder.SaveResult.SavedSuccessfully;


            }

        }
        return CuOrder.SaveResult.SavedFaild;
    }
    public boolean delete() {
        List <CuOrder> lDataCustomersOrdersCls = loadDataFromFile(FileNameRelatedToCustomerOrders);

        for (CuOrder cuOrder : lDataCustomersOrdersCls) {
            if (cuOrder.getCustomerName().equals(getCustomerName())) {
                cuOrder.set_MarkedForDelete(true);
                break;
            }
        }

        SaveCustomersDataToFile(lDataCustomersOrdersCls);

        // Reset the current object to an empty state
        this.CustomerName = "";
        this.MealName= "";
        this.NumOfMeals = 0;
        this.PriceOfMeals = 0 ;
        this.TypeOfOrder = "";


        return true;
    }

    @Override
    public String toString() {
        return "CuOrder{" +
                "mode=" + mode +
                ", CustomerName='" + CustomerName + '\'' +
                ", MealName='" + MealName + '\'' +
                ", NumOfMeals=" + NumOfMeals +
                ", PriceOfMeals=" + PriceOfMeals +
                ", TypeOfOrder='" + TypeOfOrder + '\'' +
                '}';
    }
}
