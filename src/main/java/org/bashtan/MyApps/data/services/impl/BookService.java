package org.bashtan.MyApps.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bashtan.MyApps.data.dto.library.book.BookDTO;
import org.bashtan.MyApps.data.dto.library.book.CreateBookDTO;
import org.bashtan.MyApps.data.dto.library.book.ResponseBookDTO;
import org.bashtan.MyApps.data.repositories.BookRepository;
import org.bashtan.MyApps.data.services.interfaces.BookHistoryServiceInterface;
import org.bashtan.MyApps.data.services.interfaces.BookServiceInterface;
import org.bashtan.MyApps.data.services.interfaces.PeopleServiceInterface;
import org.bashtan.MyApps.data.services.interfaces.SearchServiceInterface;
import org.bashtan.MyApps.enums.BookAction;
import org.bashtan.MyApps.exceptions.BadRequestException;
import org.bashtan.MyApps.exceptions.DatabaseException;
import org.bashtan.MyApps.exceptions.NotFoundException;
import org.bashtan.MyApps.mappers.BookMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class BookService implements BookServiceInterface {

    private final BookRepository bookRepository;

    private final SearchServiceInterface searchService;
    private final PeopleServiceInterface peopleService;
    private final BookHistoryServiceInterface bookHistoryService;


    @Override
    public void createBook(CreateBookDTO createBookDTO) {
        validatePeopleDTO(createBookDTO);
        try {
            bookRepository.save(BookMapper.toBookEntity(createBookDTO));
        } catch (Exception e) {
            log.error("Error while create Book", e);
            throw new DatabaseException("Error create Book", e);
        }

    }

    @Override
    public void editBook(BookDTO bookDTO, BookAction bookAction) {
        BookDTO findBookDTO  = getBookByID(bookDTO.getId());
        if (findBookDTO.isRemoteBook()){
            throw new BadRequestException("The book has been removed, any actions are prohibited.");
        }
        switch (bookAction) {
            case EDIT_BOOK -> {
                setParameter(bookDTO.getNameBook(), findBookDTO::setNameBook);
                setParameter(bookDTO.getPublishingYear(), findBookDTO::setPublishingYear);
                setParameter(bookDTO.getPublishingHouse(), findBookDTO::setPublishingHouse);
                setParameter(bookDTO.getDescription(), findBookDTO::setDescription);
            }
            case BORROWED_BOOK -> {
                if (findBookDTO.getPeopleDTO()!= null){
                    throw new BadRequestException("The book is already borrowed");
                }
                findBookDTO.setPeopleDTO(peopleService.getPeopleByID(bookDTO.getPeopleDTO().getId()));
                findBookDTO.setBorrowedDate(LocalDate.now());
            }
            case RETURN_BOOK -> {
                if(findBookDTO.getPeopleDTO()==null){
                    throw new BadRequestException("The book is not borrowed");
                }
                bookHistoryService.create(findBookDTO);
                findBookDTO.setPeopleDTO(null);
                findBookDTO.setBorrowedDate(null);
            }
            default -> throw new IllegalStateException("Unexpected value: " + bookAction);
        }
        try {
            bookRepository.save(BookMapper.toBookEntity(findBookDTO));
        } catch (Exception e) {
            log.error("Error while edit user", e);
            throw new DatabaseException("Error edit user", e);
        }
    }

    @Override
    public List<ResponseBookDTO> fetchAllBook() {
        return bookRepository
                .findAll()
                .stream()
                .map(BookMapper::toResponseBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseBookDTO> fetchBookBySymbol(String targetSymbol) {
        return searchService.searchBookBySymbol(targetSymbol);
    }

    private <T> void setParameter(T value, Consumer<T> setter){
        if (value != null) {
            if(value instanceof String stringValue){
                if (!stringValue.isBlank()){
                    setter.accept(value);
                }
            } else {
                setter.accept(value);
            }
        }
    }

    private void validatePeopleDTO(CreateBookDTO bookDTO) {
        if (bookDTO.getSerial()== null || bookDTO.getSerial().trim().isEmpty()) {
            throw new BadRequestException("Serial cannot be null or empty.");
        }
        if (bookDTO.getNameBook() == null || bookDTO.getNameBook().trim().isEmpty()) {
            throw new BadRequestException("Name Book cannot be null or empty.");
        }

        bookRepository.findBySerial(bookDTO.getSerial())
                .ifPresent(person->{
            throw new BadRequestException("Such a Serial already exists..");
        });
    }

    private BookDTO getBookByID(Long id) {
        return bookRepository.findById(id).map(BookMapper::toBookDTO)
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + id));
    }

}
