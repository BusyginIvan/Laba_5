package project.products.product;

import com.sun.istack.internal.NotNull;
import project.parsing.tags.InvalidTagException;
import project.parsing.tags.ParentTag;
import project.parsing.tags.TextTag;

import java.util.Objects;

import static project.products.ElementBuilder.getLine;
import static project.products.ElementBuilder.setField;

/**
 * Класс с информацией о месте нахождения.
 * @see Person
 */
public class Location {
    private float x;
    private Integer y;
    private Long z;
    private String name;

    private Location() {}

    /**
     * Поочерёдно запрашивает значения всех полей у пользователя. Создаёт новую локацию или возвращает null, если
     * пользователь не стал вводить название локации.
     * @return новая локация или null.
     */
    public static Location newLocation() {
        Location location = new Location();
        location.name = getLine(
                "Введите название локации или оставьте строку пустой",
                str -> str.equals("") ? null : str);
        if (location.name == null) return null;

        setField("Введите вещественную координату x", str2 -> location.setX(Float.parseFloat(str2)));
        setField("Введите целую координату y", str2 -> location.setY(Integer.parseInt(str2)));
        setField("Введите целую координату z", str2 -> location.setZ(Long.parseLong(str2)));

        return location;
    }

    /**
     * Получает значения характеристик локации из тега.
     * @param locationTag тег с вложенными тегами name, x, y, z.
     * @exception InvalidTagException если тег не содержит необходимых вложенных тегов или
     * если в попавшихся в нём тегах координат неверно записано число.
     * @return новая локация или null, если в качестве тега был передан null.
     */
    public static Location newLocation(ParentTag locationTag) {
        if (locationTag == null)
            return null;
        String className = "Location";
        String fieldName = "x";
        try {
            Location location = new Location();
            location.setX(Float.parseFloat(locationTag.getNestedTagContent(fieldName)));
            location.setY(Integer.parseInt(locationTag.getNestedTagContent((fieldName = "y"))));
            location.setZ(Long.parseLong(locationTag.getNestedTagContent((fieldName = "z"))));
            location.setName(Objects.requireNonNull(locationTag.getNestedTagContent((fieldName = "name"))));
            return location;
        } catch (NullPointerException e) {
            throw new InvalidTagException(className, " Отсутствует тег для поля " + fieldName + ".");
        } catch (NumberFormatException e) {
            throw new InvalidTagException(className, "В теге неверно указано значение координаты " + fieldName + ".");
        } catch (InvalidTagException | IllegalArgumentException e) {
            throw new InvalidTagException(className, e.getMessage());
        }
    }

    /**
     * Метод для получения тега, описывающего эту локацию.
     * @return тег location, содержащий теги, соответствующие каждому полю класса.
     */
    public ParentTag getTag() {
        ParentTag parentTag = new ParentTag("location");
        parentTag.addTextTag(new TextTag("name", name));
        parentTag.addTextTag(new TextTag("x", x + ""));
        parentTag.addTextTag(new TextTag("y", y + ""));
        parentTag.addTextTag(new TextTag("z", z + ""));
        return parentTag;
    }

    /**
     * Возвращает координату x.
     * @return вещественная координата.
     */
    public float getX() {
        return x;
    }

    /**
     * Возвращает координату y.
     * @return целочисленная координата.
     */
    public Integer getY() {
        return y;
    }

    /**
     * Возвращает координату z.
     * @return целочисленная координата.
     */
    public Long getZ() {
        return z;
    }

    /**
     * Возвращает название локации.
     * @return строковое название.
     */
    public String getName() {
        return name;
    }

    /**
     * Задаёт координату x.
     * @param x вещественная координата.
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Задаёт координату y.
     * @param y целочисленная координата.
     */
    public void setY(@NotNull Integer y) throws NullPointerException {
        if (y == null) throw new NullPointerException();
        this.y = y;
    }

    /**
     * Задаёт координату z.
     * @param z целочисленная координата.
     */
    public void setZ(@NotNull Long z) throws NullPointerException {
        if (z == null) throw new NullPointerException();
        this.z = z;
    }

    /**
     * Задаёт название локации.
     * @param name название.
     * @throws NullPointerException бросает, если передан null.
     */
    public void setName(@NotNull String name) throws NullPointerException {
        if (name == null) throw new NullPointerException();
        this.name = name;
    }

    /**
     * Проверка на равенсто другой локации.
     * @param o объект некоторого класса для сравнения.
     * @return true, если передан объект класса Location и значения всех полей совпадают.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Float.compare(location.x, x) == 0 &&
                y.equals(location.y) &&
                z.equals(location.z) &&
                name.equals(location.name);
    }
}