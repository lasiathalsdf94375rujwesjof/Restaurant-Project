import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Need {

    public static ArrayList<Integer> EnterNumberMeals() {
        ArrayList<Integer> lInputs = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int num;
        String Continue = "N";
    do
    {
        System.out.println("Enter The Num Of Meal Which You Want :");
        num = scanner.nextInt();

        if(!lInputs.contains(num) && (num > 0 && num <= Meal.HowManyMealsExists( "Meals.txt") )) {

            lInputs.add(num);
        }
        else {
            do
            {
                System.out.println("You Entered Meal Already Exists Or Out Of Range Please Enter New Meal : ");
                num = scanner.nextInt();
            }   while(lInputs.contains(num)||(num > Meal.HowManyMealsExists( "Meals.txt") || num < 0));
            lInputs.add(num);
             }
        System.out.println("Do You Want To Order Another Meal Also Y/N ? ");
        Continue = scanner.next();
        Continue = Continue.toUpperCase();

    }while(Continue.equals("Y") || (num > Meal.HowManyMealsExists( "Meals.txt") || num < 0));
    return lInputs;
    }
    public static int EnterNumberMeal() {
        int NumOfMeal;
        Scanner scanner = new Scanner(System.in);
        int MealsNumbers =  Meal.HowManyMealsExists( "Meals.txt");

        do{
          System.out.println("Enter The Num Of Meal Which You Want :");
          NumOfMeal = scanner.nextInt();
          if(NumOfMeal > MealsNumbers  || NumOfMeal < 0)
          {
              System.out.println("Number Is Out Of Range Please Re Enter A Meal From The List :");
          }



      }while(NumOfMeal > MealsNumbers || NumOfMeal < 0);



return NumOfMeal;
    }

}
