package org.bashtan.MyApps.data.entities.library;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookHistory")
public class BookHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    @Builder.Default
    private long id = 0L;

    @ManyToOne()
    @JoinColumn(name = "book",nullable = false,updatable = false)
    private BookEntity book;

    @ManyToOne()
    @JoinColumn(name = "people",nullable = false,updatable = false)
    private PeopleEntity people;

    @Column(name = "borrowed_date",nullable = false,updatable = false)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate borrowedDate;

    @Builder.Default
    @Column(name = "return_date",nullable = false,updatable = false)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate returnDate = LocalDate.now();
}
