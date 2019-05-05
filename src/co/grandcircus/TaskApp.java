package co.grandcircus;

import java.util.LinkedList;
import java.util.Scanner;

public class TaskApp {

	public static void main(String[] args) {
		Task task = new Task();
		LinkedList<Task> taskList = new LinkedList<>();
		Scanner scan = new Scanner(System.in);
		int quit = 0;
		task.printIntro();

		while (quit != 7) {
			task.displayMenu();
			quit = Validator.getInt(scan, "What would you like to do? ", 1, 7);
			System.out.println("");
			System.out.println("");
			System.out.println("");
			if (quit == 1) {
				task.listTasks(taskList, true);
			} else if (quit == 2) {
				task.addTask(taskList, scan);
			} else if (quit == 3) {
				task.selectTask(taskList, scan, "Select the task number of the task you would like to delete ", false);
			} else if (quit == 4) {
				task.selectTask(taskList, scan, "Select the task number of the task you would like to mark complete ", true);
			} else if (quit == 5){
				System.out.println(task.searchList(taskList, scan, "Would you like to search by name or date? "));
			}else if (quit == 6){
				task.editTask(taskList, scan, "What would you like to edit? (name, date, description) ");
			}else {
				System.out.println("Have a great day!");
			}
		}
		scan.close();
	}

}
