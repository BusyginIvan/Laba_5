package project.products.product;

import com.sun.istack.internal.NotNull;
import project.products.InvalidTagException;
import project.parsing.tags.ParentTag;
import project.parsing.tags.TextTag;

/**
 * Класс с информацией о месте нахождения.
 * @see Person
 */
public class Location {
    private float x;
    private Integer y;
    private Long z;
    private String name;

    /**
     * Пустой конструктор. Используется в {@link project.products.ElementBuilder}.
     */
    public Location() { }

    /**
     * Получает значения характеристик локации из тега.
     * @param locationTag тег с вложенными тегами name, x, y, z.
     * @exception InvalidTagException если тег не содержит необходимых вложенных тегов или
     * если в попавшихся в нём тегах координат неверно записано число.
     */
    public Location(ParentTag locationTag) {
        setX(Float.valueOf(getCoordinateFromTag(locationTag, "x")));
        setY(Integer.valueOf(getCoordinateFromTag(locationTag, "y")));
        setZ(Long.valueOf(getCoordinateFromTag(locationTag, "z")));
        setName(getTextFromTag(locationTag, "name"));
    }
    
    private static String getTextFromTag(ParentTag tag, String name) {
        for (TextTag element: tag.getTextTags())
            if (element.getName().equals(name))
                return element.getContent();
        throw new InvalidTagException(Location.class, " Отсутствует тег для поля " + name + ".");
    }

    private static String getCoordinateFromTag(ParentTag tag, String name) {
        try {
             return getTextFromTag(tag, name);
        } catch (NumberFormatException e) {
            throw new InvalidTagException(Location.class, " В теге неверно указано значение координаты " + name + ".");
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
}