package com.alanthechatbot.parse;

public class ParsedInput {
    private final String actionType;
    private final String taskDesc;
    private final String doneBy;
    private final String from;
    private final String to;
    private final int index;

    public ParsedInput(String actionType,
                       String taskDesc,
                       String doneBy,
                       String from,
                       String to,
                       int index) {
        this.actionType = actionType;
        this.taskDesc = taskDesc;
        this.doneBy = doneBy;
        this.from = from;
        this.to = to;
        this.index = index;
    }

    public String getActionType() {
        return actionType;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public String getDoneBy() {
        return doneBy;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getIndex() {
        return index;
    }

}
