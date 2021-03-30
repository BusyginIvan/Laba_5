package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Класс для построчного чтения из стандартного потока ввода.
 */
public class ConsoleReader {
    private static BufferedReader bufferedReader;
    private static Charset charset;

    /**
     * Открывает стандартный поток ввода, определяет кодировку консоли.
     */
    public static void init() {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        charset = Charset.forName(inputStreamReader.getEncoding());
        if (charset.equals(Charset.forName("cp1251")))
            charset = Charset.forName("cp866");
        bufferedReader = new BufferedReader(inputStreamReader);
    }

    /**
     * Закрывает поток ввода.
     */
    public static void close() {
        try {
            if (bufferedReader != null)
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
        String line = bufferedReader.readLine();
        if (line == null) {
            System.out.println("\n");
            Main.exit();
        }
        return new String(line.getBytes(), charset);
    }
}