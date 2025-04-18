# TodoListApplication

A simple and efficient Todo List application built entirely in Kotlin. This application allows users to manage their daily tasks efficiently by providing features like adding, editing, deleting, and marking tasks as completed.

---

## Features

- **Add Tasks**: Easily add tasks to your to-do list.
- **Edit Tasks**: Modify task details if needed.
- **Delete Tasks**: Remove tasks that are no longer relevant.
- **Mark as Completed**: Keep track of completed tasks.
- **User-Friendly Interface**: A clean and intuitive UI for a seamless user experience.

---

### Usage
1. Launch the app.
2. Add a new task by clicking the Add Task button.
3. Edit or delete tasks by selecting the desired task.
4. Mark tasks as completed by checking the task checkbox.

---

### Code Structure
- **Model**: Represents the data layer, including database and API interactions.
- **View**: Handles the UI and user interactions.
- **ViewModel**: Acts as a bridge between the View and Model, containing business logic.

---

### Technologies Used
- **Kotlin**: 100% Kotlin-based application.
- **Room Database**: For offline storage of tasks.
- **LiveData**: For observing data changes.
- **ViewModel**: For managing UI-related data.
- **Lazy Column**: For displaying the list of tasks.

---


## Getting Started

### Prerequisites

- Android Studio installed
- JDK 8 or higher

### Installation

1. Clone the repository:

    ```sh
    git clone https://github.com/Dinakaran-k/TodoListApp.git
    ```

2. Open the project in Android Studio:

    - Open Android Studio
    - Click on `File` -> `Open...`
    - Navigate to the cloned repository and open it

3. Build the project:

    - Click on `Build` -> `Make Project` or use the `Ctrl+F9` shortcut

4. Run the project:

    - Click on `Run` -> `Run 'app'` or use the `Shift+F10` shortcut



## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Thanks to the developers and contributors of the libraries and tools used in this project.
