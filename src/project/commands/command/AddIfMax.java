package project.commands.command;

import project.products.ElementBuilder;
import project.products.product.Product;
import project.products.product_collection.IProductCollection;

/**
 * Команда добавления нового товара при условии его первенства в естественном порядке сортировки.
 * @see project.commands.command_map.CommandMap
 * @see ElementBuilder
 */
public class AddIfMax implements ICommand {
    IProductCollection productCollection;

    /**
     * Эта команда будет добавлять товары в переданную коллекцию.
     * @param productCollection коллеция товаров.
     */
    public AddIfMax(IProductCollection productCollection) {
        this.productCollection = productCollection;
    }

    /**
     * Получает значения характеристик товара, применяя метод {@link ElementBuilder#changeProduct(Product)},
     * и помещает товар в коллекцию, если его название будет первым по алфавитному порядку.
     * @param arguments массив аргументов команды (не имеет значения).
     */
    @Override
    public void execute(String[] arguments) {
        Product newProduct = new Product();
        ElementBuilder.changeProduct(newProduct);
        for (Product product: productCollection)
            if (newProduct.compareTo(product) <= 0) {
                System.out.println("\nНовый товар оказался не самым большим по цене");
                return;
            }
        System.out.println("\nНовый товар добавлен в список.");
        productCollection.getArrayDeque().add(newProduct);
    }

    /**
     * Возвращает описание команды в виде массива строк: первые элементы - названия аргументов, последний - строка,
     * описывающая команду.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"добавить новый товар в коллекцию, если его цена будет наибольшей"};
    }
}