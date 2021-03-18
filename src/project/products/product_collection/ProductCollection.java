package project.products.product_collection;

import project.ConsoleReader;
import project.parsing.tags.DuplicateTagException;
import project.parsing.tags.InvalidTagException;
import project.products.product.NotUniquePassportIDException;
import project.products.product.Person;
import project.products.product.Product;
import project.parsing.tags.ParentTag;
import project.parsing.tags.TextTag;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

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
     * @param productCollectionTag тег с вложенными тегами initializationDate и products.
     * @exception InvalidTagException если тег не содержит необходимых вложенных тегов или в них некорректные данные.
     */
    public ProductCollection(ParentTag productCollectionTag) {
        try {
            setInitializationDate(LocalDate.parse(productCollectionTag.getNestedTagContent("initializationDate")));
        } catch (DateTimeParseException e) {
            throw new InvalidTagException("ProductCollection", "Неверно записана дата инициализации.");
        } catch (NullPointerException e) {
            throw new InvalidTagException("ProductCollection", " Отсутствует тег с датой инициализации.");
        } catch (DuplicateTagException e) {
            throw new InvalidTagException("ProductCollection", e.getMessage());
        }

        products = new ArrayDeque<>();
        boolean wasIDConflict = false;
        boolean saveProducts = false;
        HashSet<String> invalidID = new HashSet<>();
        HashSet<String> newID = new HashSet<>();
        ParentTag productsTag;
        try {
            productsTag = productCollectionTag.getNestedParentTag("products");
        } catch (InvalidTagException e) {
            throw new InvalidTagException("ProductCollection", e.getMessage());
        }
        for (ParentTag tagInProducts: productsTag.getParentTags()) {
            if (tagInProducts.getName().equals("product")) {
                try {
                    ParentTag tagOwner = tagInProducts.getNestedParentTag("owner");
                    if (tagOwner == null)
                        products.add(new Product(tagInProducts));
                    else {
                        try {
                            String ID;
                            if (invalidID.contains((ID = Person.newPerson(tagOwner).getPassportID()))) {
                                if (Person.lastIsNew())
                                    Person.removePerson(ID);
                                if (saveProducts) {
                                    tagInProducts.removeNestedParentTag("owner");
                                    products.add(new Product(tagInProducts));
                                }
                            } else {
                                if (Person.lastIsNew())
                                    newID.add(ID);
                                products.add(new Product(tagInProducts));
                            }
                        } catch (NotUniquePassportIDException e) {
                            invalidID.add(e.getPassportID());

                            if (!wasIDConflict) {
                                wasIDConflict = true;
                                System.out.print("При загрузке товаров в коллекцию из файла, были обнаружены такие," +
                                        " у которых владельцы имеют совпадающие номера паспортов. Желаете ли вы сохранять" +
                                        " эти товары (без информации о владельце)? Для подтверждения введите \"ok\": ");
                                try {
                                    if (ConsoleReader.readLine().equals("ok"))
                                        saveProducts = true;
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }

                            if (newID.contains(e.getPassportID()))
                                Person.removePerson(e.getPassportID());
                            Iterator<Product> iterator = products.iterator();
                            if (saveProducts) {
                                while (iterator.hasNext()) {
                                    Product product = iterator.next();
                                    if (product.getOwner() != null && product.getOwner().getPassportID().equals(e.getPassportID()))
                                        product.setOwner(null);
                                }
                                tagInProducts.removeNestedParentTag("owner");
                                products.add(new Product(tagInProducts));
                            } else {
                                while (iterator.hasNext()) {
                                    Person owner = iterator.next().getOwner();
                                    if (owner != null && owner.getPassportID().equals(e.getPassportID()))
                                        iterator.remove();
                                }
                            }
                        }
                    }
                } catch (DuplicateTagException e) {
                    System.out.println("Ошибка в структуре тега product. " + e.getMessage());
                } catch (InvalidTagException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Метод для получения тега, описывающего эту коллекцию.
     * @return тег productCollection, содержащий теги initializationDate и products, в которых, в свою очередь,
     * содержатся дата инициализации и теги товаров соответственно.
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
     * Возвращает товар с переданным номером или null, если такого нет.
     * @param id номер (ID) товара.
     * @return ссылка на объект класса {@link Product} или null.
     */
    public Product getProductByID(long id) {
        for (Product product: products) {
            if (product.getID() == id)
                return product;
        }
        return null;
    }

    /**
     * Удаляет товар с указанным номером.
     * @param id номер (ID) товара.
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

    /**
     * Возвращает дату инициализации.
     * @return объект LocalDate, представляющий дату создания.
     */
    public LocalDate getInitializationDate() {
        return initializationDate;
    }

    /**
     * Устанавливает дату инициализации.
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

    /**
     * Сортирует коллекцию.
     */
    public void sort() {
        Product[] productsArray = products.toArray(new Product[0]);
        Arrays.sort(productsArray);
        products.clear();
        Collections.addAll(products, productsArray);
    }
}