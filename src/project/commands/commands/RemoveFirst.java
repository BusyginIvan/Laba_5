package project.commands.commands;

import project.products.product_collection.IProductCollection;

/**
 * Команда удаления первого товара из списка.
 * @see project.commands.command_map.CommandMap
 */
public class RemoveFirst implements ICommand {
    IProductCollection productCollection;

    /**
     * Эта команда будет работать с переданной в конструктор коллекцией.
     * @param productCollection коллеция товаров.
     */
    public RemoveFirst(IProductCollection productCollection) {
        this.productCollection = productCollection;
    }

    /**
     * Удаляет первый товар из списка.
     * @param arguments массив аргументов команды (не имеет значения).
     */
    @Override
    public void execute(String[] arguments) {
        if (productCollection.getProducts().poll() == null)
            System.out.println("\nСписок и так был пуст.");
        else
            System.out.println("\nПервый товар удалён.");
    }

    /**
     * Возвращает описание команды в виде массива, содержащего одну строку с описанием. Используется в {@link Help}.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"удалить первый товар в списке"};
    }
}