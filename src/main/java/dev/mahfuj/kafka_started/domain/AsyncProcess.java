package dev.mahfuj.kafka_started.domain;

import java.util.List;
import java.util.stream.Collectors;

public class AsyncProcess {

    private Long id;
    private String name;
    private String moduleName;
    private String domainType;
    private List<String> tasks;

    public Long getId() {
        return id;
    }

    public AsyncProcess setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AsyncProcess setName(String name) {
        this.name = name;
        return this;
    }

    public String getModuleName() {
        return moduleName;
    }

    public AsyncProcess setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    public String getDomainType() {
        return domainType;
    }

    public AsyncProcess setDomainType(String domainType) {
        this.domainType = domainType;
        return this;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public AsyncProcess setTasks(List<String> tasks) {
        this.tasks = tasks;
        return this;
    }

    public List<AsyncProcessTask> convertToTasks() {
        return tasks.stream()
                .map(task -> new AsyncProcessTask()
                        .setProcessId(this.id)
                        .setName(this.name)
                        .setModuleName(this.moduleName)
                        .setDomainType(this.domainType)
                        .setTask(task))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "AsyncProcess{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", domainType='" + domainType + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
