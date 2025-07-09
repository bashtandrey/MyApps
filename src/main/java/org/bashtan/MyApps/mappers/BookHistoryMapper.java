package org.bashtan.MyApps.mappers;

import org.bashtan.MyApps.data.dto.library.bookHistory.BookHistoryDTO;
import org.bashtan.MyApps.data.entities.library.BookHistoryEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookHistoryMapper {
    private static BookHistoryDTO toBookHistoryDTO(BookHistoryEntity bookHistoryEntity){
        return BookHistoryDTO
                .builder()
                .bookDTO(BookMapper.toBookDTO(bookHistoryEntity.getBook()))
                .peopleDTO(PeopleMapper.toPeopleDTO(bookHistoryEntity.getPeople()))
                .borrowedDate(bookHistoryEntity.getBorrowedDate())
                .returnDate(bookHistoryEntity.getReturnDate())
                .build();
    }
    private static BookHistoryEntity toBookHistoryEntity(BookHistoryDTO bookHistoryDTO){
        return BookHistoryEntity
                .builder()
                .book(BookMapper.toBookEntity(bookHistoryDTO.getBookDTO()))
                .people(PeopleMapper.toPeopleEntity(bookHistoryDTO.getPeopleDTO()))
                .borrowedDate(bookHistoryDTO.getBorrowedDate())
                .returnDate(bookHistoryDTO.getReturnDate())
                .build();
    }

    public static List<BookHistoryDTO> toBookHistoryDTOS(List<BookHistoryEntity> bookHistoryEntities){
        if (bookHistoryEntities == null) {
            return Collections.emptyList();
        }
        return bookHistoryEntities
                .stream()
                .map(BookHistoryMapper::toBookHistoryDTO)
                .collect(Collectors.toList());
    }
    public static List<BookHistoryEntity> toBookHistoryEntities(List<BookHistoryDTO> bookHistoryDTOS){
        if (bookHistoryDTOS == null) {
            return Collections.emptyList();
        }
        return bookHistoryDTOS
                .stream()
                .map(BookHistoryMapper::toBookHistoryEntity)
                .collect(Collectors.toList());
    }

}
