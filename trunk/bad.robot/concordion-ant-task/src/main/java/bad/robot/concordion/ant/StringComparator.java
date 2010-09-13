package bad.robot.concordion.ant;

import java.util.Comparator;

public class StringComparator implements Comparator<String> {
    private final Order order;

    enum Order {
        DESCENDING {
            @Override
            int compare(String first, String second) {
                return second.compareTo(first);
            }},
        ASCENDING {
            @Override
            int compare(String first, String second) {
                return first.compareTo(second); 
            }};

        abstract int compare(String first, String second);
    };

    public StringComparator(Order order) {
        this.order = order;
    }

    public int compare(String first, String second) {
        return order.compare(first, second);
    }
}
