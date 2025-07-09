package org.bashtan.MyApps.mappers;

import org.bashtan.MyApps.data.dto.library.book.BookDTO;
import org.bashtan.MyApps.data.dto.library.book.CreateBookDTO;
import org.bashtan.MyApps.data.dto.library.book.ResponseBookDTO;
import org.bashtan.MyApps.data.entities.library.BookEntity;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public static ResponseBookDTO toResponseBookDTO(BookEntity bookEntity){
        return ResponseBookDTO
                .builder()
                .id(bookEntity.getId())
                .serial(bookEntity.getSerial())
                .nameBook(bookEntity.getNameBook())
                .publishingYear(bookEntity.getPublishingYear())
                .publishingHouse(bookEntity.getPublishingHouse())
                .description(bookEntity.getDescription())
                .build();
    }
    public static BookEntity toBookEntity(CreateBookDTO createBookDTO){
        return BookEntity
                .builder()
                .serial(createBookDTO.getSerial())
                .nameBook(createBookDTO.getNameBook())
                .publishingYear(createBookDTO.getPublishingYear())
                .publishingHouse(createBookDTO.getPublishingHouse())
                .description(createBookDTO.getDescription())
                .build();
    }
    public static BookEntity toBookEntity(BookDTO bookDTO) {
        return BookEntity
                .builder()
                .id(bookDTO.getId())
                .serial(bookDTO.getSerial())
                .nameBook(bookDTO.getNameBook())
                .publishingYear(bookDTO.getPublishingYear())
                .publishingHouse(bookDTO.getPublishingHouse())
                .description(bookDTO.getDescription())
                .dateOfCreated(bookDTO.getDateOfCreated())
                .borrowedDate(bookDTO.getBorrowedDate())
                .remoteBook(bookDTO.isRemoteBook())
                .people(PeopleMapper.toPeopleEntity(bookDTO.getPeopleDTO()))
                .bookHistory(BookHistoryMapper.toBookHistoryEntities(bookDTO.getBookHistoryDTOS()))
                .build();
    }
    public static BookDTO toBookDTO(BookEntity bookEntity) {
        return BookDTO
                .builder()
                .id(bookEntity.getId())
                .serial(bookEntity.getSerial())
                .nameBook(bookEntity.getNameBook())
                .publishingYear(bookEntity.getPublishingYear())
                .publishingHouse(bookEntity.getPublishingHouse())
                .description(bookEntity.getDescription())
                .dateOfCreated(bookEntity.getDateOfCreated())
                .borrowedDate(bookEntity.getBorrowedDate())
                .remoteBook(bookEntity.isRemoteBook())
                .peopleDTO(PeopleMapper.toPeopleDTO(bookEntity.getPeople()))
                .bookHistoryDTOS(BookHistoryMapper.toBookHistoryDTOS(bookEntity.getBookHistory()))
                .build();
    }


}
