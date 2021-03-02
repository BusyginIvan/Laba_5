package project.commands.command;

import project.products.product_collection.IProductCollection;

/**
 * Команда очищения спика товаров.
 * @see project.commands.command_map.CommandMap
 */
public class Clear implements ICommand {
    IProductCollection productCollection;

    public Clear(IProductCollection productCollection) {
        this.productCollection = productCollection;
    }

    /**
     * Очищает список товаров.
     * @param arguments массив аргументов команды (не имеет значения).
     */
    @Override
    public void execute(String[] arguments) {
        System.out.println("\nСписок очищен.");
        productCollection.getArrayDeque().clear();
    }

    /**
     * Возвращает описание команды в виде массива строк: первые элементы - названия аргументов, последний - строка,
     * описывающая команду.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"очистить список товаров"};
    }
}