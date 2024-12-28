import java.io.*;
import java.util.List;
import java.util.ArrayList;
public class Meal {

    static enum Mode {EmptyMode, UpdateMode, NewMode}
    static  enum SaveResult{SavedFaild  , SavedSuccessfully ,svFaildAccountMailExists  };

    public Mode mode;
    public SaveResult saveresult;
    private String _MealName;
    private String _Ingredients;
    private String _MealNumberInDataFile;
    private Double _MealPrice;
    private Boolean _MarkedForDelete = false;
    final static String FileNameRelatedToMeals = "Meals.txt";


    public Meal(Mode mode, String _MealName, String _Ingredients, String _MealNumberInDataFile, Double _MealPrice) {
        this.mode = mode;
        this._MealName = _MealName;
        this._Ingredients = _Ingredients;
        this._MealNumberInDataFile = _MealNumberInDataFile;
        this._MealPrice = _MealPrice;
    }
    public static Meal GetEmptyMealObject()
    {
        return new Meal(Mode.EmptyMode , "" , "" , "" , (double) 0);
    }
   public void AddNewMeal()
   {
       addDataLineToFile(convertMealObjectToLine(this , "#//#"));
   }

   static public void UpdateMeal()
   {
       List <Meal> lDataMealscls;
       lDataMealscls = loadDataFromFile(FileNameRelatedToMeals);
       SaveMealsDataToFile(lDataMealscls);

   }
   Boolean IsEmpty()
   {
       return(mode == Mode.EmptyMode);
   }
    public String get_MealName() {
        return _MealName;
    }

    public void set_MealName(String _MealName) {
        this._MealName = _MealName;
    }

    public String get_Ingredients() {
        return _Ingredients;
    }

    public void set_Ingredients(String _Ingredients) {
        this._Ingredients = _Ingredients;
    }
    // Read Only Property cause we donnot want to edit it
    public String get_MealNumberInDataFile() {
        return _MealNumberInDataFile;
    }


    public Double get_MealPrice() {
        return _MealPrice;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void set_MealPrice(Double _MealPrice) {
        this._MealPrice = _MealPrice;
    }

    public Boolean IsMarkedForDelete() {
        return _MarkedForDelete;
    }

    static String convertMealObjectToLine(Meal meal, String separator) {
        return String.join(separator,
                meal._MealName, meal._Ingredients,
                meal._MealNumberInDataFile,
                String.valueOf(meal._MealPrice));
    }
    public static Meal ConvertLineToMealObject(String line, String separator) {
        List<String> lMealsData = clsString.split(line, separator);

        return new Meal(
                Mode.UpdateMode,
                lMealsData.get(0),
                lMealsData.get(1),
                lMealsData.get(2),
                Double.parseDouble(lMealsData.get(3))
        );
    }
    public static List<Meal> loadDataFromFile(String fileName) {
        List<Meal> lMealsData = new ArrayList<>();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String dataLine;
                while ((dataLine = br.readLine()) != null) {
                    lMealsData.add(ConvertLineToMealObject(dataLine, "#//#"));
                }
            }
        }
        // هون بدنا نكتب جوات الاكسبشن شي يعبر عن الخطا او فينا من دون ما نفصل نعطي خطا وبس منتناقش ومنقرر بعدين
        catch (IOException e) {

            System.err.println("Error Reading File: " + e.getMessage());

        } catch (Exception e) {

            System.err.println("Unexpected Error Due To: " + e.getMessage());
        }
        return lMealsData;
    }
    public static List<String> GetIngredientsOfOOMeal(List<Meal> lMealsDatacls , int IndexOfMeal) {
        List<String> lMealsData = clsString.split(lMealsDatacls.get(IndexOfMeal).get_Ingredients(), "-");
        return lMealsData;
    }

    public static void SaveMealsDataToFile(List<Meal> lMealsData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileNameRelatedToMeals))) {
            for (Meal meal : lMealsData) {
                if (meal.IsMarkedForDelete() == false) {
                    // We only write records that are not marked for delete.
                    String dataLine = convertMealObjectToLine(meal, "#//#");
                    writer.write(dataLine);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while saving client data: " + e.getMessage());
        }
    }
    public static void addDataLineToFile(String stDataLine) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileNameRelatedToMeals, true))) {
            writer.write(stDataLine);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static Meal FindMeal(String MealAccount) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FileNameRelatedToMeals))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Meal meal = ConvertLineToMealObject(line , "#//#");
                if (meal.get_MealNumberInDataFile().equals(MealAccount)) {

                    return meal;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return GetEmptyMealObject();
    }

    @Override

    public String toString() {
        return "Meal{" +
                "_MealName='" + _MealName + '\'' +
                ", _Ingredients='" + _Ingredients + '\'' +
                ", _MealNumberInDataFile='" + _MealNumberInDataFile + '\'' +
                ", _MealPrice=" + _MealPrice +
                '}';
    }


    public static Meal GetAddNewMealObject(String MealAccount)
    {
        return new Meal(Mode.NewMode , " " ," " ,MealAccount , (double) 0);

    }
    public static List<Meal> GetMealsList()
    {
        return loadDataFromFile(FileNameRelatedToMeals);
    }
    static Boolean IsMealExist(String MailAccount)
    {
        Meal meal = Meal.FindMeal( MailAccount);
        return(!meal.IsEmpty());
    }
    public SaveResult Save()
    {
        switch(mode)
        {
            case EmptyMode : {
                if (IsEmpty()) {
                    return SaveResult.SavedFaild;
                }
            }
            case UpdateMode : {
                UpdateMeal();
                return SaveResult.SavedSuccessfully;


            }
            case NewMode : {
                if(Meal.IsMealExist(get_MealNumberInDataFile()))
                {
                    return SaveResult.svFaildAccountMailExists;


                }
                else
                    AddNewMeal();
                mode = Mode.UpdateMode;
                return SaveResult.SavedSuccessfully;


            }

        }
        return SaveResult.SavedFaild;
    }

    public void setMarkedForDelete(Boolean _MarkedForDelete) {
        this._MarkedForDelete = _MarkedForDelete;
    }

    public boolean delete() {
        List<Meal> lMealsDatacls = loadDataFromFile(FileNameRelatedToMeals);

        for (Meal meal : lMealsDatacls) {
            if (meal.get_MealNumberInDataFile().equals(this._MealNumberInDataFile)) {
                meal.setMarkedForDelete(true);
                break;
            }
        }

        SaveMealsDataToFile(lMealsDatacls);

        // Reset the current object to an empty state
        this._MealName = "";
        this._Ingredients = "";
        this._MealNumberInDataFile = "";
        this._MealPrice = 0.0;


        return true;
    }
    public static int HowManyMealsExists(String fileName) {
        int Counter = 0;

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String dataLine;
                while ((dataLine = br.readLine()) != null) {
                    Counter++;
                }
            }
        }
        // هون بدنا نكتب جوات الاكسبشن شي يعبر عن الخطا او فينا من دون ما نفصل نعطي خطا وبس منتناقش ومنقرر بعدين
        catch (IOException e) {

            System.err.println("Error Reading File: " + e.getMessage());

        } catch (Exception e) {

            System.err.println("Unexpected Error Due To: " + e.getMessage());
        }
        return Counter;
    }

}
