package com.example.ewidencja.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="EVENTS")
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private LocalDate date;
    private String accoutingNumber;
    private Double income;
    private Double expenses;
    private String comment;

    public Event(){}
    public Event(LocalDate date, String accoutingNumber, Double income, Double expenses, String comment) {
        this.date = date;
        this.accoutingNumber = accoutingNumber;
        this.income = income;
        this.expenses = expenses;
        this.comment = comment;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date=date;
    }

    public String getAccoutingNumber() {
        return accoutingNumber;
    }

    public void setAccoutingNumber(String accoutingNumber) {
        this.accoutingNumber=accoutingNumber;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income=income;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses=expenses;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment=comment;
    }

    public User getUsers() {
        return user;
    }

    public void setUsers(User user) {
        this.user=user;
    }
}
