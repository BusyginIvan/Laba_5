package project.commands.commands;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Команда для получения справки о доступных командах.
 * @see project.commands.command_map.CommandMap
 */
public class Help implements ICommand {
    HashMap<String, ICommand> commands;

    /**
     * Команда будет выводить справку по переданному в конструктор набору команд.
     * @param commands набор команд.
     */
    public Help(HashMap<String, ICommand> commands) {
        this.commands = commands;
    }

    /**
     * Выводит справку по каждой из команд (название - краткое описание).
     * В случае отсутствия команд сообщает об этом пользователю.
     * @param arguments массив аргументов команды (не имеет значения).
     */
    @Override
    public void execute(String[] arguments) {
        Iterator<String> iterator = commands.keySet().iterator();
        if (iterator.hasNext()) {
            System.out.print("Все доступные команды:");
            line(iterator.next());
            while (iterator.hasNext()) {
                System.out.print(";");
                line(iterator.next());
            }
            System.out.println(".");
        } else
            System.out.println("Не существует команд, которые вы бы могли использовать.\n");
    }

    private void line(String key) {
        String[] description = commands.get(key).description();
        System.out.print("\n " + key);
        for (int i = 0; i < description.length - 1; i++)
            System.out.print(" <" + description[i] + ">");
        System.out.print(" - " + description[description.length - 1]);
    }

    /**
     * Возвращает описание команды в виде массива строк: первые элементы - названия аргументов, последний - строка,
     * описывающая команду.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"вывести информацию о доступных командах"};
    }
}