package dev.mahfuj.kafka_started.processes.domain;

public class RateChange {

    private String accountNumber;
    private Long profitRate;

    public RateChange() {
    }

    public RateChange(String accountNumber, Long profitRate) {
        this.accountNumber = accountNumber;
        this.profitRate = profitRate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public RateChange setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public Long getProfitRate() {
        return profitRate;
    }

    public RateChange setProfitRate(Long profitRate) {
        this.profitRate = profitRate;
        return this;
    }

    @Override
    public String toString() {
        return "RateChange{" +
                "accountNumber='" + accountNumber + '\'' +
                ", profitRate=" + profitRate +
                '}';
    }
}
