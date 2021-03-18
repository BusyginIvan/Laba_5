package project.commands.commands;

import project.products.product_collection.IProductCollection;

/**
 * Команда, выводящая информацию о коллекции товаров.
 * @see project.commands.command_map.CommandMap
 */
public class Info implements ICommand {
    private IProductCollection productCollection;

    /**
     * Эта команда будет выводить информацию о переданной в конструктор коллекции.
     * @param productCollection коллеция товаров.
     */
    public Info(IProductCollection productCollection) {
        this.productCollection = productCollection;
    }

    /**
     * Выводит информацию о списке товаров: дата инициализации и количество элементов.
     * @param arguments массив аргументов команды (не имеет значения).
     */
    @Override
    public void execute(String[] arguments) {
        System.out.print("Коллекция товаров:\n" +
                " Дата инициализации: " + productCollection.getInitializationDataString() + "\n" +
                " Количетво элементов: " + productCollection.getProducts().size() + "\n");
    }

    /**
     * Возвращает описание команды в виде массива, содержащего одну строку с описанием. Используется в {@link Help}.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"вывести информацию о коллекции"};
    }
}