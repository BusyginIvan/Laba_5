package project.commands.command;

import project.MyReader;

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
        System.out.println("Завершение работы программы.");
        MyReader.close();
        System.exit(0);
    }

    /**
     * Возвращает описание команды в виде массива строк: первые элементы - названия аргументов, последний - строка,
     * описывающая команду.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"завершение работы программы"};
    }
}