package project.commands.command;

import project.products.ElementBuilder;
import project.products.product.Product;
import project.products.product_collection.IProductCollection;

/**
 * Команда замены товара по номеру.
 * @see project.commands.command_map.CommandMap
 * @see ElementBuilder
 */
public class Update implements ICommand {
    IProductCollection productCollection;

    /**
     * Эта команда будет работать с переданной в конструктор коллекцией.
     * @param productCollection коллеция товаров.
     */
    public Update(IProductCollection productCollection) {
        this.productCollection = productCollection;
    }

    /**
     * Получает значения характеристик товара, применяя метод {@link ElementBuilder#changeProduct(Product)}, и заменяет
     * товар с указанным номером на новый.
     * @param arguments массив аргументов команды, arguments[0] - номер товара.
     */
    @Override
    public void execute(String[] arguments) {
        if (arguments == null || arguments.length == 0)
            throw new IllegalArgumentException("После этой команды должен следовать аргумент с номером товара.");
        try {
            ElementBuilder.changeProduct(productCollection.getProductByID(Long.valueOf(arguments[0])));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Аргумент номера товара - натуральное число.");
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Товара с таким индексом не существует.");
        }
    }

    /**
     * Возвращает описание команды в виде массива строк: первые элементы - названия аргументов, последний - строка,
     * описывающая команду.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"номер товара", "заменить товар с указанным номером новым"};
    }
}