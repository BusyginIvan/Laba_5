package project.commands.commands;

import project.products.product.Person;
import project.products.product.Product;
import project.products.product_collection.IProductCollection;

import java.util.Iterator;

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
        Iterator<Product> iterator = productCollection.iterator();
        if (iterator.hasNext()) {
            Person minOwner = iterator.next().getOwner();
            while (iterator.hasNext()) {
                Person owner = iterator.next().getOwner();
                if (minOwner == null) {
                    if (owner != null)
                        minOwner = owner;
                } else if (owner != null && minOwner.compareTo(owner) > 0)
                    minOwner = owner;
            }
            if (minOwner == null)
                System.out.println("Нет товаров, имеющих владельцев.");
            else {
                boolean first = true;
                for (Product product: productCollection)
                    if (product.getOwner() == minOwner) {
                        if (first) first = false;
                        else System.out.println();
                        product.printInfo(0);
                    }
            }
        } else
            System.out.println("Товаров в списке нет.");
    }

    /**
     * Возвращает описание команды в виде массива, содержащего одну строку с описанием. Используется в {@link Help}.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"вывести информацию о товарах, имя владельца которых идёт первым в алфавитном порядке"};
    }
}