package project.commands.commands;

import project.products.product.Product;
import project.products.product_collection.IProductCollection;

/**
 * Команда, выводящая информацию о первом товаре.
 * @see project.commands.command_map.CommandMap
 */
public class Head implements ICommand {
    IProductCollection productCollection;

    /**
     * Эта команда будет выводить информацию о товарах из переданной в конструктор коллекции.
     * @param productCollection коллеция товаров.
     */
    public Head(IProductCollection productCollection) {
        this.productCollection = productCollection;
    }

    /**
     * Выводит информацию о первом товаре из списка. В случае отсутствия товаров сообщает об этом пользователю.
     * @param arguments массив аргументов команды (не имеет значения).
     */
    @Override
    public void execute(String[] arguments) {
        Product product;
        if ((product = productCollection.getProducts().peek()) == null)
            System.out.println("Список товаров пуст.");
        else
            product.printInfo(0);
    }

    /**
     * Возвращает описание команды в виде массива, содержащего одну строку с описанием. Используется в {@link Help}.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"вывести информацию о первом товаре"};
    }
}