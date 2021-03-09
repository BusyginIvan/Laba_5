package project.products.product;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

/**
 * Класс, содержащий информацию о человеке: имя, номер паспорта, рост (если он имеет значение), масса и место нахождения (если оно имеет значение).
 * @see Product
 * @see Location
 */
public class Person implements Comparable<Person> {
    private String name;
    private Float height;
    private float weight;
    private String passportID;
    private Location location;

    private static ArrayList<String> usedPassportID = new ArrayList<>();

    /**
     * Позволяет указать имя человека.
     * @param name новое имя.
     * @throws IllegalArgumentException бросает, если передана пустая строка или null.
     */
    public void setName(@NotNull String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Имя человека должно быть представлено непустой строкой!");
        this.name = name;
    }

    /**
     * Позволяет указать рост.
     * @param height рост в метрах или null.
     * @throws IllegalArgumentException бросает, если передано неположительное число.
     */
    public void setHeight(Float height) throws IllegalArgumentException {
        if (height == null)  {
            this.height = null;
            return;
        }
        if (height <= 0)  throw new IllegalArgumentException("Рост человека не может быть меньше или равен нулю!");
        this.height = height;
    }

    /**
     * Позволяет указать массу.
     * @param weight масса в килограммах.
     * @throws IllegalArgumentException бросает, если передано неположительное число.
     */
    public void setWeight(float weight) throws IllegalArgumentException {
        if (weight <= 0)  throw new IllegalArgumentException("Масса человека не может быть меньше или равна нулю!");
        this.weight = weight;
    }

    /**
     * Позволяет указать номер паспорта. Номер должен быть уникальным (ранее не использовавшимся).
     * @param passportID номер паспорта.
     * @throws IllegalArgumentException бросает, если передан null, строка менее чем из 4 символов или уже использовавшийся номер.
     */
    public void setPassportID(@NotNull String passportID) throws IllegalArgumentException {
        if (passportID == null || passportID.length() < 4)
            throw new IllegalArgumentException("Номер паспорта должен быть представлен строкой не менее чем из 4 символов!");
        if (usedPassportID.contains(passportID))
            throw new IllegalArgumentException("Номер паспорта должен быть уникальным!");
        usedPassportID.remove(this.passportID);
        usedPassportID.add(passportID);
        this.passportID = passportID;
    }

    /**
     * Позволяет указать место нахождения (локацию).
     * @param location объект локации или null.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Возвращает имя.
     * @return имя.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает рост.
     * @return рост в метрах.
     */
    public Float getHeight() {
        return height;
    }

    /**
     * Возвращает массу.
     * @return масса в кг.
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Возвращает номер паспорта.
     * @return номер паспорта.
     */
    public String getPassportID() {
        return passportID;
    }

    /**
     * Возвращает локацию.
     * @return объект класса, представляющего место нахождения человека, или null.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Сравнивает с другим человеком по положению имён в алфавитном порядке.
     * @param person другой человек.
     * @return число меньше нуля, если имя этого человека предшествует имени другого; равное нулю, если они тёзки; больше нуля, если имя этого человека следует за именем другого.
     */
    @Override
    public int compareTo(Person person) {
        return name.compareTo(person.getName());
    }

    /**
     * Проверяет, представляет ли другой объект этого же человека.
     * @param o объект некоторого класса.
     * @return true, если объект ссылается на того же человека.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return passportID.equals(((Person) o).passportID);
    }
}