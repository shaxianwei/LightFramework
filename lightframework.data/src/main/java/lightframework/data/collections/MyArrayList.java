package lightframework.data.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @param <T> 
 * @author Tom Deng
 */
@SuppressWarnings("serial")
public class MyArrayList<T> extends ArrayList<T> implements Enumerable<T> {

    public MyArrayList() {
        this(10);
    }

    public MyArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public static <U> MyArrayList<U> wrap(U[] array) {
        if (array == null || array.length == 0) {
            throw new NullPointerException("array为空或length为零");
        }

        return wrap(Arrays.asList(array));
    }

    public static <U> MyArrayList<U> wrap(List<U> arrayList) {

        if (arrayList == null || arrayList.isEmpty()) {
            throw new NullPointerException("arrayList为空或length为零");
        }

        MyArrayList<U> myArrayList = new MyArrayList<U>(arrayList.size());
        myArrayList.addAll(arrayList);

        return myArrayList;
    }

    @Override
    public Enumerable<T> where(Predicate<T> predicate) {
        MyArrayList<T> entities = new MyArrayList<T>();
        for (T entity : this) {
            if (predicate.isMatch(entity)) {
                entities.add(entity);
            }
        }
        return entities;
    }

    @Override
    public void each(Action<T> action) {
        for (T entity : this) {
            action.execute(entity);
        }
    }
}
