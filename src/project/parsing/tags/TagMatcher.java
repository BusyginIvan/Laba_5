package project.parsing.tags;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Этот класс позволяет анализировать текст на наличие тегов, а также выделять области имени, аргументов и содержимого в
 * найденных тегах.
 */
public class TagMatcher {
    public final static Pattern pattern = Pattern.compile(
            "(?s)<\\s*(?<name>\\w*)(?<args>(\\s+\\w+\\s*=\\s*\"[^\"]*\")*)\\s*>(?<content>.*?)<\\s*/\\s*\\k<name>\\s*>");
    private Matcher matcher;

    /**
     * Создаёт объект для работы с указанным текстом.
     * @param text обрабатываемый текст.
     */
    public TagMatcher(CharSequence text) {
        matcher = pattern.matcher(text);
    }

    /**
     * Ищет следующий тег в тексте этого объекта.
     * @return true, если тег был найден.
     */
    public boolean findTag() {
        return matcher.find();
    }

    /**
     * Имя последнего найденного тега.
     * @return имя.
     * @exception IllegalStateException если тег не искался или не был найден.
     */
    public String getName() {
        return matcher.group("name");
    }

    /**
     * Область текста с аргументами последнего найденного тега.
     * @return строка с последовательностью аргументов.
     * @exception IllegalStateException если тег не искался или не был найден.
     */
    public String getArgs() {
        return matcher.group("args");
    }

    /**
     * Содержимое последнего найденного тега в виде текста.
     * @return текст внутри тега.
     * @exception IllegalStateException если тег не искался или не был найден.
     */
    public String getContent() {
        return matcher.group("content");
    }

    /**
     * Проверка наличия тега в указанном тексте.
     * @param text текст, в котором ищется тег.
     * @return true, если в тексте есть тег.
     */
    public static boolean haveTag(CharSequence text) {
        return pattern.matcher(text).find();
    }

    /**
     * Проверка наличия тега внутри последнего найденного.
     * @return true, если тег имеется.
     */
    public boolean haveTag() {
        return haveTag(getContent());
    }
}