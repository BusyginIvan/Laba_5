package project.products.product;

import com.sun.istack.internal.NotNull;
import project.products.InvalidTagException;
import project.parsing.tags.ParentTag;
import project.parsing.tags.TextTag;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;

/**
 * Класс, хранящий в себе информацию о некотором товаре.
 * @see project.products.product_collection.IProductCollection
 * @see Person
 * @see UnitOfMeasure
 * @see Coordinates
 * @see Location
 */
public class Product implements Comparable<Product> {
    private long ID;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private double price;
    private UnitOfMeasure unitOfMeasure;
    private Person owner;

    private static long nextID = 1;

    {
        ID = nextID++;
        creationDate = LocalDateTime.now();
    }

    /**
     * Пустой конструктор. Используется в {@link project.products.ElementBuilder}.
     */
    public Product() { }

    /**
     * Получает значения характеристик товара из тега.
     * @param productTag тег с вложенными тегами name, coordinates, creationDate, price и, возможно, unitOfMeasure и owner.
     * @exception InvalidTagException если тег не содержит необходимых вложенных тегов или если в них некорректные данные.
     */
    public Product(ParentTag productTag) {
        try {
            name: {
                for (String key : productTag.getArguments().keySet())
                    if (key.equals("name")) {
                        setName(productTag.getArguments().get(key));
                        break name;
                    }
                throw new InvalidTagException(this.getClass(), "У тега нет аргумента имени.");
            }
            String fieldName;
            setCoordinates(new Coordinates(requireNonNull(getNestedParentTag(productTag, (fieldName = "coordinates")), fieldName)));
            try {
                setCreationDate(LocalDateTime.parse(requireNonNull(getTextFromNestedTag(productTag, (fieldName = "creationDate")), fieldName)));
            } catch (DateTimeParseException e) {
                throw new InvalidTagException(this.getClass(), "Неверно записана дата производства.");
            }
            try {
                setPrice(Double.valueOf(requireNonNull(getTextFromNestedTag(productTag, (fieldName = "price")), fieldName)));
            } catch (NumberFormatException e) {
                throw new InvalidTagException(this.getClass(), "Цена записана некорректно.");
            }
            for (TextTag element: productTag.getTextTags())
                if (element.getName().equals("unitOfMeasure")) {
                    setUnitOfMeasure(UnitOfMeasure.valueOf(element.getContent()));
                    break;
                }
            for (ParentTag element: productTag.getParentTags())
                if (element.getName().equals("owner")) {
                    setOwner(new Person(element));
                    break;
                }
        } catch (IllegalArgumentException e) {
            throw new InvalidTagException(e.getMessage());
        } catch (NullPointerException e) {
            throw new InvalidTagException(this.getClass(), "Отсутствует тег для поля " + e.getMessage() + ".");
        }
    }

    private static String getTextFromNestedTag(ParentTag tag, String name) {
        for (TextTag element: tag.getTextTags())
            if (element.getName().equals(name))
                return element.getContent();
        return null;
    }

    private static ParentTag getNestedParentTag(ParentTag tag, String tagName) {
        for (ParentTag element: tag.getParentTags())
            if (element.getName().equals(tagName))
                return element;
        return null;
    }

    /**
     * Метод для получения тега, описывающего этот товар.
     * @return тег product, содержащий теги, соответствующие каждому непустому полю класса.
     */
    public ParentTag getTag() {
        ParentTag parentTag = new ParentTag("product");
        parentTag.addArgument("name", name);
        parentTag.addParentTag(coordinates.getTag());
        parentTag.addTextTag(new TextTag("creationDate", creationDate.toString()));
        parentTag.addTextTag(new TextTag("price", price + ""));
        if (!(unitOfMeasure == null))
            parentTag.addTextTag(new TextTag("unitOfMeasure", unitOfMeasure.toString()));
        if (!(owner == null))
            parentTag.addParentTag(owner.getTag());
        return parentTag;
    }

    /**
     * Задаёт наименование товара.
     * @param name новое название.
     * @throws IllegalArgumentException бросает, если передан null или пустая строка.
     */
    public void setName(@NotNull String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Наименование продукта должно быть представлено непустой строкой!");
        this.name = name;
    }

    /**
     * Устанавливает цену на товар.
     * @param price новая цена.
     * @throws IllegalArgumentException бросает, если передано неположительное число.
     */
    public void setPrice(double price) throws IllegalArgumentException {
        if (price <= 0) throw new IllegalArgumentException("Цена должна быть положительным числом!");
        this.price = price;
    }

    /**
     * Задаёт единицу измерения товара.
     * @param unitOfMeasure enum, единица измерения. Может быть null (измеряется в штуках).
     */
    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    /**
     * ПРозволяет указать владельца товара.
     * @param owner новый владелец (или null).
     */
    public void setOwner(Person owner) {
        this.owner = owner;
    }

    /**
     * Задаёт координаты товара.
     * @param coordinates объект класса Coordinates, с новой парой координат x и y.
     */
    public void setCoordinates(Coordinates coordinates) throws IllegalArgumentException {
        if (coordinates == null)
            throw new IllegalArgumentException("null - недопустимое значение координат товара!");
        this.coordinates = coordinates;
    }

    /**
     * Задаёт дату и время создания товара.
     * @param creationDate объект, представляющий дату и время производства.
     */
    public void setCreationDate(LocalDateTime creationDate) {
        if (creationDate == null)
            throw new IllegalArgumentException("null - недопустимое значение даты производства товара!");
        this.creationDate = creationDate;
    }

    /**
     * Возвращает уникальный номер товара.
     * @return номер товара.
     */
    public long getID() {
        return ID;
    }

    /**
     * Возвращает наименование товара.
     * @return название товара.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает класс с парой координат x и y этого товара.
     * @return класс координат.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Возвращает объект класса, представляющего время создания товара.
     * @return дата и время производства.
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Возвращает цену на товар.
     * @return цена.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Возвращает единицу измерения товара.
     * @return объект enum или null.
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Возвращает объект класса Person, представляющий владельца товара.
     * @return объект класса Person или null.
     */
    public Person getOwner() {
        return owner;
    }

    /**
     * Выводит информацию о продукте в стандортный поток.
     * @param indent отступ от левого края (число пробелов).
     */
    public void printInfo(int indent) {
        char[] chars = new char[indent];
        Arrays.fill(chars, ' ');
        String s = new String(chars);
        System.out.println(s + "Продукт №" + ID + ":\n" +
                s + " Наименование: " + name + "\n" +
                s + " Координаты:" + "\n" +
                s + "  x: " + coordinates.getX() + "\n" +
                s + "  y: " + coordinates.getY() + "\n" +
                s + " Дата создания: " + creationDate.toString().replace("T", "  ") + "\n" +
                s + " Цена: " + price + "\n" +
                s + " Единица измерения: " + (unitOfMeasure == null ? "-" : unitOfMeasure) + "\n" +
                s + " Владелец: " + (owner == null ? "отсутствует" : owner.getName()));
    }

    /**
     * Сравнивает этот товар с друим по цене.
     * @param other объект другого товара.
     * @return число меньше нуля, если цена этого товара меньше; равное нулю, если равна; больше нуля, если цена этого товара больше.
     */
    @Override
    public int compareTo(Product other) {
        return (int) (price - other.getPrice());
    }
}