package project.products.product_collection;

import com.sun.istack.internal.NotNull;
import project.products.product.Product;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/**
 * Класс-обёртка для коллекции товаров. Помимо Коллекции, представленной объектом класса {@link ArrayDeque}, хранит время создания спсика.
 * @see java.time.LocalDate
 * @see Product
 */
@XmlRootElement
public class ProductCollection implements IProductCollection {
    private ArrayDeque<Product> arrayDeque;
    private LocalDate initializationDate;

    /**
     * Создаёт пустую коллекцию и устанавливает текущее время в качестве даты создания.
     */
    public ProductCollection() {
        arrayDeque = new ArrayDeque<>();
        initializationDate = LocalDate.now();
    }

    /**
     * Возвращает продукт в переданным номером или null, если такого нет.
     * @param id номер (ID) продукта.
     * @return объект класса {@link Product} или null.
     */
    public Product getProductByID(long id) {
        for (Product product: arrayDeque) {
            if (product.getID() == id)
                return product;
        }
        return null;
    }

    /**
     * Удаляет продукт с указанным номером.
     * @param id номер (ID) продукта.
     * @return true, если такой продукт был и удалён, иначе false.
     */
    public boolean removeProductByID(long id) {
        Iterator<Product> iterator = iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getID() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Метод, позволяющий непосредственно получить итератор по списку товаров.
     * @return итератор.
     */
    public Iterator<Product> iterator() {
        return arrayDeque.iterator();
    }

    /**
     * Возвращает ссылку на коллекцию товаров.
     * @return коллекция (двусторонняя очередь).
     */
    @XmlElementWrapper(name = "project/products")
    @XmlElement(name = "product")
    public ArrayDeque<Product> getArrayDeque() {
        return arrayDeque;
    }

    /**
     * Задать коллекцию товаров.
     * @param arrayDeque объект коллекции {@link ArrayDeque}.
     */
    public void setArrayDeque(@NotNull ArrayDeque<Product> arrayDeque) {
        if (arrayDeque == null) throw new NullPointerException();
        this.arrayDeque = arrayDeque;
    }

    /**
     * Возвращает дату инициализации.
     * @return объект LocalDate, представляющий дату создания.
     */
    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    public LocalDate getInitializationDate() {
        return initializationDate;
    }

    /**
     * Усталовить дату инициализации.
     * @param initializationDate объект LocalDate, представляющий дату создания.
     */
    public void setInitializationDate(LocalDate initializationDate) {
        this.initializationDate = initializationDate;
    }

    /**
     * Возвращает дату инициализации в виде строки.
     * @return строковое представление даты.
     */
    @Override
    public String getInitializationDataString() {
        return initializationDate.toString();
    }

    /*public void sort() {
        Product[] products = arrayDeque.toArray(new Product[0]);
        Arrays.sort(products);
        arrayDeque.clear();
        Collections.addAll(arrayDeque, products);
    }*/
}