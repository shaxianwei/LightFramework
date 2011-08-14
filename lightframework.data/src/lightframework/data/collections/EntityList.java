/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.collections;

import java.util.ArrayList;

/**
 *
 * @author Tom Deng
 */
@SuppressWarnings("serial")
public class EntityList<T> extends ArrayList<T> implements Enumerable<T> {

    public EntityList() {
        this(10);
    }

    public EntityList(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public Enumerable<T> where(Predicate<T> predicate) {
        EntityList<T> entities = new EntityList<T>();
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
