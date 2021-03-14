package project.parsing.tags;

/**
 * Класс, представляющий тег с текстовым содержимым. Помимо имени и списка аргументов хранит содержащийся в нём текст.
 */
public class TextTag extends Tag {
    private String content;

    /**
     * Создаёт тег по последней находке объекта TagMatcher.
     * @param matcher объект, предварительно нашедший тег в тексте методом {@link TagMatcher#findTag()}.
     * @exception IllegalStateException бросается, если тег не искался или не был найден.
     */
    public TextTag(TagMatcher matcher) {
        super(matcher);
        content = matcher.getContent();
    }

    /**
     * Создаёт тег без аргументов с указанным именем и текстовым содержанием.
     * @param name имя тега.
     * @param content содержимое.
     */
    public TextTag(String name, String content) {
        super(name);
        this.content = content;
    }

    /**
     * Возвращает текстовое представление этого тега.
     * @return текстовый тег.
     */
    public String toText() {
        return beginTag() + content + endTag();
    }

    /**
     * Возвращает текстовое содержимое этого тега.
     * @return содержимое тега.
     */
    public String getContent() {
        return content;
    }
}