package project.commands.command_map;

import project.commands.commands.CommandException;

/**
 * Класс, реализующий этот интерфейс, должен быть способен распознать команду по её строковому представлению и выполнить её.
 */
public interface ICommandMap {
    /**
     * Выполняет строковую команду.
     * @param str строка с командой и аргументами.
     * @exception CommandException если команда не распознана.
     */
    void execute(String str) throws CommandException;
}
