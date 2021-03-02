package project.products.product_collection;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

/**
 * Этот класс предоставляет для JAXB возможность конвертировать объект LocalDate в строковое представление и наоборот.
 * @see LocalDate
 * @see ProductCollection
 */
public class DateAdapter extends XmlAdapter<String, LocalDate> {
    /**
     * Преобразует строку в объект даты.
     * @param s строковое представление даты (например, 2021-02-21).
     * @return объект класса даты.
     */
    @Override
    public LocalDate unmarshal(String s) {
        return LocalDate.parse(s);
    }

    /**
     * Преобразует объект даты и времени в строку. Если в качестве объекта передан null, вернёт строку "null".
     * @param localDateTime конвертируемый объект класса даты.
     * @return строковое представление даты (например, 2021-02-21).
     */
    @Override
    public String marshal(LocalDate localDateTime) {
        if (localDateTime == null) return "null";
        return localDateTime.toString();
    }
}