package com.alanthechatbot.task;

import java.util.ArrayList;

/**
 * A list of the current tasks added by the user.
 */
public class TaskList {
    private final ArrayList<Task> taskList = new ArrayList<>();

    /**
     * Prints the size of the list.
     */
    private String getListSizeString() {
        return "Now you have " + taskList.size() + " tasks in the list.";
    }

    /**
     * Gets the size of the list.
     *
     * @return the size of the list
     */

    public int size() {
        return taskList.size();
    }

    /**
     * Adds the given task to the task list.
     *
     * @param task the task to be added
     */
    public String addTask(Task task) {
        taskList.add(task);
        return "Got it. I've added this task:\n"
                + "  " + task.toString() + "\n" +
                getListSizeString();
    }

    /**
     * Deletes the task specified by the taskId.
     *
     * @param taskId the id of the task to delete
     * @throws IllegalArgumentException if the task id is out of bounds
     */
    public String deleteTaskWithId(int taskId) {
        if (taskId > taskList.size() || taskId < 1) {
            return "The task id is invalid!";
        }
        Task task = taskList.remove(taskId - 1);
        return "Noted. I've removed this task:\n" +
                "  " + task.toString() + "\n" +
                getListSizeString();
    }

    /**
     * Marks the task specified by the taskId as completed.
     *
     * @param taskId the id of the task to mark completed
     * @throws IllegalArgumentException if the task id is out of bounds
     */
    public String markTaskWithId(int taskId) {
        if (taskId > taskList.size() || taskId < 1) {
            return "The task id is invalid!";
        }
        Task task = taskList.get(taskId - 1);
        task.isDone = true;
        return "Nice! I've marked this task as done:\n" +
                "  " + task;
    }

    public String tagTaskWithId(int taskId, String tagName) {
        if (taskId > taskList.size() || taskId < 1) {
            return "The task id is invalid!";
        }
        Task task = taskList.get(taskId - 1);
        task.tag = tagName;
        return "Nice! I've tagged this task:\n" +
                "  " + task;
    }

    /**
     * Prints the tasks in the task list.
     */
    public String printTasks() {
        if (taskList.isEmpty()) {
            return "You have completed all your tasks! :)";
        }
        StringBuilder res = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            res.append("  ").append(i + 1).append(". ").append(task.toString()).append("\n");
        }

        return res.toString();
    }

    /**
     * Searches the task list for tasks whose description contains the given string and
     * prints all occurrences of such tasks.
     *
     * @param keyword the string to match
     */
    public String findTaskWithKeyword(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        for (Task task : taskList) {
            if (task.descriptionContains(keyword)) {
                matches.add(task);
            }
        }

        if (matches.isEmpty()) {
            return "There are no matches in your list.";
        } else {
            StringBuilder res = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matches.size(); i++) {
                Task match = matches.get(i);
                res.append("  ").append(i + 1).append(". ").append(match.toString()).append('\n');
            }
            return res.toString();
        }
    }
}
