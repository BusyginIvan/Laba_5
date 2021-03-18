package project.parsing.load;

import project.parsing.tags.InvalidTagException;
import project.products.product_collection.ProductCollection;
import project.parsing.tags.ParentTag;
import project.parsing.tags.TagMatcher;

import java.io.*;
import java.util.Iterator;

/**
 * Класс для чтения списка товаров из файла формата XML.
 */
public class Loader {
    /**
     * Возвращает коллекцию товаров, созданную из файла по указанному пути.
     * @param path файл для чтения.
     * @return коллекция продуктов.
     * @exception LoadException если файл не найден или имеет некорректное содержимое.
     */
    public static ProductCollection load(String path) {
        try {
            File file = new File(path);
            if (!file.exists())
                throw new LoadException("Ошибка загрузки! Файл не найден.");
            if (!file.canRead())
                throw new LoadException("Ошибка загрузки! Нет прав на чтение файла.");
            Iterator<String> iterator = new BufferedReader(new FileReader(file)).lines().iterator();
            String result = "";
            while (iterator.hasNext())
                result += iterator.next() + "\n";
            TagMatcher tagMatcher = new TagMatcher(result);
            if (tagMatcher.findTag()) {
                if (tagMatcher.haveTag())
                    return new ProductCollection(new ParentTag(tagMatcher));
                else
                    throw new LoadException("Ошибка загрузки! Корневой тег не имеет должного содержимого.");
            } else
                throw new LoadException("Ошибка загрузки! Файл не содержит корневого тега.");
        } catch (FileNotFoundException e) {
            throw new LoadException("Ошибка загрузки! Файл не найден.");
        } catch (InvalidTagException e) {
            throw new LoadException(e.getMessage());
        }
    }
}