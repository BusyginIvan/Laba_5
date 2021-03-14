package project.products;

import project.parsing.tags.Tag;

/**
 * Исключение, связанное с интерпретацией содержимого объекта {@link Tag}.
 */
public class InvalidTagException extends RuntimeException {
    /**
     * Конструктор, создающий исключение с указанным сообщением.
     * @param message сообщение ошибки.
     */
    public InvalidTagException(String message) {
        super(message);
    }

    /**
     * Конструктор для исключений при создании объекта по тегу.
     * @param c класс создаваемого объекта.
     * @param message поясняющее сообщение.
     */
    public InvalidTagException(Class c, String message) {
        super("Ошибка при создании объекта " + c.getName() + "! " + message);
    }
}
