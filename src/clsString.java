import java.util.ArrayList;
import java.util.List;

public class clsString {

    public static List<String> split(String str, String delimiter) {
        List<String> result = new ArrayList<>();
        int pos = 0;

        while ((pos = str.indexOf(delimiter)) != -1) {
            String word = str.substring(0, pos);
            result.add(word);
            str = str.substring(pos + delimiter.length());
        }

        if (!str.isEmpty()) {
            result.add(str);
        }

        return result;
    }
}