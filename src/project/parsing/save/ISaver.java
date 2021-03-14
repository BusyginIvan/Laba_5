package project.parsing.save;

import project.products.product_collection.IProductCollection;

import java.io.IOException;

/**
 * Класс, реализующий этот интерфейс, может сохранить список товаров в некоторый заранее определённый файл.
 */
public interface ISaver {
    /**
     * Сохраняет переданный список в файл.
     * @param productCollection коллекция товаров, сохраняемая в файл.
     * @throws IOException если файл не был найден или произошла ошибка при записи.
     */
    void save(IProductCollection productCollection) throws IOException;
}