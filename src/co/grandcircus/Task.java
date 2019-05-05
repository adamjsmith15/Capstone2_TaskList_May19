package co.grandcircus;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.Scanner;

public class Task {

	private String memberName;
	private String taskDescript;
	private boolean taskComplete;
	private LocalDate dueDate;
	private String formattedDate;

	public Task(String memberName, String taskDescription, LocalDate dueDate) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		this.memberName = memberName;
		this.taskDescript = taskDescription;
		this.dueDate = dueDate;
		this.taskComplete = false;
		this.formattedDate = dateFormat.format(dueDate);
	}

	public Task() {

	}

	public void addTask(LinkedList<Task> taskList, Scanner sc) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		System.out.println("ADD TASK");
		System.out.println("");
		System.out.print("Team Member Name: ");
		String name = sc.nextLine();
		System.out.println("");
		System.out.print("Task Description: ");
		String task = sc.nextLine();
		System.out.println("");
		LocalDate date = null;
		while (date == null) {
			System.out.print("Due Date (MM/dd/yyyy): ");
			String tempDate = sc.nextLine();
			try {
				date = LocalDate.parse(tempDate, dateFormat);
				} catch (DateTimeParseException e) {
				System.out.println("Sorry, that's not valid. Please try again.");
				}
		}
		taskList.add(new Task(name, task, date));
		setFormattedDate(date);
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("Task entered!");
		System.out.println("");

	}

	public void printIntro() {
		System.out.println("Welcome to the Task Manager!");
		System.out.println("");
		System.out.println("");
	}

	public void listTasks(LinkedList<Task> taskList, boolean choice) {
		String format = "%-15s %-15s %-15s %-15s \n";
		if (!taskList.isEmpty()) {
			if (choice == true) {
				System.out.printf(format, "Done?", "Due date", "Team Member", "Description");
				System.out.println("");
				for (int i = 0; i < taskList.size(); i++) {
					System.out.print(taskList.get(i));
					System.out.println("");
				}
			} else {
				String format2 = "%-10s %-15s %-15s %-15s %-15s \n";
				System.out.printf(format2, "Task #", "Done?", "Due date", "Team Member", "Description");
				System.out.println("");
				for (int i = 0; i < taskList.size(); i++) {
					System.out.printf("%-11d", i + 1);
					System.out.print(taskList.get(i));
					System.out.println("");
				}
			}
		} else {
			System.out.println("There are no tasks at this time.");
			System.out.println("");
		}
	}

	public void displayMenu() {

		String format = "%5s %s \n";
		System.out.printf(format, "1.", "  List tasks");
		System.out.printf(format, "2.", "  Add task");
		System.out.printf(format, "3.", "  Delete task");
		System.out.printf(format, "4.", "  Mark task complete");
		System.out.printf(format, "5.", "  Search task list");
		System.out.printf(format, "6.", "  Edit a task");
		System.out.printf(format, "7.", "  Quit");
		System.out.println("");
		System.out.println("");

	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getTaskDescript() {
		return taskDescript;
	}

	public void setTaskDescript(String taskDescript) {
		this.taskDescript = taskDescript;
	}

	public boolean isTaskComplete() {
		return taskComplete;
	}

	public void selectTask(LinkedList<Task> list, Scanner sc, String prompt, boolean choice) {
		if (list.isEmpty()) {
			System.out.println("List is empty");
		} else {
			listTasks(list, false);
			int userEntry = Validator.getInt(sc, prompt);
			while (userEntry > list.size() || userEntry < 1) {
				System.out.println("Not a valid entry, please select a task from the list.");
				System.out.println(" ");
				listTasks(list, false);
				userEntry = sc.nextInt();
			}
			if (choice == true) {
				if (list.get(userEntry - 1).isTaskComplete() == true) {
					System.out.println("Entry is already completed!");
					System.out.println("");
				}else {
					System.out.println(list.get(userEntry - 1));
					System.out.print("Are you sure you want to mark this task complete? (y/n): ");
					String userConfirm = sc.next();
					if (userConfirm.equals("y")) {
						list.get(userEntry - 1).setTaskComplete();
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("Task set to complete!");
						System.out.println("");
					} else {

					}
				}
			} else {
				System.out.println(list.get(userEntry - 1));
				System.out.print("Are you sure you want to delete this task? (y/n): ");
				String userConfirm = sc.next();
				if (userConfirm.equals("y")) {
					list.remove(userEntry - 1);
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("Task removed!");
					System.out.println("");
				} else {

				}
				// garbage line
				sc.nextLine();
			}

		}

	}

	public String searchList(LinkedList<Task> list, Scanner sc, String prompt) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		if (list.isEmpty()) {
			return "List is empty, cannot search";
		} else {
			System.out.println(prompt);
			String userSearch = sc.next();
			String searchResults = "";
			String name;
			while (!(userSearch.equalsIgnoreCase("name") || userSearch.equalsIgnoreCase("date")
					|| userSearch.equalsIgnoreCase("quit"))) {
				System.out.println("Please select a search parameter, name or date (quit to return to menu): ");
				userSearch = sc.next();
			}
			if (userSearch.equalsIgnoreCase("name")) {
				System.out.print("What name would you like to search for? ");
				name = sc.next();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getMemberName().contains(name)) {
						searchResults += list.get(i) + "\n";
					} else {
						continue;
					}
				}
				if (searchResults.isEmpty()) {
					return "No search results return for " + name;
				} else {
					String formatStr = "%-15s %-15s %-15s %-15s \n";
					System.out.println("Here are the search results for " + name + ":");
					System.out.printf(formatStr, "Done?", "Due date", "Team Member", "Description");
					
					return  searchResults;
				}
			} else if (userSearch.equalsIgnoreCase("date")) {
				System.out.print("What date would you like to see the prior tasks for? ");
				LocalDate date = null;
				while (date == null) {
					System.out.print("(MM/dd/yyyy): ");
					String tempDate = sc.next();
					try {
						date = LocalDate.parse(tempDate, dateFormat);
					} catch (DateTimeParseException e) {
					System.out.println("Sorry, that's not valid. Please try again.");
					}
					for(int i = 0; i < list.size(); i++) {
						if(list.get(i).getDueDate().isBefore(date)) {
							searchResults += list.get(i) + "\n";
						}else {
							continue;
						}
					}
				}
				return searchResults;
			} else {
				return "Quitting to menu... \n \n";
			}
		}
	}
	
	public void editTask(LinkedList<Task> list, Scanner sc, String prompt) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		if(list.isEmpty()) {
			System.out.println("There are no tasks to edit. ");
		}else {
			listTasks(list, false);
			int userEntry = Validator.getInt(sc, "Select the task number of the task you would like to edit. ");
			while (userEntry > list.size() || userEntry < 1) {
				System.out.println("Not a valid entry, please select a task number from the list.");
				System.out.println(" ");
				listTasks(list, false);
				userEntry = sc.nextInt();
			}
			System.out.println(prompt);
			String choice = sc.next();
			if(choice.equalsIgnoreCase("name")) {
				System.out.println(list.get(userEntry - 1));
				System.out.println("What you would like to change the name to?");
				sc.nextLine();
				String newName = sc.nextLine();
				list.get(userEntry-1).setMemberName(newName);
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("Name changed.");
				System.out.println("");
				System.out.println(list.get(userEntry - 1));
				
			}else if(choice.equalsIgnoreCase("date")) {
				System.out.println(list.get(userEntry - 1));
				System.out.println("What you would like to change the due date to?");
				LocalDate date = null;
				while (date == null) {
					//System.out.print("Due Date (MM/dd/yyyy): ");
					String tempDate = sc.next();
					try {
						date = LocalDate.parse(tempDate, dateFormat);
						} catch (DateTimeParseException e) {
						System.out.println("Sorry, that's not valid. Please try again.");
						}
				}
				list.get(userEntry-1).setFormattedDate(date);
				System.out.println("");
				System.out.println("");
				System.out.println("Date changed!");
				System.out.println("");
			}else if(choice.equalsIgnoreCase("description")) {
				System.out.println(list.get(userEntry - 1));
				System.out.println("What you would like to change the description to?");
				// garbage line
				sc.nextLine();
				String newDesc = sc.nextLine();
				list.get(userEntry - 1).setTaskDescript(newDesc);
				System.out.println("");
				System.out.println("");
				System.out.println("Description changed!");
				System.out.println("");
			}else {
				System.out.println("Quitting back to menu...");
			}
			
		}

		
	}

	public void setTaskComplete() {
		this.taskComplete = true;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public void setFormattedDate(LocalDate dueDate) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		this.formattedDate = dateFormat.format(dueDate);
	}
	public String getFormattedDate() {
		return formattedDate;
	}

	// @override
	public String toString() {
		String format = "%-15s %-15s %-15s %-15s \n";
		String list = "";
		String done = "";
		if(isTaskComplete()) {
			done = "Complete";
		}else {
			done = "Not Complete";
		}
		list = String.format(format, done, getFormattedDate(), getMemberName(), getTaskDescript());
		return list;
	}

}

