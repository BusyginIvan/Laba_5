package project.products.product_collection;

import project.products.InvalidTagException;
import project.products.product.ContainsPassportID;
import project.products.product.Person;
import project.products.product.Product;
import project.parsing.tags.ParentTag;
import project.parsing.tags.TextTag;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Класс-обёртка для коллекции товаров. Помимо Коллекции, представленной объектом класса {@link ArrayDeque}, хранит время создания спсика.
 * @see java.time.LocalDate
 * @see Product
 */
public class ProductCollection implements IProductCollection {
    private ArrayDeque<Product> products;
    private LocalDate initializationDate;

    /**
     * Создаёт пустую коллекцию и устанавливает текущее время в качестве даты создания.
     */
    public ProductCollection() {
        products = new ArrayDeque<>();
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

        products = new ArrayDeque<>();
        for (ParentTag tagInProductCollection: productCollectionTag.getParentTags())
            if (tagInProductCollection.getName().equals("products")) {
                HashSet<ParentTag> tagsWithInvalidOwner = new HashSet<>();
                for (ParentTag tagInProducts: tagInProductCollection.getParentTags())
                    if (tagInProducts.getName().equals("product")) {
                        try {
                            products.add(new Product(tagInProducts));
                        } catch (ContainsPassportID e) {
                            tagsWithInvalidOwner.add(tagInProducts);
                        } catch (InvalidTagException e) {}
                    }
                if (tagsWithInvalidOwner.size() > 0) {
                    System.out.print("При загрузке товаров в коллекцию из файла, были обнаружены такие, у которых владельцы имеют совпадающие ");
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
        for (Product product: products)
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
        for (Product product: products) {
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
        return products.iterator();
    }

    /**
     * Возвращает ссылку на коллекцию товаров.
     * @return коллекция (двусторонняя очередь).
     */
    public ArrayDeque<Product> getProducts() {
        return products;
    }

    /*/**
     * Задать коллекцию товаров.
     * @param arrayDeque объект коллекции {@link ArrayDeque}.
     *
    public void setArrayDeque(@NotNull ArrayDeque<Product> arrayDeque) {
        if (arrayDeque == null) throw new NullPointerException();
        this.arrayDeque = arrayDeque;
    }*/

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