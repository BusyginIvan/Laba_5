package project.products;

import com.sun.istack.internal.NotNull;
import project.ConsoleReader;
import project.Main;
import project.products.product.*;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * Набор статических методов для обеспечения ввода значений пользователем.
 */
public class ElementBuilder {
    /**
     * Запрашивает ввод у пользователя и выполняет указанное действие над полученным значением.
     * @param invitationToEnter приглашение для ввода.
     * @param setter действие над полученной строкой.
     */
    public static void setField(String invitationToEnter, Consumer<String> setter) {
        System.out.print(invitationToEnter + ": ");
        while (true) {
            try {
                String line = ConsoleReader.readLine();
                if (line == null) Main.exit();
                else setter.accept(line);
                break;
            } catch (IOException e) {
                System.out.println("Что-то пошло не так...");
            } catch (NumberFormatException e) {
                System.out.println("Число записано некорректно.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            System.out.print(" Попробуйте ещё раз:");
        }
    }

    /**
     * Запрашивает ввод у пользователя и возвращает обработанное указанным способом значение.
     * @param invitationToEnter приглашение для ввода.
     * @param validator метод, преобразующий значение и требующий его корректности.
     * @return обработанное значение.
     */
    public static String getLine(String invitationToEnter, UnaryOperator<String> validator) {
        System.out.print(invitationToEnter + ": ");
        while (true) {
            try {
                String line = ConsoleReader.readLine();
                if (line == null) Main.exit();
                else return validator.apply(line);
            } catch (IOException e) {
                System.out.println("Что-то пошло не так.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            System.out.print(" Попробуйте ещё раз:");
        }
    }
}