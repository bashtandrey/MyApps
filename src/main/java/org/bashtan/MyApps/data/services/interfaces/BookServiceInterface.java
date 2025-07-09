package org.bashtan.MyApps.data.services.interfaces;


import org.bashtan.MyApps.data.dto.library.book.BookDTO;
import org.bashtan.MyApps.data.dto.library.book.CreateBookDTO;
import org.bashtan.MyApps.data.dto.library.book.ResponseBookDTO;
import org.bashtan.MyApps.enums.BookAction;

import java.util.List;


public interface BookServiceInterface {
    void createBook(CreateBookDTO bookDTO);
    void editBook(BookDTO bookDTO, BookAction bookAction);
//    void deletePeople(DeletePeopleRequestDTO deletePeopleRequestDTO);
    List<ResponseBookDTO> fetchAllBook();
    List<ResponseBookDTO> fetchBookBySymbol(String targetSymbol);
}
