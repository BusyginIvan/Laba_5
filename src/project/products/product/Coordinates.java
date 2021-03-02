package project.products.product;

import com.sun.istack.internal.NotNull;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Класс с парой координат x и y.
 * @see Product
 */
@XmlType( name = "coordinates")
public class Coordinates implements Comparable<Coordinates> {
    private Float x;
    private double y;

    /**
     * Задаёт координату x.
     * @param x вещественная координата.
     * @exception IllegalArgumentException бросает, если получен null или число, не превосходящее -230.
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
    @XmlAttribute
    public Float getX() {
        return x;
    }

    /**
     * Возвращает координату y.
     * @return вещественная координата.
     */
    @XmlAttribute
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