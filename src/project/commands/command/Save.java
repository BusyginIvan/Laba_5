package project.commands.command;

import project.products.product_collection.IProductCollection;
import project.save_and_load.ISaver;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

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
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка! Файл для сохранения не найден.");
        } catch (JAXBException e) {
            System.out.println("При попытке декодировать xml произошла ошибка.");
        } catch (SecurityException e) {
            System.out.println("Ошибка! Отсутствуют права доступа к файлу.");
        }
    }

    /**
     * Возвращает описание команды в виде массива строк: первые элементы - названия аргументов, последний - строка,
     * описывающая команду.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"сохранить коллекцию в файл"};
    }
}