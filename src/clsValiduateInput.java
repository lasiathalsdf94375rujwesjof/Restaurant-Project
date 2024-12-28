import java.util.Scanner;

public class clsValiduateInput {
    public static String ReadString(String Message)
    {
        Scanner in = new Scanner(System.in);
        String S ;
        System.out.println(Message);
        S = in.nextLine();
        return S;
    }
    public static int ReadInt(String Message)
    {
        Scanner in = new Scanner(System.in);
        int S ;
        System.out.println(Message);
        S = in.nextInt();
        return S;
    }
    public static String ReadStringC(String Message)
    {
        Scanner in = new Scanner(System.in);
        String S ;
        System.out.println(Message);
        S = in.next();
        return S;
    }
    public static short ReadIntBetweenRangeS(String Message ,int From , int To)
    {
        short S;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println(Message);
            S = in.nextShort();
           }while(S > To || S < From);
         return S;
    }
    public static int ReadIntBetweenRangeI(String Message ,int From , int To)
    {
        int S;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println(Message);
            S = in.nextInt();
        }while(S > To || S < From);
        return S;
    }
}
