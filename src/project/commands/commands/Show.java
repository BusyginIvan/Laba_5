package project.commands.commands;

import project.products.product.Product;
import project.products.product_collection.IProductCollection;

import java.util.Iterator;

/**
 * Команда плучения информации о каждом товаре списка.
 * @see project.commands.command_map.CommandMap
 */
public class Show implements ICommand {
    IProductCollection productCollection;

    /**
     * Эта команда будет работать с товарами из переданной в конструктор коллекции.
     * @param productCollection коллеция товаров.
     */
    public Show(IProductCollection productCollection) {
        this.productCollection = productCollection;
    }

    /**
     * Выводит информацию о каждом из товаров в списке.
     * @param arguments массив аргументов команды (не имеет значения).
     */
    @Override
    public void execute(String[] arguments) {
        if (productCollection.getProducts().size() == 0)
            System.out.println("Товаров нет.");
        else {
            System.out.println("Список товаров:");
            Iterator<Product> iterator = productCollection.iterator();
            iterator.next().printInfo(1);
            while (iterator.hasNext()) {
                System.out.println();
                iterator.next().printInfo(1);
            }
        }
    }

    /**
     * Возвращает описание команды в виде массива, содержащего одну строку с описанием. Используется в {@link Help}.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"вывести список всех товаров"};
    }
}