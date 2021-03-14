package project.products.product_collection;

import com.sun.istack.internal.NotNull;
import project.products.InvalidTagException;
import project.products.product.Product;
import project.parsing.tags.ParentTag;
import project.parsing.tags.TextTag;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayDeque;
import java.util.Iterator;

/**
 * Класс-обёртка для коллекции товаров. Помимо Коллекции, представленной объектом класса {@link ArrayDeque}, хранит время создания спсика.
 * @see java.time.LocalDate
 * @see Product
 */
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
     * Создаёт коллекцию, получая данные из тега.
     * @param productCollectionTag тег, описывающий коллекцию товаров.
     * @exception InvalidTagException если тег не содержит необходимых вложенных тегов или в них некорректные данные.
     */
    public ProductCollection(ParentTag productCollectionTag) {
        date: try {
            String name = "initializationDate";
            for (TextTag element : productCollectionTag.getTextTags())
                if (element.getName().equals(name)) {
                    setInitializationDate(LocalDate.parse(element.getContent()));
                    break date;
                }
            throw new InvalidTagException(this.getClass(), "Отсутствует тег для поля " + name + ".");
        } catch (DateTimeParseException e) {
            throw new InvalidTagException(this.getClass(), "Неверно записана дата инициализации.");
        }

        arrayDeque = new ArrayDeque<>();
        for (ParentTag tagInProductCollection: productCollectionTag.getParentTags())
            if (tagInProductCollection.getName().equals("products")) {
                for (ParentTag tagInProducts: tagInProductCollection.getParentTags())
                    if (tagInProducts.getName().equals("product")) {
                        try {
                            arrayDeque.add(new Product(tagInProducts));
                        } catch (InvalidTagException e) {}
                    }
                return;
            }
    }

    /**
     * Метод для получения тега, описывающего эту коллекцию.
     * @return тег productCollection, содержащий теги initializationDate и products, в которых, в свою очередь, содержатся дата
     * инициализации и теги товаров соответственно.
     */
    public ParentTag getTag() {
        ParentTag productCollectionTag = new ParentTag("productCollection");
        productCollectionTag.addTextTag(new TextTag("initializationDate", initializationDate.toString()));
        ParentTag productsTag = new ParentTag("products");
        for (Product product: arrayDeque)
            productsTag.addParentTag(product.getTag());
        productCollectionTag.addParentTag(productsTag);
        return productCollectionTag;
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