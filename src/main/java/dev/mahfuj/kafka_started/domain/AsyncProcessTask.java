package dev.mahfuj.kafka_started.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "processId",
        "name",
        "moduleName",
        "domainType",
        "taskId",
        "domainData",
        "status"
})
public class AsyncProcessTask {

    @JsonProperty("processId")
    private Long processId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("moduleName")
    private String moduleName;

    @JsonProperty("domainType")
    private String domainType;

    @JsonProperty("taskId")
    private Long taskId;

    @JsonProperty("domainData")
    private String domainData;

    @JsonProperty("status")
    private String status;

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

    public Long getTaskId() {
        return taskId;
    }

    public AsyncProcessTask setTaskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getDomainData() {
        return domainData;
    }

    public AsyncProcessTask setDomainData(String domainData) {
        this.domainData = domainData;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public AsyncProcessTask setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "AsyncProcessTask{" +
                "processId=" + processId +
                ", name='" + name + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", domainType='" + domainType + '\'' +
                ", taskId=" + taskId +
                ", domainData='" + domainData + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
