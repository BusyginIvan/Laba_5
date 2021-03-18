package project.parsing.load;

/**
 * Исключение при загрузке из файла.
 */
public class LoadException extends RuntimeException {
    /**
     * Конструктор, создающий исключение с указанным сообщением.
     * @param message сообщение ошибки.
     */
    public LoadException(String message) {
        super(message);
    }
}
