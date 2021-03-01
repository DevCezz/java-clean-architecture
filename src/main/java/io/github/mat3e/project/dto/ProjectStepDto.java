package io.github.mat3e.project.dto;

public interface ProjectStepDto {

    int getId();

    String getDescription();

    int getDaysToProjectDeadline();

    class DeserializationImpl implements ProjectStepDto {

        private final int id;
        private final String description;
        private final int daysToProjectDeadline;

        public DeserializationImpl(final int id, final String description, final int daysToProjectDeadline) {
            this.id = id;
            this.description = description;
            this.daysToProjectDeadline = daysToProjectDeadline;
        }

        @Override
        public int getId() {
            return 0;
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public int getDaysToProjectDeadline() {
            return 0;
        }
    }
}
