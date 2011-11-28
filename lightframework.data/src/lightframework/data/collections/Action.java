package lightframework.data.collections;

/**
 *
 * @author Tom Deng
 */
public interface Action<T>{
    void execute(T element);
}