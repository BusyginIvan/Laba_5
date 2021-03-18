package project.parsing.tags;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Этот класс позволяет анализировать текст на наличие тегов, а также выделять области имени, аргументов и содержимого в
 * найденных тегах.
 */
public class TagMatcher {
    public final static Pattern beginTagPattern = Pattern.compile("<\\s*(?<name>\\w*)(?<args>(\\s+\\w+\\s*=\\s*\"[^\"]*\")*)\\s*>");
    private String text;
    private int i;
    private String name;
    private String args;
    private String content;

    /**
     * Создаёт объект для работы с указанным текстом.
     * @param text обрабатываемый текст.
     */
    public TagMatcher(String text) {
        this.text = text;
        i = 0;
    }

    /**
     * Ищет следующий тег в тексте этого объекта.
     * @return true, если тег был найден.
     */
    public boolean findTag() {
        Matcher matcher = beginTagPattern.matcher(text.substring(i));
        if (!matcher.find())
            return false;
        i += matcher.end();
        name = matcher.group("name");
        args = matcher.group("args");
        String beginTagRegex = "<\\s*" + name + "(\\s+\\w+\\s*=\\s*\"[^\"]*\")*\\s*>";
        matcher = Pattern.compile("(" + beginTagRegex + ")|(<\\s*/\\s*" + name + "\\s*>)").matcher(text.substring(i));
        int n = 1;
        while (n > 0) {
            if (!matcher.find())
                return false;
            if (matcher.group().matches(beginTagRegex)) n++;
            else n--;
        }
        content = text.substring(i, i + matcher.start());
        i += matcher.end();
        return true;
    }

    /**
     * Имя последнего найденного тега.
     * @return имя.
     * @exception IllegalStateException если тег не искался или не был найден.
     */
    public String getName() {
        return name;
    }

    /**
     * Область текста с аргументами последнего найденного тега.
     * @return строка с последовательностью аргументов.
     * @exception IllegalStateException если тег не искался или не был найден.
     */
    public String getArgs() {
        return args;
    }

    /**
     * Содержимое последнего найденного тега в виде текста.
     * @return текст внутри тега.
     * @exception IllegalStateException если тег не искался или не был найден.
     */
    public String getContent() {
        return content;
    }

    /**
     * Проверка наличия тега в указанном тексте.
     * @param text текст, в котором ищется тег.
     * @return true, если в тексте есть тег.
     */
    public static boolean haveTag(String text) {
        return new TagMatcher(text).findTag();
    }

    /**
     * Проверка наличия тега внутри последнего найденного.
     * @return true, если тег имеется.
     */
    public boolean haveTag() {
        return haveTag(content);
    }
}