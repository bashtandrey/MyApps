package org.bashtan.MyApps.data.entities.library;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    @Builder.Default
    private long id = 0L;

    @Column(name = "serial", updatable = false, nullable = false, unique = true)
    private String serial;

    @Column(name = "name_book", nullable = false)
    private String nameBook;

    @Builder.Default
    @Column(name = "publishing_year")
    private String publishingYear = "";

    @Builder.Default
    @Column(name = "publishing_house")
    private String publishingHouse = "";

    @Builder.Default
    @Column(name = "description", length = 500)
    private String description = "";

    @Builder.Default
    @Column(name = "date_of_created", updatable = false, nullable = false)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate dateOfCreated = LocalDate.now();

    @Column(name = "borrowed_date ")
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate borrowedDate;

    @Builder.Default
    @Column(name = "remote_book", nullable = false)
    private boolean remoteBook = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private PeopleEntity people;

    @OneToMany(mappedBy = "book" ,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BookHistoryEntity> bookHistory;
}
