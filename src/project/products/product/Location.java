package project.products.product;

import com.sun.istack.internal.NotNull;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Класс с информацией о месте нахождения.
 * @see Person
 */
@XmlType(propOrder = { "x", "y", "z", "name" }, name = "location")
public class Location {
    private float x;
    private Integer y;
    private Long z;
    private String name;

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
    @XmlAttribute
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
