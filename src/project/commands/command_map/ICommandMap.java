package project.commands.command_map;

/**
 * Класс, реализующий этот интерфейс, должен быть способен распознать команду по её строковому представлению и выполнить её.
 */
public interface ICommandMap {
    /**
     * Выполняет строковую команду.
     * @param str строка с командой и аргументами.
     */
    void execute(String str);
}
