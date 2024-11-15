package dev.mahfuj.kafka_started.domain;

public class AsyncTask {

    private Long id;
    private String domainData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomainData() {
        return domainData;
    }

    public void setDomainData(String domainData) {
        this.domainData = domainData;
    }
}
