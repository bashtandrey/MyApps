package org.bashtan.MyApps.data.services.impl;


import lombok.RequiredArgsConstructor;
import org.bashtan.MyApps.data.dto.library.book.ResponseBookDTO;
import org.bashtan.MyApps.data.dto.library.people.ResponsePeopleDTO;
import org.bashtan.MyApps.data.dto.user.ResponseUserDTO;
import org.bashtan.MyApps.data.dto.church.youTube.VideoDTO;
import org.bashtan.MyApps.data.repositories.BookRepository;
import org.bashtan.MyApps.data.repositories.PeopleRepository;
import org.bashtan.MyApps.data.repositories.VideoRepository;
import org.bashtan.MyApps.mappers.BookMapper;
import org.bashtan.MyApps.mappers.PeopleMapper;
import org.bashtan.MyApps.mappers.VideoMapper;
import org.bashtan.MyApps.mappers.UserMapper;
import org.bashtan.MyApps.data.services.interfaces.SearchServiceInterface;
import org.bashtan.MyApps.data.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService implements SearchServiceInterface {
    private final UserRepository userRepository;
    private final PeopleRepository peopleRepository;
    private final BookRepository bookRepository;
    private final VideoRepository videoRepository;

    @Override
    public  List<ResponseUserDTO> searchUserBySymbol(String targetSymbol) {
            String loweCaseSymbol = symbolLowerCase(targetSymbol);
            return userRepository
                    .findAll()
                    .stream()
                    .filter(person ->
                            person.getLogin().toLowerCase().contains(loweCaseSymbol) ||
                            person.getEmail().toLowerCase().contains(loweCaseSymbol))
                    .map(UserMapper::toResponseUserDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public List<ResponsePeopleDTO> searchPeopleBySymbol(String targetSymbol) {
        String loweCaseSymbol = symbolLowerCase(targetSymbol);
        return peopleRepository
                .findAll()
                .stream()
                .filter(person ->
                                person.getFirstName().toLowerCase().contains(loweCaseSymbol) ||
                                person.getLastName().toLowerCase().contains(loweCaseSymbol) ||
                                person.getPhone().toLowerCase().contains(loweCaseSymbol)
                        )
                .map(PeopleMapper::toResponsePeopleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseBookDTO> searchBookBySymbol(String targetSymbol) {
        String loweCaseSymbol = symbolLowerCase(targetSymbol);
        return bookRepository
                .findAll()
                .stream()
                .filter(book->
                        book.getSerial().toLowerCase().contains(loweCaseSymbol) ||
                        book.getNameBook().toLowerCase().contains(loweCaseSymbol) ||
                        book.getPublishingYear().toLowerCase().contains(loweCaseSymbol) ||
                        book.getPublishingHouse().toLowerCase().contains(loweCaseSymbol))
                .map(BookMapper::toResponseBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VideoDTO> searchStreamBySymbol(String targetSymbol) {
        String loweCaseSymbol = symbolLowerCase(targetSymbol);
        return videoRepository
                .findAll()
                .stream()
                .filter(stream -> stream.getTitle().toLowerCase().contains(loweCaseSymbol)||
                        stream.getUrl().toLowerCase().contains(loweCaseSymbol))
                .map(VideoMapper::toVideoDTO)
                .collect(Collectors.toList());
    }

    private String symbolLowerCase(String targetSymbol){
        return targetSymbol.trim().toLowerCase();
    }
}
