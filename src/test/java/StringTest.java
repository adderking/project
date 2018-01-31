import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTest {

    private void stringSplit(String content) {
        String reg = "(dx[0-9*])";
        Pattern pattern = Pattern.compile(reg,Pattern.MULTILINE);
        String[] ss = content.split(reg);
        for (String s : ss) {
            System.out.println("dx"+s);
        }
    }

    private void stringSplitForIndex(String content) {
        int length = content.length();
        String equipmentID=null;
        for(int i=0;i<length;i+=7) {
            equipmentID = content.substring(i, i + 7);
            System.out.println(equipmentID);
        }
    }

    @Test
    public void split() {
        String text = "dx00001dx00002dx00003";
        stringSplitForIndex(text);
    }
}
