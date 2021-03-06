package project.products.product_collection;

import project.products.product.Product;
import project.parsing.tags.ParentTag;

import java.util.ArrayDeque;

/**
 * Класс, реализующий этот интерфейс, должен хранить в себе коллекцию продуктов, быть итерируемым, обеспечивать взаимодействие с продуктами по их номеру и хранить дату инициализации списка.
 * @see Product
 */
public interface IProductCollection extends Iterable<Product> {
    /**
     * Возвращает ссылку на коллекцию товаров.
     * @return коллекция (двусторонняя очередь).
     */
    ArrayDeque<Product> getProducts();
    /**
     * Возвращает товар с переданным номером или null, если такого нет.
     * @param id номер (ID) товара.
     * @return объект класса {@link Product} или null.
     */
    Product getProductByID(long id);
    /**
     * Удаляет товар с указанным номером.
     * @param id номер (ID) товара.
     * @return true, если такой товар был и удалён, иначе false.
     */
    boolean removeProductByID(long id);
    /**
     * Возвращает дату инициализации в виде строки.
     * @return строковое представление даты.
     */
    String getInitializationDataString();

    /**
     * Возвращает объект {@link ParentTag}, описывающий эту коллекцию товаров.
     * @return тег с вложенными тегами даты инициализации и перечисления товаров.
     */
    ParentTag getTag();
}