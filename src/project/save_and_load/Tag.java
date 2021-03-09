package project.save_and_load;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tag {
    private String name;
    private HashMap<String, String> arguments;

    public Tag(TagMatcher matcher) {
        name = matcher.getName();
        Pattern argsPattern = Pattern.compile("\\s+(?<name>\\w+)\\s*=\\s*\"(?<value>[^\"]*)\"");
        Matcher argsMatcher = argsPattern.matcher(matcher.getArgs());
        arguments = new HashMap<>();
        while (argsMatcher.find())
            arguments.put(argsMatcher.group("name"), argsMatcher.group("value"));
    }
}