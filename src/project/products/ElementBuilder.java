package project.products;

import com.sun.istack.internal.NotNull;
import project.ConsoleReader;
import project.products.product.*;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Единственной задачей этого класса является осуществление ввода всех полей объекта класса {@link Product} из стандартного потока.
 */
public class ElementBuilder {
    /**
     * Осуществляет взаимодействие с пользователем, при котором заполняются все поля переданного товара.
     * @param product изменяемый объект.
     */
    public static void changeProduct(@NotNull Product product) {
        setField("Введите наименование товара", product::setName);
        Coordinates coordinates = new Coordinates();
        setField("Введите вещественную координату x", str -> coordinates.setX(Float.valueOf(str)));
        setField("Введите вещественную координату y", str -> coordinates.setY(Double.valueOf(str)));
        product.setCoordinates(coordinates);
        setField("Введите цену на товар", str -> product.setPrice(Double.valueOf(str)));
        setField("Введите единицу измерения (KILOGRAMS / SQUARE_METERS / LITERS / GRAMS) или оставьте строку пустой",
                str -> product.setUnitOfMeasure(str.equals("") ? null : UnitOfMeasure.valueOf(str)));
        setField("Введите имя владельца или оставьте строку пустой", str -> {
            if (str.equals(""))
                product.setOwner(null);
            else {
                Person person = new Person();
                person.setName(str);
                setField("Введите рост владельца или оставьте строку пустой",
                        str1 -> person.setHeight(str1.equals("") ? null : Float.valueOf(str1)));
                setField("Введите обхват талии владельца",
                        str1 -> person.setWeight(Float.valueOf(str1)));
                setField("Введите номер паспорта", person::setPassportID);
                setField("Введите название локации или оставьте строку пустой", str1 -> {
                    if (str1.equals(""))
                        person.setLocation(null);
                    else {
                        Location location = new Location();
                        location.setName(str1);
                        setField("Введите вещественную координату x", str2 -> location.setX(Float.valueOf(str2)));
                        setField("Введите целую координату y", str2 -> location.setY(Integer.valueOf(str2)));
                        setField("Введите целую координату z", str2 -> location.setZ(Long.valueOf(str2)));
                        person.setLocation(location);
                    }
                });
                product.setOwner(person);
            }
        });
    }

    private static void setField(String invitationToEnter, Consumer<String> setter) {
        while (true) {
            System.out.print(invitationToEnter + ": ");
            try {
                setter.accept(ConsoleReader.readLine());
                break;
            } catch (IOException e) {
                System.out.print("Что-то пошло не так.");
            } catch (NumberFormatException e) {
                System.out.print("Некорректный ввод.");
            } catch (IllegalArgumentException e) {
                System.out.print(e.getMessage());
            }
            System.out.println(" Попробуйте ещё раз.");
        }
    }
}