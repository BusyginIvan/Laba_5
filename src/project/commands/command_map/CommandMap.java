package project.commands.command_map;

import project.commands.commands.*;
import project.products.product_collection.IProductCollection;
import project.parsing.save.ISaver;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Класс, сопоставляющий названия команд и их реализации.
 * @see ICommand
 */
public class CommandMap implements ICommandMap {
    private HashMap<String, ICommand> commands;

    /**
     * Задаёт сопоставления команд и названий. Команды будут работать с коллекцией продуктов productCollection
     * и использовать saver для сохранения коллекции в нужный файл.
     * @param productCollection обрабатываемый список товаров.
     * @param saver объект класса сохранения.
     */
    public CommandMap(IProductCollection productCollection, ISaver saver) {
        commands = new HashMap<>(16);
        commands.put("help", new Help(commands));
        commands.put("info", new Info(productCollection));
        commands.put("show", new Show(productCollection));
        commands.put("save", new Save(productCollection, saver));
        commands.put("add", new Add(productCollection));
        commands.put("remove_by_id", new RemoveByID(productCollection));
        commands.put("update", new Update(productCollection));
        commands.put("clear", new Clear(productCollection));
        commands.put("remove_first", new RemoveFirst(productCollection));
        commands.put("head", new Head(productCollection));
        commands.put("min_by_owner", new MinByOwner(productCollection));
        commands.put("max_by_coordinates", new MaxByCoordinates(productCollection));
        commands.put("print_unique_owner", new PrintUniqueOwner());
        commands.put("add_if_max", new AddIfMax(productCollection));
        commands.put("execute_script", new ExecuteScript(this));
        commands.put("exit", new Exit());
    }

    /**
     * Выполняет строковую команду.
     * @param str строка с командой и аргументами.
     * @exception CommandException если получена несуществующая команда или переданы неверные аргументы.
     */
    public void execute(String str) throws CommandException {
        System.out.println();
        String[] words = str.split("\\s+");
        if (words.length == 0)
            throw new CommandException("Пустая строка - не команда!");
        if (commands.containsKey(words[0])) {
            if (words.length > 1)
                commands.get(words[0]).execute(Arrays.copyOfRange(words, 1, words.length));
            else
                commands.get(words[0]).execute(null);
        } else throw new CommandException("Такой команды не существует!");
    }
}