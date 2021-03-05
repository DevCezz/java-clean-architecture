package io.github.mat3e.project;

class ProjectStep {
    private int id;
    private String description;
    private int daysToProjectDeadline;
    private Project project;

    private ProjectStep(final int id, final String description, final int daysToProjectDeadline, final Project project) {
        this.id = id;
        this.description = description;
        this.daysToProjectDeadline = daysToProjectDeadline;
        this.project = project;
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public int getDaysToProjectDeadline() {
        return daysToProjectDeadline;
    }

    void setDaysToProjectDeadline(int daysToProjectDeadline) {
        this.daysToProjectDeadline = daysToProjectDeadline;
    }

    public Project getProject() {
        return project;
    }

    void setProject(Project project) {
        this.project = project;
    }
}
