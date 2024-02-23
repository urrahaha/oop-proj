package forum;

import login.LoginRegister;

import java.util.Objects;

import static login.LoginRegister.currentUser;
import static login.LoginRegister.scanner;

public class ForumView {

    public static void start() {
        System.out.println("Welcome to the forums");
        demoInit();

        System.out.println();
        displaySections();
        System.out.println();

        System.out.println("x to exit, numbers to browse sections");
        String input = scanner.nextLine();

        Section locatedSection = null;

        for (Section section : Forum.getSections()) {
            if (Objects.equals(input, Integer.toString(section.getID()))) {
                locatedSection = section;
                break;
            }
        }

        do {
            if (Objects.equals(input, "x")) {
                LoginRegister.start();
            }
            else if (locatedSection != null) {
                browseSection(locatedSection);
            }
            else {
                input = "";
            }
        }
        while (input.isEmpty());
    }

    public static void browseSection(Section section) {
        System.out.println("Browsing " + section.getSectionTitle());

        System.out.println();
        displayThreads();
        System.out.println();

        System.out.println("x to go back, numbers to browse threads, p to post thread");
        String input = scanner.nextLine();

        Thread locatedThread = null;

        for (Thread thread : Forum.getThreads()) {
            if (Objects.equals(input, Integer.toString(thread.getID()))) {
                locatedThread = thread;
                break;
            }
        }

        do {
            if (Objects.equals(input, "x")) {
                start();
            }
            else if (locatedThread != null) {
                browseThread(locatedThread, section);
            }
            else if (Objects.equals(input, "p")) {
                createSectionThread(section);
            }
            else {
                input = "";
            }
        }
        while (input.isEmpty());
    }

    public static void browseThread(Thread thread, Section section) {
        System.out.println("Browsing thread: " + thread.getThreadTitle());

        System.out.println();
        displayThreadPosts(thread);
        System.out.println();

        System.out.println("x to go back, p to post in this thread");
        String input = scanner.nextLine();

        do {
            if (Objects.equals(input, "x")) {
                browseSection(section);
            }
            else if (Objects.equals(input, "p")) {
                postInThread(thread, section);
            }
            else {
                input = "";
            }
        }
        while (input.isEmpty());
    }

    public static void displaySections() {
        for (Section section : Forum.getSections()) {
            System.out.println(section.getID() + " " + section.getSectionTitle());
        }
    }

    public static void displayThreads() {
        if (Forum.getThreads().isEmpty()) {
            System.out.println("There aren't any threads in this section yet");
            return;
        };

        for (Thread thread : Forum.getThreads()) {
            System.out.println(thread.getID() + " " + thread.getThreadTitle());
        }
    }

    public static void displayThreadPosts(Thread thread) {
        for (Thread.Post post : thread.getPosts()) {
            System.out.println(post.getPoster());
            System.out.println(post.getContent());
            System.out.println();
        }
    }

    public static void createSectionThread(Section section) {
        System.out.println("Write: ");
        String content = scanner.nextLine();

        Thread thread = Thread.createThread(content, currentUser, section);

        browseSection(section);
    }

    public static void postInThread(Thread thread, Section section) {
        System.out.println("Write: ");
        String content = scanner.nextLine();

        thread.createThreadPost(content, currentUser);

        browseThread(thread, section);
    }

    public static void demoInit() {
        if (Forum.getSections().isEmpty()) {
            Section.createSection("Section 0");
            Section.createSection("Section 1");
            Section.createSection("Section 2");
        }
    }
}
