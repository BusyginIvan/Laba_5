package project.products.product;

import com.sun.istack.internal.NotNull;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.util.Arrays;

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
     * @see DateAdapter
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
     * Задаёт координаты товара.
     * @param coordinates объект класса Coordinates, с новой парой координат x и y.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Задаёт дату и время создания товара.
     * @param creationDate объект, представляющий дату и время производства.
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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
                s + " x: " + coordinates.getX() + "\n" +
                s + " y: " + coordinates.getY() + "\n" +
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

/**
 * Этот класс предоставляет для JAXB возможность конвертировать объект LocalDateTime в строковое представление и обратно.
 */
class DateAdapter extends XmlAdapter<String, LocalDateTime> {
    /**
     * Преобразует строку в объект даты и времени.
     * @param s строковое представление даты и времени (например, 2021-02-21T20:43:16.367).
     * @return объект класса даты и времени.
     */
    @Override
    public LocalDateTime unmarshal(String s) {
        return LocalDateTime.parse(s);
    }

    /**
     * Преобразует объект даты и времени в строку. Если в качестве объекта передан null, вернёт строку "null".
     * @param localDateTime конвертируемый объект класса даты и времени.
     * @return строковое представление даты и времени (например, 2021-02-21T20:43:16.367).
     */
    @Override
    public String marshal(LocalDateTime localDateTime) {
        if (localDateTime == null) return "null";
        return localDateTime.toString();
    }
}