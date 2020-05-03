package util;

/**
 * Consumer interface to allow us to run a lambda 
 * function consuming in one parameter of type T
 */
public interface Consumer<T> {
	void run(T t);
}