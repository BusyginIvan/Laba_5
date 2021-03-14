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
    IProductCollection productCollection;

    /**
     * Эта команда будет работать с товарами из переданной в конструктор коллекции.
     * @param productCollection коллеция товаров.
     */
    public PrintUniqueOwner(IProductCollection productCollection) {
        this.productCollection = productCollection;
    }

    /**
     * Выводит имена и номера паспорта людей, являющихся владельцами тех или иных товаров.
     * @param arguments массив аргументов команды (не имеет значения).
     */
    @Override
    public void execute(String[] arguments) {
        HashSet<Person> owners = new HashSet<>();
        for (Product product: productCollection)
            if (product.getOwner() != null)
                owners.add(product.getOwner());
        if (owners.size() == 0)
            System.out.println("Владельцев нет.");
        else
            for (Person person: owners)
                System.out.println(person.getName() + " (номер паспорта - " + person.getPassportID() + ")");
    }

    /**
     * Возвращает описание команды в виде массива строк: первые элементы - названия аргументов, последний - строка,
     * описывающая команду.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"вывести всех владельцев"};
    }
}