package com.example.ewidencja.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="EVENTS")
@NoArgsConstructor
@EqualsAndHashCode
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Setter
    @Getter
    private LocalDate date;
    @Setter
    @Getter
    private String accoutingNumber;
    @Setter
    @Getter
    private Double income;
    @Setter
    @Getter
    private Double expenses;
    @Setter
    @Getter
    private String comment;

    public Event(LocalDate date, String accoutingNumber, Double income, Double expenses, String comment) {
        this.date = date;
        this.accoutingNumber = accoutingNumber;
        this.income = income;
        this.expenses = expenses;
        this.comment = comment;
    }

    @Setter
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public User getUsers() {
        return user;
    }

}
