package project.commands.commands;

/**
 * Исключение при выполнении команды.
 */
public class CommandException extends Exception {
    /**
     * Конструктор, создающий исключение с указанным сообщением.
     * @param message сообщение ошибки.
     */
    public CommandException(String message) {
        super(message);
    }
}