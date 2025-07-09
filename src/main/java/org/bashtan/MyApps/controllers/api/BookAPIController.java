package org.bashtan.MyApps.controllers.api;

import lombok.RequiredArgsConstructor;
import org.bashtan.MyApps.data.dto.library.book.BookDTO;
import org.bashtan.MyApps.data.dto.library.book.CreateBookDTO;
import org.bashtan.MyApps.data.dto.library.book.ResponseBookDTO;
import org.bashtan.MyApps.data.services.interfaces.BookServiceInterface;
import org.bashtan.MyApps.enums.BookAction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
@Transactional
public class BookAPIController {
    private final BookServiceInterface bookService;

    private static final String CREATE = "/create";
    private static final String EDITE = "/edit";
    private static final String BORROWED = "/borrowed";
    private static final String RETURN = "/return";
    private static final String DELETE = "/delete";
    private static final String FETCH_ALL = "/fetchAll";
    private static final String SEARCH_BY_SYMBOL = "/searchBySymbol";


    @PostMapping(CREATE)
//    @PermitAll
    public ResponseEntity<Void> createBook(@RequestBody CreateBookDTO createBookDTO) {
        bookService.createBook(createBookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(EDITE)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> editBook (@RequestBody BookDTO bookDTO) {
        bookService.editBook(bookDTO, BookAction.EDIT_BOOK);
        return ResponseEntity.ok().build();
    }
    @PatchMapping(BORROWED)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> borrowedBook (@RequestBody BookDTO bookDTO) {
        bookService.editBook(bookDTO, BookAction.BORROWED_BOOK);
        return ResponseEntity.ok().build();
    }
    @PatchMapping(RETURN)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> returnBook (@RequestBody BookDTO bookDTO) {
        bookService.editBook(bookDTO, BookAction.RETURN_BOOK);
        return ResponseEntity.ok().build();
    }

//    @DeleteMapping(DELETE)
////    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Void> deleteBook(@RequestBody DeletePeopleRequestDTO deletePeopleRequestDTO) {
//        peopleService.deletePeople(deletePeopleRequestDTO);
//        return ResponseEntity.noContent().build();
//    }
//
    @GetMapping(FETCH_ALL)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ResponseBookDTO>> fetchAllBook() {
        return new ResponseEntity<>(bookService.fetchAllBook(), HttpStatus.OK);
    }

    @GetMapping(SEARCH_BY_SYMBOL)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ResponseBookDTO>> fetchBookBySymbol(
            @RequestParam(value = "target_symbol") String targetSymbol) {
        return new ResponseEntity<>(bookService
                .fetchBookBySymbol(targetSymbol), HttpStatus.OK);
    }
}
