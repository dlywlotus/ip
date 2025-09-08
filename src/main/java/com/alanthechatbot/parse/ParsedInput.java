package com.alanthechatbot.parse;

public record ParsedInput(String actionType, String taskDesc,
                          String doneBy, String from, String to,
                          int index, String tagName) {

}
