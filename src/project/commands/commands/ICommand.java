package project.commands.commands;

/**
 * Класс-команда должен обрабатывать аргументы и выполнять действия, краткое описание которых представляет в виде строки.
 * @see project.commands.command_map.ICommandMap
 */
public interface ICommand {
    /**
     * Выполнение команды.
     * @param arguments массив аргументов команды.
     * @exception CommandException исключение при выполнении команды (обычно связано с некорректностью переданных аргументов).
     */
    void execute(String[] arguments) throws CommandException;

    /**
     * Возвращает описание команды в виде массива строк: первые элементы - названия аргументов, последний - строка,
     * описывающая команду. Описание пишется с маленькой буквы без концевого знака препинания.
     * @return массив строк.
     */
    String[] description();
}