package project.commands.command;

/**
 * Класс-команда должен обрабатывать аргументы и выполнять действия, краткое описание которых представляет в виде строки.
 * @see project.commands.command_map.ICommandMap
 */
public interface ICommand {
    /**
     * Выполнение команды.
     * @param arguments массив аргументов команды.
     */
    void execute(String[] arguments);

    /**
     * Возвращает описание этой команды в виде предложения с маленькой буквы без закрывающего знака препинания.
     * @return строковое описание.
     */
    String[] description();
}