package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс для построчного чтения из стандартного потока ввода.
 */
public class MyReader {
    private static BufferedReader bufferedReader;

    /**
     * Открывает стандартный поток ввода и передаёт его в bufferedReader.
     */
    public static void init() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Закрывает поток ввода.
     */
    public static void close() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("\nЧёт поток не закрылся...");
        }
    }

    /**
     * Читает строку из стандартного потока ввода.
     * @return прочитанная строка.
     * @throws IOException ошибка при попытке получения строки из стандартного потока ввода.
     */
    public static String readLine() throws IOException {
        return bufferedReader.readLine();
    }
}