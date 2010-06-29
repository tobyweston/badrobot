package bad.robot.concordion.ant;

import java.util.Comparator;

public class DescendingComparator implements Comparator<String> {

    public static DescendingComparator descending() {
        return new DescendingComparator();
    }

    private DescendingComparator() {
    }

    public int compare(String first, String second) {
       return second.compareTo(first);
   }
}
