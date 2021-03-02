package project;

import project.commands.command_map.CommandMap;
import project.products.product_collection.IProductCollection;
import project.products.product_collection.ProductCollection;
import project.save_and_load.SaveAndLoad;

import java.io.File;
import java.io.IOException;

/**
 * Этот класс содержит метод main.
 * @see project.save_and_load.SaveAndLoad
 * @see project.products.product_collection.ProductCollection
 * @see project.commands.command_map.CommandMap
 * @see MyReader
 * */
public class Main {
    /**
     * Загружает список товаров из указанного файла, содержит цикл ввода и исполнения команд.
     * @param args аргумент командной строки; адрес файла, из которого будет осуществляться загрузка коллекции.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка! Вы не указали файл в аргументе командной строки.");
            System.exit(1);
        }
        File file = new File(args[0]);
        if (!file.exists()) {
            System.out.println("Ошибка! Указанного файла не существует.");
            System.exit(1);
        } else if (!file.canRead()) {
            System.out.println("Ошибка! Нет права на чтение указанного файла.");
            System.exit(1);
        }

        SaveAndLoad saveAndLoad = new SaveAndLoad(file, ProductCollection.class);
        IProductCollection productCollection;
        if ((productCollection = (IProductCollection) saveAndLoad.load()) == null) {
            System.out.println("Создана новая пустая коллекция.");
            productCollection = new ProductCollection();
        } else
            System.out.println("Коллекция загружена из файла.");

        CommandMap commands = new CommandMap(productCollection, saveAndLoad);

        MyReader.init();
        while (true) {
            try {
                System.out.print("\nВведите команду: ");
                commands.execute(MyReader.readLine());
            } catch (IOException e) {
                System.out.println("\nЧто-то пошло не так. Попробуйте ещё раз.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Повторите ввод команды.");
            }
        }
    }
}