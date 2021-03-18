package project.parsing.tags;

/**
 * Исключение, связанное с присутствием в одном теге нескольких одноимённых.
 */
public class DuplicateTagException extends InvalidTagException {
    /**
     * Конструктор, создающий исключение с сообщением о дублировании тега.
     * @param name повторяющееся имя тегов.
     */
    public DuplicateTagException(String name) {
        super("Дублируется тег " + name + ".");
    }
}