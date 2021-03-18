package project.products.product;

import com.sun.istack.internal.NotNull;
import project.parsing.tags.DuplicateTagException;
import project.parsing.tags.InvalidTagException;
import project.parsing.tags.ParentTag;
import project.parsing.tags.TextTag;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static project.products.ElementBuilder.setField;

/**
 * Класс, хранящий в себе информацию о некотором товаре.
 * @see project.products.product_collection.IProductCollection
 * @see Person
 * @see UnitOfMeasure
 * @see Coordinates
 * @see Location
 */
public class Product implements Comparable<Product> {
    private final long ID;
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
     * Поочерёдно запрашивает значения всех полей у пользователя.
     */
    public Product() {
        update();
    }

    /**
     * Поочерёдно запрашивает значения всех полей у пользователя и заменяет ими старые.
     */
    public void update() {
        setField("Введите наименование товара", this::setName);
        Coordinates coordinates = new Coordinates();
        setCoordinates(coordinates);
        setField("Введите цену на товар", str -> setPrice(Double.parseDouble(str)));
        setField("Введите единицу измерения (KILOGRAMS / SQUARE_METERS / LITERS / GRAMS) или оставьте строку пустой",
                str -> setUnitOfMeasure(str.equals("") ? null : UnitOfMeasure.valueOf(str)));
        setOwner(Person.newPerson("владельца"));
    }

    /**
     * Получает значения характеристик товара из тега.
     * @param productTag тег с вложенными тегами name, coordinates, creationDate, price и, возможно, unitOfMeasure и owner.
     * @exception InvalidTagException если тег не содержит необходимых вложенных тегов или если в них некорректные данные.
     */
    public Product(ParentTag productTag) {
        String className = "Product";
        try {
            String fieldName;
            if (!productTag.getArguments().containsKey(fieldName = "name"))
                throw new InvalidTagException(className, "У тега нет аргумента " + fieldName + ".");
            setName(productTag.getArguments().get(fieldName));

            try {
                setCoordinates(new Coordinates(requireNonNull(productTag.getNestedParentTag(fieldName = "coordinates"), fieldName)));
                setCreationDate(LocalDateTime.parse(requireNonNull(productTag.getNestedTagContent(fieldName = "creationDate"), fieldName)));
                setPrice(Double.parseDouble(requireNonNull(productTag.getNestedTagContent(fieldName = "price"), fieldName)));
                setOwner(Person.newPerson(productTag.getNestedParentTag("owner")));
            } catch (NullPointerException e) {
                throw new InvalidTagException(className, "Отсутствует тег для поля " + e.getMessage() + ".");
            } catch (DateTimeParseException | NumberFormatException e) {
                throw new InvalidTagException(className, "Неверно записано значение поля " + fieldName + ".");
            } catch (DuplicateTagException e) {
                throw new InvalidTagException(className, e.getMessage());
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidTagException(e.getMessage());
        }

        try {
            String content = productTag.getNestedTagContent("unitOfMeasure");
            setUnitOfMeasure(content == null ? null : UnitOfMeasure.valueOf(content));
        } catch (IllegalArgumentException e) {
            throw new InvalidTagException(className, "Некорректная единица измерения товара.");
        }
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
     * Позволяет указать владельца товара.
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
                s + " Владелец: " + (owner == null ? "отсутствует" : owner.toString()));
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