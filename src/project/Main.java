package project;

import project.commands.command_map.CommandMap;
import project.commands.commands.CommandException;
import project.products.product_collection.IProductCollection;
import project.products.product_collection.ProductCollection;
import project.parsing.load.LoadException;
import project.parsing.load.Loader;
import project.parsing.save.Saver;

import java.io.IOException;

/**
 * Этот класс содержит метод main.
 * @see Loader
 * @see Saver
 * @see project.products.product_collection.ProductCollection
 * @see project.commands.command_map.CommandMap
 * @see ConsoleReader
 * */
public class Main {
    /**
     * Обеспечивает загрузку коллекции из файла или создание новой в случае неудачи, содержит цикл ввода и исполнения команд.
     * @param args аргументы командной строки; args[0] - адрес файла, из которого будет осуществляться загрузка коллекции.
     * @exception IOException если была ошибка при чтении подтверждения продолжения работы с новой пустой коллекцией.
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Ошибка! Вы не указали файл в аргументе командной строки.");
            System.exit(1);
        }

        ConsoleReader.init();

        IProductCollection productCollection = new ProductCollection();
        try {
            productCollection = Loader.load(args[0]);
            System.out.println("\nКоллекция загружена из файла.");
        } catch (LoadException e) {
            System.out.print(e.getMessage() +
                    "\n\nЖелаете ли вы начать работу с новым пустым списком товаров?\n" +
                    "Для подтверждения введите \"ok\": ");
            if (!ConsoleReader.readLine().equals("ok"))
                exit();
        }

        CommandMap commands = new CommandMap(productCollection, new Saver(args[0]));
        while (true) {
            try {
                System.out.print("\nВведите команду: ");
                commands.execute(ConsoleReader.readLine());
            } catch (IOException e) {
                System.out.println("\nЧто-то пошло не так. Попробуйте ещё раз.");
            } catch (CommandException e) {
                System.out.println(e.getMessage());
                System.out.println("Повторите ввод команды.");
            } catch (NullPointerException e) {
                System.out.println("\n");
                exit();
            }
        }
    }

    /**
     * Закрывает стандартный поток ввода и завершает работу программы.
     */
    public static void exit() {
        System.out.println("Завершение работы программы.");
        ConsoleReader.close();
        System.exit(0);
    }
}