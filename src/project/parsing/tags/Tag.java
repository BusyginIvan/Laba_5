package project.parsing.tags;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, представляющий некоторый тег. Хранит его имя и список аргументов.
 */
public class Tag {
    private String name;
    private HashMap<String, String> arguments;

    /**
     * Создаёт тег по последней находке объекта TagMatcher.
     * @param matcher объект, предварительно нашедший тег в тексте методом {@link TagMatcher#findTag()}.
     * @exception IllegalStateException бросается, если тег не искался или не был найден.
     */
    public Tag(TagMatcher matcher) {
        name = matcher.getName();
        Pattern argsPattern = Pattern.compile("\\s+(?<name>\\w+)\\s*=\\s*\"(?<value>[^\"]*)\"");
        Matcher argsMatcher = argsPattern.matcher(matcher.getArgs());
        arguments = new HashMap<>();
        while (argsMatcher.find())
            arguments.put(argsMatcher.group("name"), argsMatcher.group("value"));
    }

    /**
     * Создаёт тег без аргументов с указанным именем.
     * @param name имя тега.
     */
    public Tag(String name) {
        arguments = new HashMap<>();
        this.name = name;
    }

    /**
     * Возвращает строковое представление открывающего тега.
     * @return открывающий тег.
     */
    public String beginTag() {
        String result = "<" + name;
        for (String key: arguments.keySet())
            result += " " + key + "=\"" + arguments.get(key) + "\"";
        return result + ">";
    }

    /**
     * Возвращает строковое представление закрывающего тега.
     * @return закрывающий тег.
     */
    public String endTag() {
        return "</" + name + ">";
    }

    /**
     * Добавляет аргумент с указанными именем и значением.
     * @param key имя аргумента.
     * @param value значение.
     */
    public void addArgument(String key, String value) {
        arguments.put(key, value);
    }

    /**
     * Возвращает имя тега.
     * @return имя.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает ссылку на список аргументов.
     * @return карта аргументов.
     */
    public HashMap<String, String> getArguments() {
        return arguments;
    }
}