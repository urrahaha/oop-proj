package forum;

import java.util.ArrayList;

public class Section {
    private final int id;
    private String sectionTitle;
    private Section parentSection;
    private ArrayList<Section> subsections;
    private final ArrayList<Thread> threads = new ArrayList<>();

    private Section(String sectionTitle) {
        this.id = Forum.getSections().size();
        this.sectionTitle = sectionTitle;
        Forum.getSections().add(this);
    }

    private Section(String sectionTitle, Section parentSection) {
        this.id = Forum.getSections().size();
        this.sectionTitle = sectionTitle;
        this.parentSection = parentSection;
        Forum.getSections().add(this);
        parentSection.getSubsections().add(this);
    }

    public int getID() { return id; }

    public String getSectionTitle() { return sectionTitle; }
    public void editSectionTitle(String sectionTitle) { this.sectionTitle = sectionTitle; }

    public Section getParentSection() { return parentSection; }
    public void setParentSection(Section parentSection) { this.parentSection = parentSection; }

    public ArrayList<Section> getSubsections() {
        createSubsections();
        return subsections;
    }
    public void createSubsections() {
        if (subsections == null) {
            subsections = new ArrayList<>();
        }
    }

    public ArrayList<Thread> getSectionThreads() { return threads; }

    public static Section createSection(String sectionTitle) {
        return new Section(sectionTitle);
    }

    public static Section createSection(String sectionTitle, Section parentSection) {
        return new Section(sectionTitle, parentSection);
    }
}
