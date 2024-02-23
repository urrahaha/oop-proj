package forum;

import login.User;

import java.util.ArrayList;

public class Forum {
    private final static ArrayList<Thread> THREADS = new ArrayList<>();
    private final static ArrayList<User> USERS = new ArrayList<>();
    private final static ArrayList<Section> SECTIONS = new ArrayList<>();

    public static ArrayList<Thread> getThreads() { return THREADS; }

    public static ArrayList<User> getUsers() { return USERS; }

    public static ArrayList<Section> getSections() { return SECTIONS; }
}
