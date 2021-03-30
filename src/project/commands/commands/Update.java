package project.commands.commands;

import project.commands.CommandException;
import project.products.ElementBuilder;
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
     * Получает значения характеристик товара у пользователя и заменяет товар с указанным номером на новый.
     * @param arguments массив аргументов команды, arguments[0] - номер товара.
     * @exception CommandException если отсутсвует или некорректно записан аргумент номера товара.
     */
    @Override
    public void execute(String[] arguments) throws CommandException {
        if (arguments == null || arguments.length == 0)
            throw new CommandException("После этой команды должен следовать аргумент с номером товара.");
        try {
            productCollection.getProductByID(Long.parseLong(arguments[0])).update();
        } catch (NumberFormatException e) {
            throw new CommandException("Аргумент номера товара - натуральное число.");
        } catch (NullPointerException e) {
            System.out.println("Товара с таким индексом не существует.");
        }
    }

    /**
     * Возвращает описание команды в виде массива строк: первые элементы - названия аргументов, последний - строка,
     * описывающая команду. Используется в {@link Help}.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"номер товара", "заменить товар с указанным номером новым"};
    }
}