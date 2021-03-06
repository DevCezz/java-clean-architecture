package io.github.mat3e.task.vo;

public class TaskSourceId {
    private String sourceId;

    protected TaskSourceId() {}

    public TaskSourceId(final String sourceId) {
        this.sourceId = sourceId;
    }

    String getSourceId() {
        return sourceId;
    }
}
