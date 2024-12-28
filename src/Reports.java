import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reports {
    public static String CalculateDailyRevenues(String fileName) {
        ArrayList<Double> lDataCustomersOrdersCls = new ArrayList<>();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String dataLine;
                while ((dataLine = br.readLine()) != null) {
                  lDataCustomersOrdersCls.add( ConvertLineToCuOrders(dataLine, "#//#").PriceOfMeals);
                }
            }
        }
        // هون بدنا نكتب جوات الاكسبشن شي يعبر عن الخطا او فينا من دون ما نفصل نعطي خطا وبس منتناقش ومنقرر بعدين
        catch (IOException e) {

            System.err.println("Error Reading File: " + e.getMessage());

        } catch (Exception e) {

            System.err.println("Unexpected Error Due To: " + e.getMessage());
        }
         double DailyRevenus = 0.0;
        for (Double i : lDataCustomersOrdersCls)
        {
            DailyRevenus += i;
        }

        return String.valueOf(DailyRevenus);
    }
    public static int CalculateDailyOrdersNumber(String fileName) {
        int num = 0;
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String dataLine;
                while ((dataLine = br.readLine()) != null) {
                  num++;
                }
            }
        }
        // هون بدنا نكتب جوات الاكسبشن شي يعبر عن الخطا او فينا من دون ما نفصل نعطي خطا وبس منتناقش ومنقرر بعدين
        catch (IOException e) {

            System.err.println("Error Reading File: " + e.getMessage());

        } catch (Exception e) {

            System.err.println("Unexpected Error Due To: " + e.getMessage());
        }


        return num;
    }
    public static ArrayList<String> MealsInList(String fileName) {
        ArrayList<String> lDataCustomersOrdersCls = new ArrayList<>();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String dataLine;
                while ((dataLine = br.readLine()) != null) {
                    lDataCustomersOrdersCls.add( ConvertLineToCuOrders(dataLine, "#//#").MealName);
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
    public static ArrayList<String> CalculateARegularCustomerOfthRestaurant(String fileName) {
        ArrayList<String> lDataCustomersOrdersCls = new ArrayList<>();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String dataLine;
                while ((dataLine = br.readLine()) != null) {
                    lDataCustomersOrdersCls.add( ConvertLineToCuOrders(dataLine, "#//#").CustomerName);
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
    public static ArrayList<String> getRepeatedNames(ArrayList<String> names) {
        // HashMap لتخزين عدد مرات التكرار لكل اسم
        Map<String, Integer> nameCount = new HashMap<>();

        // حساب التكرارات
        for (String name : names) {
            nameCount.put(name, nameCount.getOrDefault(name, 0) + 1);
        }

        // قائمة جديدة لتخزين الأسماء المكررة فقط
        ArrayList<String> repeatedNames = new ArrayList<>();

        // إضافة الأسماء المكررة إلى القائمة مرة واحدة
        for (Map.Entry<String, Integer> entry : nameCount.entrySet()) {
            if (entry.getValue() > 1) {
                repeatedNames.add(entry.getKey());
            }
        }

        return repeatedNames;
    }
    public static String getMostFrequentName(ArrayList<String> names) {
        // HashMap لتخزين عدد مرات التكرار لكل اسم
        Map<String, Integer> nameCount = new HashMap<>();

        // حساب التكرارات
        for (String name : names) {
            nameCount.put(name, nameCount.getOrDefault(name, 0) + 1);
        }

        // العثور على الاسم الأكثر تكرارًا
        String mostFrequentName = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : nameCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostFrequentName = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        return mostFrequentName;
    }
    public static CuOrder ConvertLineToCuOrders(String line, String separator) {
        List<String> lDataCustomersOrdersCls = clsString.split(line, separator);

        return new CuOrder(
                CuOrder.Mode.UpdateMode,
                lDataCustomersOrdersCls.get(0),
                lDataCustomersOrdersCls.get(1),
                Integer.parseInt(lDataCustomersOrdersCls.get(2)),
                Double.parseDouble(lDataCustomersOrdersCls.get(3)),
                lDataCustomersOrdersCls.get(4));
    }

}
