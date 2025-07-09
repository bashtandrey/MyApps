package org.bashtan.MyApps.data.dto.library.book;

import lombok.*;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateBookDTO {
    private String serial;
    private String nameBook;
    @Builder.Default
    private String publishingYear="";
    @Builder.Default
    private String publishingHouse="";
    @Builder.Default
    private String description="";
}
