package com.sgarlea.todo.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;

    @Valid
    @NotNull
    private Task task;

    @NotNull
    private Boolean active = Boolean.TRUE;

    public String getId() {
        return id;
    }

    public Notification setId(String id) {
        this.id = id;
        return this;
    }

    public Task getTask() {
        return task;
    }

    public Notification setTask(Task task) {
        this.task = task;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public Notification setActive(Boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(task, that.task) &&
                Objects.equal(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, task, active);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("task", task)
                .add("active", active)
                .toString();
    }

}


