package project.parsing.load;

/**
 * Исключение при загрузке из файла.
 */
public class LoadException extends RuntimeException {
    public LoadException(String message) {
        super(message);
    }
}
