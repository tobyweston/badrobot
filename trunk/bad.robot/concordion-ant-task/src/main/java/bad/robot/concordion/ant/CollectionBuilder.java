package bad.robot.concordion.ant;

import java.util.Collection;

public interface CollectionBuilder<COLLECTION extends Collection<?>, EXCEPTION extends Exception> extends Builder {

    COLLECTION build() throws EXCEPTION;

}
