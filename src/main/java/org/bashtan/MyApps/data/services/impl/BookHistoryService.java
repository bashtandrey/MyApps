package org.bashtan.MyApps.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bashtan.MyApps.data.dto.library.book.BookDTO;
import org.bashtan.MyApps.data.entities.library.BookHistoryEntity;
import org.bashtan.MyApps.data.repositories.BookHistoryRepository;
import org.bashtan.MyApps.data.services.interfaces.BookHistoryServiceInterface;
import org.bashtan.MyApps.exceptions.DatabaseException;
import org.bashtan.MyApps.mappers.BookMapper;
import org.bashtan.MyApps.mappers.PeopleMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class BookHistoryService implements BookHistoryServiceInterface {
    private final BookHistoryRepository bookHistoryRepository;

    @Override
    public void create(BookDTO bookDTO) {
        try {
            bookHistoryRepository.save(BookHistoryEntity
                    .builder()
                    .book(BookMapper.toBookEntity(bookDTO))
                    .people(PeopleMapper.toPeopleEntity(bookDTO.getPeopleDTO()))
                    .borrowedDate(bookDTO.getBorrowedDate())
                    .build());
        }catch (Exception e) {
            log.error("Error while create Book History", e);
            throw new DatabaseException("Error create Book History", e);
        }
    }
}
