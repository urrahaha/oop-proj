package login;

import forum.Forum;
import forum.ForumView;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LoginRegister {
    private static final User ADMIN = User.createUser("adminuser", "adminpass", "admincontact");
    public static final Scanner scanner = new Scanner(System.in);
    public static User currentUser;

    // Starting point of the login process
    // Each branching point in the process will be separated into methods
    public static void start() {
        System.out.print("\n");
        System.out.println("Enter 1 to login");
        System.out.println("Enter 2 to register");
        System.out.println("Enter 3 to browse the forum");
        System.out.println("Enter 4 to exit");
        int input = 0;
        boolean success = false;

        // Exception handling to prevent disallowed inputs from crashing the program
        while (!success) {
            try {
                input = scanner.nextInt();
                scanner.nextLine();
                if (input == 1 || input == 2 || input == 3) {
                    success = true;
                }
                else if (input == 4) {
                    System.exit(0);
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Only 1, 2, 3, and 4 are allowed, please try again");
                scanner.next();
            }
        }

        if (input == 1) {
            login();
        }
        else if (input == 2) {
            addUser();
            start();
        }
        else if (input == 3) {
            if (currentUser != null) {
                ForumView.start();
            }
            else {
                System.out.println("You need to be logged in before using the forums");
                System.out.println();
                start();
            }
        }
    }

    // Login as normal user or as administrator
    // If password is admin password, branch into admin terminal
    // If password matches existing user password, branch into user terminal
    public static void login() {
        System.out.flush();
        System.out.println("Enter username to login");
        String username = scanner.nextLine().strip();

        System.out.println("Enter password to login");
        String pass = scanner.nextLine().strip();

        if (username.equals(ADMIN.getUsername()) && pass.equals(ADMIN.getPassword())) {
            currentUser = ADMIN;
            System.out.println("Logged in as admin");
            loginAdmin();
        }
        else {
            User locatedUser = null;
            while (locatedUser == null) {
                for (User user : Forum.getUsers()) {
                    if (username.equals(user.getUsername()) && pass.equals(user.getPassword())) {
                        locatedUser = user;
                        break;
                    }
                }

                if (locatedUser != null) {
                    currentUser = locatedUser;
                    loginUser(locatedUser);
                }
                else {
                    System.out.println("Invalid username or password");
                    System.out.println("Enter 1 to try again, anything else to go back");
                    int input = 0;

                    // Exception handling
                    try {
                        input = scanner.nextInt();
                        scanner.nextLine();
                        if (input != 1) {
                            throw new InputMismatchException();
                        }
                    }
                    catch (InputMismatchException ignored) {
                        start();
                    }

                    if (input == 1) {
                        login();
                    }
                }
            }
        }
    }

    public static void loginUser(User user) {
        System.out.println("Enter 1 to view your user account details");
        System.out.println("Enter 2 to edit your user account details");
        System.out.println("Enter 3 to go back");
        int input = 0;
        boolean success = false;

        while (!success) {
            try {
                input = scanner.nextInt();
                scanner.nextLine();
                if (input == 1 || input == 2 || input == 3) {
                    success = true;
                }
                else {
                    throw new InputMismatchException();
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Only 1, 2 and 3 are allowed, please try again");
                scanner.next();
            }
        }

        if (input == 1) {
            viewUserDetails(user, false);
            loginUser(user);
        }
        else if (input == 2) {
            editUser(user, false);
            loginUser(user);
        }
        else if (input == 3) {
            start();
        }
    }

    // Admin terminal to add or edit users
    public static void loginAdmin() {
        System.out.println("Enter 1 to add user");
        System.out.println("Enter 2 to edit user");
        System.out.println("Enter 3 to list users (ID and username)");
        System.out.println("Enter 4 to go back");
        int input = 0;
        boolean success = false;

        // Exception handling
        while (!success) {
            try {
                input = scanner.nextInt();
                scanner.nextLine();
                if (input == 1 || input == 2 || input == 3 || input == 4) {
                    success = true;
                }
                else {
                    throw new InputMismatchException();
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Only 1, 2, 3, and 4 are allowed, please try again");
                scanner.next();
            }
        }

        if (input == 1) {
            addUser();
            loginAdmin();
        }
        else if (input == 2) {
            System.out.println("Enter user ID to edit");
            int idInput;
            User locatedUser = null;
            boolean locateUserSuccess = false;

            while (!locateUserSuccess) {
                boolean idSuccess = false;
                while (!idSuccess) {
                    try {
                        idInput = scanner.nextInt();
                        scanner.nextLine();
                        idSuccess = true;

                        for (User user : Forum.getUsers()) {
                            if (idInput == user.getID()) {
                                locatedUser = user;
                                break;
                            }
                        }
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Only whole numbers are allowed, please try again");
                        scanner.next();
                    }
                }

                if (locatedUser != null) {
                    locateUserSuccess = true;
                    editUser(locatedUser, true);
                    loginAdmin();
                }
                else {
                    System.out.println("User for this ID does not exist");
                    System.out.println("Enter 1 to try again, anything else to go back");

                    int locateUserInput = 0;

                    // Exception handling
                    try {
                        locateUserInput = scanner.nextInt();
                        scanner.nextLine();
                        if (locateUserInput != 1) {
                            throw new InputMismatchException();
                        }
                    }
                    catch (InputMismatchException ignored) {
                        loginAdmin();
                    }
                }
            }
        }
        else if (input == 3) {
            listUsers();
            loginAdmin();
        }
        else if (input == 4) {
            start();
        }
    }

    // Adding/registering a user account, asks for name, password, and contact number
    // Creates a new User class
    public static void addUser() {
        System.out.println("Enter username");
        String username = scanner.nextLine().strip();

        System.out.println("Enter password");
        String password = scanner.nextLine().strip();

        System.out.println("Enter contact number");
        String contactNumber = scanner.nextLine().strip();

        for (User user : Forum.getUsers()) {
            if (username.equals(user.getUsername())) {
                System.out.println("Username already exists, try again");
                addUser();
            }
            break;
        }

        User.createUser(username, password, contactNumber);
        System.out.println("User has been registered");
    }

    // Edit user details as user or admin
    // isAdmin is true for admin
    //
    // Edits user details one by one
    //
    public static void editUser(User user, boolean isAdmin) {
        System.out.println("Editing user ID " + user.getID() + ", enter nothing to skip");

        System.out.println("Enter new username, current username is " + user.getUsername());
        String username = scanner.nextLine().strip();

        String newPassword = "";
        if (isAdmin) {
            System.out.println("Enter new password, current password is " + user.getPassword());
            newPassword = scanner.nextLine().strip();
        }
        else {
            System.out.println("Enter current password to change it, enter nothing to skip");
            String currentPassword = scanner.nextLine();
            boolean isCorrectPassword = false;

            while (!isCorrectPassword) {
                if (currentPassword.equals(user.getPassword())) {
                    System.out.println("Enter new password");
                    newPassword = scanner.nextLine().strip();
                    isCorrectPassword = true;
                }
                else if (currentPassword.isEmpty()) {
                    isCorrectPassword = true;
                }
                else {
                    System.out.println("Incorrect password, please try again (Enter nothing to skip)");
                }
            }
        }

        System.out.println("Enter new contact number, current contact number is " + user.getContactNumber());
        String contactNumber = scanner.nextLine().strip();

        if (!username.isEmpty()) {
            user.setUsername(username);
        }
        if (!newPassword.isEmpty()) {
            user.setPassword(newPassword);
        }
        if (!contactNumber.isEmpty()) {
            user.setContactNumber(contactNumber);
        }

        System.out.print("Saved user details");
    }

    // View individual user account details
    // Intended for use by normal users
    public static void viewUserDetails(User user, boolean showPassword) {
        System.out.println("Username: " + user.getUsername());
        if (showPassword) {
            System.out.println("Password: " + user.getPassword());
        }
        System.out.println("Contact Number: " + user.getContactNumber());
        System.out.println(" ");
    }

    public static void listUsers() {
        System.out.println("ID, Username");
        for (User user : Forum.getUsers()) {
            System.out.println(user.getID() + " " + user.getUsername());
        }
        System.out.println(" ");
    }
}
