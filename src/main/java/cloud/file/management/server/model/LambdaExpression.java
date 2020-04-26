package cloud.file.management.server.model;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Class implements useful features to work with list
 */
public abstract class LambdaExpression {
    /**
     * @param list some list to process
     * @param consumer operation to execute for each element list
     * @param <T> type of element list
     */
    public static <T> void consumer(List<T> list, Consumer<T>consumer){
        for(T t : list){
            consumer.accept(t);
        }
    }

    /**
     * @param list some list to process
     * @param consumer operation to execute for each element list
     * @param predicate condition of the action event
     * @param <T> type of element list
     */
    public static <T> void actionIf(List<T> list, Consumer<T>consumer, Predicate<T>predicate){
        for ( T t : list ){
            if ( predicate.test(t) ){
                consumer.accept(t);
            }
        }
    }

    /**
     * @param list some list to process
     * @param predicate condition of the searched item
     * @param <T> type of element list
     * @return found item
     */
    public static <T> T find(List<T> list, Predicate<T> predicate){
        for ( T t : list ){
            if ( predicate.test(t) )
                return t;
        }
        return null;
    }
}
