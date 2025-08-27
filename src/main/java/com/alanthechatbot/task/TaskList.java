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
    private void printListSizeString() {
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
    }

    /**
     * Gets the size of the list.
     * @return the size of the list
     */

    public int size() {
        return taskList.size();
    }

    /**
     * Adds the given task to the task list.
     * @param task the task to be added
     * @param canPrint whether to print a log of the task addition event
     */
    public void addTask(Task task, boolean canPrint) {
        taskList.add(task);
        if (canPrint) {
            System.out.println("Got it. I've added this task:");
            System.out.println("  " + task.toString());
            printListSizeString();
        }
    }

    /**
     * Deletes the task specified by the taskId.
     * @param taskId the id of the task to delete
     * @param canPrint whether to print a log of the task deletion event
     * @throws IllegalArgumentException if the task id is out of bounds
     */
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

    /**
     * Marks the task specified by the taskId as completed.
     * @param taskId the id of the task to mark completed
     * @param canPrint whether to print a log of the task marking event
     * @throws IllegalArgumentException if the task id is out of bounds
     */
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


    /**
     * Prints the tasks in the task list.
     */
    public void printTasks() {
        if (taskList.isEmpty()) {
            System.out.println("You have completed all your tasks! :)");
            return;
        }

        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            System.out.printf("  %d.%s\n", i + 1, task.toString());
        }
    }
}
