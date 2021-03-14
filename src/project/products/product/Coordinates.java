package project.products.product;

import com.sun.istack.internal.NotNull;
import project.products.InvalidTagException;
import project.parsing.tags.ParentTag;
import project.parsing.tags.TextTag;

/**
 * Класс с парой координат x и y.
 * @see Product
 */
public class Coordinates implements Comparable<Coordinates> {
    private Float x;
    private double y;

    /**
     * Пустой конструктор. Используется в {@link project.products.ElementBuilder}.
     */
    public Coordinates() { }

    /**
     * Получает значения координат x и y из соответствующего тега.
     * @param coordinatesTag тег с вложенными тегами x и y.
     * @exception InvalidTagException бросает, если тег не содержит необходимых вложенных тегов или
     * если в попавшихся в нём тегах x или y неверно записано вещественное число.
     */
    public Coordinates(ParentTag coordinatesTag) {
        setX(Float.valueOf(getTextFromTag(coordinatesTag, "x")));
        setY(Double.valueOf(getTextFromTag(coordinatesTag, "y")));
    }

    private String getTextFromTag(ParentTag tag, String name) {
        for (TextTag element: tag.getTextTags())
            if (element.getName().equals(name)) {
                try {
                    return element.getContent();
                } catch (NumberFormatException e) {
                    throw new InvalidTagException(this.getClass(), "В теге неверно указано значение координаты " + name + ".");
                } catch (IllegalArgumentException e) {
                    throw new InvalidTagException(this.getClass(), e.getMessage());
                }
            }
        throw new InvalidTagException(this.getClass(), "Тег не содержит координату " + name + ".");
    }

    /**
     * Метод для получения тега с координатами.
     * @return тег coordinates, содержащий теги x и y.
     */
    public ParentTag getTag() {
        ParentTag parentTag = new ParentTag("coordinates");
        parentTag.addTextTag(new TextTag("x", x + ""));
        parentTag.addTextTag(new TextTag("y", y + ""));
        return parentTag;
    }

    /**
     * Задаёт координату x.
     * @param x вещественная координата.
     * @exception IllegalArgumentException если получен null или число, не превосходящее -230.
     */
    public void setX(@NotNull Float x) throws IllegalArgumentException {
        if (x == null || x <= -230) throw new IllegalArgumentException("Абсцисса должна быть числом, превосходящим -230 !");
        this.x = x;
    }

    /**
     * Задаёт координату y.
     * @param y вещественная координата.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Возвращает координату x.
     * @return вещественная координата.
     */
    public Float getX() {
        return x;
    }

    /**
     * Возвращает координату y.
     * @return вещественная координата.
     */
    public double getY() {
        return y;
    }

    /**
     * Сравнивает с другим объектом класса координат. Возвращает разницу сумм коодинат.
     * @param coordinates другая пара координат.
     * @return округлённая разница сумм координат.
     */
    @Override
    public int compareTo(Coordinates coordinates) {
        return (int)(x + y - (coordinates.x + coordinates.y));
    }
}