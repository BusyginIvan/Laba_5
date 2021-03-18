package project.commands.commands;

import project.products.product.Person;
import project.products.product.Product;
import project.products.product_collection.IProductCollection;

import java.util.HashSet;

/**
 * Команда, выводящая список всех владельцев.
 * @see project.commands.command_map.CommandMap
 */
public class PrintUniqueOwner implements ICommand {
    /**
     * Выводит имена и номера паспорта людей, являющихся владельцами тех или иных товаров.
     * @param arguments массив аргументов команды (не имеет значения).
     */
    @Override
    public void execute(String[] arguments) {
        if (Person.getPersons().size() == 0)
            System.out.println("Владельцев нет.");
        else
            for (Person person: Person.getPersons())
                System.out.println(person.toString());
    }

    /**
     * Возвращает описание команды в виде массива, содержащего одну строку с описанием. Используется в {@link Help}.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"вывести всех владельцев"};
    }
}