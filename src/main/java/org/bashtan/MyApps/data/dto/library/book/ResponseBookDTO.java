package org.bashtan.MyApps.data.dto.library.book;

import lombok.*;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseBookDTO {

    private long id;
    private String serial;
    private String nameBook;
    private String publishingYear;
    private String publishingHouse;
    private String description;
}
