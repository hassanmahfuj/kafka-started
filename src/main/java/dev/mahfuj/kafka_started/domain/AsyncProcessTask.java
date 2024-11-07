package dev.mahfuj.kafka_started.domain;

public class AsyncProcessTask {

    private Long processId;
    private String name;
    private String moduleName;
    private String domainType;
    private String task;
    private String status;
    private Long total;
    private Long count;

    public Long getProcessId() {
        return processId;
    }

    public AsyncProcessTask setProcessId(Long processId) {
        this.processId = processId;
        return this;
    }

    public String getName() {
        return name;
    }

    public AsyncProcessTask setName(String name) {
        this.name = name;
        return this;
    }

    public String getModuleName() {
        return moduleName;
    }

    public AsyncProcessTask setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    public String getDomainType() {
        return domainType;
    }

    public AsyncProcessTask setDomainType(String domainType) {
        this.domainType = domainType;
        return this;
    }

    public String getTask() {
        return task;
    }

    public AsyncProcessTask setTask(String task) {
        this.task = task;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public AsyncProcessTask setStatus(String status) {
        this.status = status;
        return this;
    }

    public Long getTotal() {
        return total;
    }

    public AsyncProcessTask setTotal(Long total) {
        this.total = total;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public AsyncProcessTask setCount(Long count) {
        this.count = count;
        return this;
    }

    @Override
    public String toString() {
        return "AsyncProcessTask{" +
                "processId=" + processId +
                ", name='" + name + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", domainType='" + domainType + '\'' +
                ", task='" + task + '\'' +
                ", status='" + status + '\'' +
                ", total=" + total +
                ", count=" + count +
                '}';
    }
}
