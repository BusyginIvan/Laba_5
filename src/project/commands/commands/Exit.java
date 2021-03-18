package project.commands.commands;

import project.ConsoleReader;
import project.Main;

/**
 * Команда завершения работы программы.
 * @see project.commands.command_map.CommandMap
 */
public class Exit implements ICommand {
    /**
     * Завершает работу программы.
     * @param arguments массив аргументов команды (не имеет значения).
     */
    @Override
    public void execute(String[] arguments) {
        Main.exit();
    }

    /**
     * Возвращает описание команды в виде массива, содержащего одну строку с описанием. Используется в {@link Help}.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"завершение работы программы"};
    }
}