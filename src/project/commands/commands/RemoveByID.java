package project.commands.commands;

import project.products.product_collection.IProductCollection;

/**
 * Команда удаления товара по номеру.
 * @see project.commands.command_map.CommandMap
 */
public class RemoveByID implements ICommand {
    IProductCollection productCollection;

    /**
     * Эта команда будет работать с переданным в конструктор списком товаров.
     * @param productCollection коллеция товаров.
     */
    public RemoveByID(IProductCollection productCollection) {
        this.productCollection = productCollection;
    }

    /**
     * Удаляет из списка товар с указанным номером. Если его не существует - сообщает об этом пользователю.
     * @param arguments массив аргументов команды, arguments[0] - номер товара.
     * @exception CommandException если отсутсвует или некорректно записан аргумент номера товара.
     */
    @Override
    public void execute(String[] arguments) throws CommandException {
        if (arguments == null || arguments.length == 0)
            throw new CommandException("После этой команды должен следовать аргумент с номером товара.");
        try {
            if (productCollection.removeProductByID(Long.valueOf(arguments[0])))
                System.out.println("Товар удалён.");
            else
                System.out.println("Товара с таким индексом не существует.");
        } catch (NumberFormatException e) {
            throw new CommandException("Аргумент номера товара - натуральное число.");
        }
    }

    /**
     * Возвращает описание команды в виде массива строк: первые элементы - названия аргументов, последний - строка,
     * описывающая команду.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"номер товара", "удалить элемент с заданным номером"};
    }
}