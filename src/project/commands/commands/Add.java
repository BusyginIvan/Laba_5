package project.commands.commands;

import project.products.ElementBuilder;
import project.products.product.*;
import project.products.product_collection.IProductCollection;

/**
 * Команда добавления нового товара.
 * @see project.commands.command_map.CommandMap
 * @see ElementBuilder
 */
public class Add implements ICommand {
    IProductCollection productCollection;

    /**
     * Команда будет добавлять товары в переданную в конструктор коллекцию.
     * @param productCollection коллеция товаров.
     */
    public Add(IProductCollection productCollection) {
        this.productCollection = productCollection;
    }

    /**
     * Получает значения характеристик товара у пользователя и помещает новый товар в коллекцию.
     * @param arguments массив аргументов команды (не имеет значения).
     */
    @Override
    public void execute(String[] arguments) {
        productCollection.getProducts().add(new Product());
    }

    /**
     * Возвращает описание команды в виде массива, содержащего одну строку с описанием. Используется в {@link Help}.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"добавить новый товар в коллекцию"};
    }
}