package project.parsing.tags;

import java.util.HashSet;

/**
 * Класс, представляющий тег, содержащий другие теги. Помимо имени и списка аргументов хранит список текстовых и
 * список родительских тегов, в нём содержащихся.
 */
public class ParentTag extends Tag {
    private HashSet<ParentTag> parentTags;
    private HashSet<TextTag> textTags;

    /**
     * Создаёт тег по последней находке объекта TagMatcher.
     * @param matcher объект, предварительно нашедший тег в тексте методом {@link TagMatcher#findTag()}.
     * @exception IllegalStateException бросается, если тег не искался или не был найден.
     */
    public ParentTag(TagMatcher matcher) {
        super(matcher);
        parentTags = new HashSet<>();
        textTags = new HashSet<>();
        TagMatcher contentMatcher = new TagMatcher(matcher.getContent());
        while (contentMatcher.findTag()) {
            if (contentMatcher.haveTag())
                parentTags.add(new ParentTag(contentMatcher));
            else
                textTags.add(new TextTag(contentMatcher));
        }
    }

    /**
     * Создаёт тег без аргументов и тегов внутри с указанным именем.
     * @param name имя тега.
     */
    public ParentTag(String name) {
        super(name);
        parentTags = new HashSet<>();
        textTags = new HashSet<>();
    }

    /**
     * Возвращает текстовое представление этого тега.
     * @return текстовый тег.
     */
    public String toText() {
        String result = beginTag();
        for (ParentTag parentTag: parentTags)
            result += "\n\t" + parentTag.toText().replaceAll("\n", "\n\t");
        for (TextTag textTag: textTags)
            result += "\n\t" + textTag.toText().replaceAll("\n", "\n\t");
        return result + "\n" + endTag();
    }

    /**
     * Добавляет родительский тег, если такого ещё нет.
     * @param parentTag тег, содержащий теги.
     * @return true, если тега с таким именем ещё не было.
     */
    public boolean addParentTag(ParentTag parentTag) {
        return parentTags.add(parentTag);
    }

    /**
     * Добавляет тег с текстовым содержимым, если такого ещё нет.
     * @param textTag тег с текстом.
     * @return true, если тега с таким именем ещё не было.
     */
    public boolean addTextTag(TextTag textTag) {
        return textTags.add(textTag);
    }

    /**
     * Возвращает множество родительских тегов.
     * @return множество тегов.
     */
    public HashSet<ParentTag> getParentTags() {
        return parentTags;
    }

    /**
     * Возвращает множество тегов с текстом.
     * @return множество тегов.
     */
    public HashSet<TextTag> getTextTags() {
        return textTags;
    }
}