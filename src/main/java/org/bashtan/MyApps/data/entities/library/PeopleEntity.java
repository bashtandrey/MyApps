package org.bashtan.MyApps.data.entities.library;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.bashtan.MyApps.enums.Gender;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "peoples")
public class PeopleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    @Builder.Default
    private long id = 0L;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthday", nullable = false)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate birthday;

    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Builder.Default
    @Column(name = "phone")
    private String phone = "";

    @Builder.Default
    @Column(name = "remote_people",nullable = false)
    private boolean remotePeople = false;

    @OneToMany(mappedBy = "people")
//    @JsonIgnore
    private List<BookEntity> books;

    @OneToMany(mappedBy = "people", fetch = FetchType.LAZY)
//    @JsonIgnore
    private List<BookHistoryEntity> bookHistory;
}