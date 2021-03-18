package project.products.product;

import com.sun.istack.internal.NotNull;
import project.parsing.tags.InvalidTagException;
import project.parsing.tags.ParentTag;
import project.parsing.tags.TextTag;

import static project.products.ElementBuilder.setField;

/**
 * Класс с парой координат x и y.
 * @see Product
 */
public class Coordinates implements Comparable<Coordinates> {
    private Float x;
    private double y;

    /**
     * Запрашивает значения координат у пользователя.
     */
    public Coordinates() {
        setField("Введите вещественную координату x", str -> setX(Float.parseFloat(str)));
        setField("Введите вещественную координату y", str -> setY(Double.parseDouble(str)));
    }

    /**
     * Получает значения координат x и y из соответствующего тега.
     * @param coordinatesTag тег с вложенными тегами x и y.
     * @exception InvalidTagException бросает, если тег не содержит необходимых вложенных тегов или
     * если в попавшихся в нём тегах x или y неверно записано вещественное число.
     */
    public Coordinates(ParentTag coordinatesTag) {
        String className = "Coordinates";
        String name = "x";
        try {
            setX(Float.parseFloat(coordinatesTag.getNestedTagContent(name)));
            setY(Double.parseDouble(coordinatesTag.getNestedTagContent((name = "y"))));
        } catch (NullPointerException e) {
            throw new InvalidTagException(className, " Отсутствует тег для поля " + name + ".");
        } catch (NumberFormatException e) {
            throw new InvalidTagException(className, "В теге неверно указано значение координаты " + name + ".");
        } catch (InvalidTagException | IllegalArgumentException e) {
            throw new InvalidTagException(className, e.getMessage());
        }
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