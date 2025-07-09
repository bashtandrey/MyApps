package org.bashtan.MyApps.data.dto.library.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.bashtan.MyApps.data.dto.library.bookHistory.BookHistoryDTO;
import org.bashtan.MyApps.data.dto.library.people.PeopleDTO;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private long id;
    private String serial;
    private String nameBook;
    private String publishingYear;
    private String publishingHouse;
    private String description;

    @Builder.Default
    private LocalDate dateOfCreated = LocalDate.now();
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate borrowedDate;
    private boolean remoteBook;
    private PeopleDTO peopleDTO;
    private List<BookHistoryDTO> bookHistoryDTOS;

}
