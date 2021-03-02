package project.commands.command;

import project.products.product.Product;
import project.products.product_collection.IProductCollection;

/**
 * Команда, выводящая информацию о товаре, первом в порядке сортировки по владельцу.
 * @see project.commands.command_map.CommandMap
 */
public class MinByOwner implements ICommand {
    IProductCollection productCollection;

    /**
     * Эта команда будет работать с товарами из переданной в конструктор коллекции.
     * @param productCollection коллеция товаров.
     */
    public MinByOwner(IProductCollection productCollection) {
        this.productCollection = productCollection;
    }

    /**
     * Выводит информацию о товаре, первом согласно сортировке по именам владельцев.
     * В случае отсутствия товаров сообщает об этом пользователю.
     * @param arguments массив аргументов команды (не имеет значения).
     */
    @Override
    public void execute(String[] arguments) {
        Product minByOwner;
        if ((minByOwner = productCollection.getArrayDeque().peek()) == null)
            System.out.println("Товаров в списке нет.");
        else {
            for (Product product: productCollection)
                if (!(product.getOwner() == null) && minByOwner.getOwner().compareTo(product.getOwner()) > 0)
                    minByOwner = product;
            minByOwner.printInfo(0);
        }
    }

    /**
     * Возвращает описание команды в виде массива строк: первые элементы - названия аргументов, последний - строка,
     * описывающая команду.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"вывести информацию о товаре, имя владельца которого идёт первым в алфавитном порядке"};
    }
}