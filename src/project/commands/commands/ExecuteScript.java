package project.commands.commands;

import project.commands.command_map.CommandMap;

import java.io.*;

/**
 * Команда "выполнить скрипт".
 * @see project.commands.command_map.CommandMap
 */
public class ExecuteScript implements ICommand {
    CommandMap commandMap;
    private int OpenedScriptsNumber;
    private final int MaxOpenedScripts = 100;

    /**
     * Команда будет выполнять команды из переданного в этот конструктор перечня.
     * @param commandMap набор выпоняемых команд.
     */
    public ExecuteScript(CommandMap commandMap) {
        OpenedScriptsNumber = 0;
        this.commandMap = commandMap;
    }

    /**
     * Выполняет скрипт из указанного файла.
     * @param arguments массив аргументов команд; arguments[0] - адрес файла со скриптом.
     * @exception CommandException если отсутствует аргумент адреса файла, файла не существует или достигнут лимит
     * на уровень вложенности скрипта (100).
     */
    @Override
    public void execute(String[] arguments) throws CommandException {
        if (arguments == null || arguments.length == 0)
            throw new CommandException("Ошибка! После этой команды должен следовать аргумент с адресом файла.");
        File file = new File(arguments[0]);
        if (!file.exists())
            throw new CommandException("Ошибка! Указанного файла не существует.");
        if (OpenedScriptsNumber >= MaxOpenedScripts)
            throw new CommandException("Пришлось пропустить вложенный вызов скрипта, так как был достигнут предельный уровень вложенности.");
        OpenedScriptsNumber++;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while (true) {
                try {
                    if ((line = bufferedReader.readLine()) == null) break;
                    commandMap.execute(line);
                } catch (IOException e) {
                    System.out.println("При чтении команды произошла ошибка...");
                } catch (CommandException e) {
                    System.out.println(e.getMessage());
                }
            }
            bufferedReader.close();
        } catch (SecurityException e) {
            throw new CommandException("Ошибка! Нет права на чтение указанного файла.");
        } catch (IOException e) {
            System.out.println("Чёт поток не закрылся...");
        }
        OpenedScriptsNumber--;
    }

    /**
     * Возвращает описание команды в виде массива строк: первые элементы - названия аргументов, последний - строка,
     * описывающая команду.
     * @return массив строк.
     */
    @Override
    public String[] description() {
        return new String[]{"адрес файла", "выполнить скрипт"};
    }
}