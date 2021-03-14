package project.parsing.save;

import project.products.product_collection.IProductCollection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Класс для сохранения списка товаров в формате XML.
 */
public class Saver implements ISaver {
    private File file;

    /**
     * Конструктор, позволяющий создать объект класса для работы с конкретным файлом.
     * @param path путь к файлу для записи.
     */
    public Saver(String path) {
        file = new File(path);
    }

    /**
     * Сохраняет переданный список в файл.
     * @param productCollection коллекция товаров.
     * @throws IOException если файл не был найден или произошла ошибка при записи.
     */
    public void save(IProductCollection productCollection) throws IOException {
        save(file, productCollection);
    }

    /**
     * Сохраняет переданный список в указанный файл.
     * @param file файл для сохранения коллекции.
     * @param productCollection коллекция товаров.
     * @throws IOException если файл не был найден или произошла ошибка при записи.
     */
    public static void save(File file, IProductCollection productCollection) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + productCollection.getTag().toText()).getBytes());
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                System.out.println("Чего-то поток записи не закрылся...");
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Ошибка при сохранении коллекции! Файл не найден.");
        } catch (IOException e) {
            throw new IOException("Ошибка при сохранении коллекции! Не удалось записать данные в файл.");
        }
    }
}