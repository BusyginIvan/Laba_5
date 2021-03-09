package project.save_and_load;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagMatcher {
    public final static Pattern pattern = Pattern.compile(
            "<\\s*(?<name>\\w*)(?<args>(\\s+\\w+\\s*=\\s*\"[^\"]*\")*)\\s*>(?<content>[^<]*)<\\s*/\\s*\\k<name>\\s*>");
    private Matcher matcher;

    public TagMatcher(CharSequence text) {
        matcher = pattern.matcher(text);
    }

    public boolean findTag() {
        return matcher.find();
    }

    public String getName() {
        return matcher.group("name");
    }

    public String getArgs() {
        return matcher.group("args");
    }

    public String getContent() {
        return matcher.group("content");
    }

    public static boolean haveTag(CharSequence text) {
        return pattern.matcher(text).lookingAt();
    }

    public boolean haveTag() {
        return pattern.matcher(getContent()).lookingAt();
    }
}