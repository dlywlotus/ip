package com.alanthechatbot.task;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> taskList = new ArrayList<>();

    private void printListSizeString() {
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
    }

    public int size() {
        return taskList.size();
    }

    public void addTask(Task task, boolean canPrint) {
        taskList.add(task);
        if (canPrint) {
            System.out.println("Got it. I've added this task:");
            System.out.println("  " + task.toString());
            printListSizeString();
        }
    }

    public void deleteTask(int taskId, boolean canPrint) {
        if (taskId > taskList.size() || taskId < 1) {
            throw new IllegalArgumentException("The task id is invalid!");
        }
        Task task = taskList.remove(taskId - 1);
        if (canPrint) {
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + task.toString());
            printListSizeString();
        }
    }

    public void markTask(int taskId, boolean canPrint) {
        if (taskId > taskList.size() || taskId < 1) {
            throw new IllegalArgumentException("The task id is invalid!");
        }
        Task task = taskList.get(taskId - 1);
        task.isDone = true;
        if (canPrint) {
            System.out.println("Nice! I've marked this task as done:");
            System.out.printf("  %s\n", task);
        }
    }

    public void printTasks() {
        if (taskList.isEmpty()) {
            System.out.println("You have completed all your tasks! :)");
            return;
        }

        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            System.out.printf("  %d.%s\n", i + 1, task);
        }
    }

    /**
     * Searches the task list for tasks whose description contains the given string and
     * prints all occurrences of such tasks.
     *
     * @param keyword the string to match
     */
    public void findAll(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        for (Task task : taskList) {
            if (task.descriptionContains(keyword)) {
                matches.add(task);
            }
        }

        if (matches.isEmpty()) {
            System.out.println("There are no matches in your list.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matches.size(); i++) {
                Task match = matches.get(i);
                System.out.printf("  %d.%s\n", i + 1, match);
            }
        }
    }

}
