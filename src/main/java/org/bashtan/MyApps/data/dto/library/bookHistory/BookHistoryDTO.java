package org.bashtan.MyApps.data.dto.library.bookHistory;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.bashtan.MyApps.data.dto.library.book.BookDTO;
import org.bashtan.MyApps.data.dto.library.people.PeopleDTO;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class BookHistoryDTO {
    private BookDTO bookDTO;
    private PeopleDTO peopleDTO;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate borrowedDate;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate returnDate;

}
