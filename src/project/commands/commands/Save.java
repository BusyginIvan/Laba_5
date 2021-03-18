package project.commands.commands;

import project.products.product_collection.IProductCollection;
import project.parsing.save.ISaver;

import java.io.IOException;

/**
 * Команда сохранения списка товаров в файл.
 * @see project.commands.command_map.CommandMap
 * @see ISaver
 */
public class Save implements ICommand {
    IProductCollection productCollection;
    ISaver saver;

    /**
     * Команда будет сохранять переданную в конструктор коллекцию, применяя saver.
     * @param productCollection коллеция товаров.
     * @param saver объект, сохраняющий коллекцию в нужный файл.
     */
    public Save(IProductCollection productCollection, ISaver saver) {
        this.productCollection = productCollection;
        this.saver = saver;
    }

    /**
     * Сохраняет коллекцию товаров с файл. При возникновении ошибок, сообщает о них пользователю.
     * @param arguments массив аргументов команды (не имеет значения).
     */
    @Override
    public void execute(String[] arguments) {
        try {
            saver.save(productCollection);
            System.out.println("Коллекция сохранена.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Возвращает описание команды в виде массива, содержащего одну строку с описанием. Используется в {@link Help}.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"сохранить коллекцию в файл"};
    }
}