package com.alanthechatbot.parse;

import com.alanthechatbot.exceptions.InputParsingException;

import java.util.ArrayList;
import java.util.Arrays;

import com.alanthechatbot.exceptions.InputParsingException;

/**
 * A class to translate the user's inputs into actions to be carried out
 */
public class Parser {
    private final String inputLine;

    public Parser(String inputLine) {
        assert inputLine.isEmpty() : "The input passed to parser should not be empty!";
        this.inputLine = inputLine;

    }

    /**
     * Parses the user's input into a ParsedInput.
     *
     * @return the parsed input
     * @throws InputParsingException if the task id is missing or is not an integer
     * @see com.alanthechatbot.parse.ParsedInput
     */
    public ParsedInput parse() throws InputParsingException {
        int i;

        ArrayList<String> inputWords = new ArrayList<>(Arrays.asList(inputLine.split(" ")));
        String firstWord = inputWords.get(0);

        ArrayList<String> taskDesc = new ArrayList<>();
        ArrayList<String> doneBy = new ArrayList<>();
        ArrayList<String> from = new ArrayList<>();
        ArrayList<String> to = new ArrayList<>();
        String tagName = "";

        int index = 0;

        switch (firstWord) {
        case "todo":
            i = 1;
            while (i < inputWords.size()) {
                taskDesc.add(inputWords.get(i));
                i += 1;
            }
            break;
        case "deadline":
            i = 1;
            while (i < inputWords.size() && !inputWords.get(i).equals("/by")) {
                taskDesc.add(inputWords.get(i));
                i += 1;
            }
            for (int j = i + 1; j < inputWords.size(); j++) {
                doneBy.add(inputWords.get(j));
            }
            break;
        case "event":
            taskDesc = new ArrayList<>();
            i = 1;
            while (i < inputWords.size() && !inputWords.get(i).equals("/from")) {
                taskDesc.add(inputWords.get(i));
                i += 1;
            }
            i += 1;
            while (i < inputWords.size() && !inputWords.get(i).equals("/to")) {
                from.add(inputWords.get(i));
                i += 1;
            }
            for (int j = i + 1; j < inputWords.size(); j++) {
                to.add(inputWords.get(j));
            }
            break;
        case "mark":
        case "delete":
        case "tag":
            if (inputWords.size() < 2) {
                throw new InputParsingException("Please provide the task id!");
            }
            try {
                index = Integer.parseInt(inputWords.get(1));
            } catch (NumberFormatException e) {
                throw new InputParsingException("The task id should be an integer!");
            }
            if (firstWord.equals("tag")) {
                if (inputWords.size() < 3) {
                    throw new InputParsingException("Please provide the tag name!");
                }
                tagName = inputWords.get(2);
            }
            break;
        case "find":
            if (inputWords.size() < 2) {
                throw new InputParsingException("Please provide the keyword!");
            }
            taskDesc.add(inputWords.get(1));
            break;
        case "list":
            if (inputWords.size() == 2) {
                String tag = inputWords.get(1).substring(1);
                if (tag.isEmpty()) {
                    throw new InputParsingException("Tag name cannot be empty!");
                }
                tagName = tag;
            }
            break;
        case "bye":
            break;
        default:
            firstWord = "invalid input";
        }
        return new ParsedInput(firstWord,
                String.join(" ", taskDesc),
                String.join(" ", doneBy),
                String.join(" ", from),
                String.join(" ", to),
                index, tagName);
    }
}
