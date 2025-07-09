package org.bashtan.MyApps.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bashtan.MyApps.data.dto.library.people.CreatePeopleDTO;
import org.bashtan.MyApps.data.dto.library.people.DeletePeopleRequestDTO;
import org.bashtan.MyApps.data.dto.library.people.PeopleDTO;
import org.bashtan.MyApps.data.dto.library.people.ResponsePeopleDTO;
import org.bashtan.MyApps.data.entities.library.PeopleEntity;
import org.bashtan.MyApps.data.repositories.PeopleRepository;
import org.bashtan.MyApps.data.services.interfaces.PeopleServiceInterface;
import org.bashtan.MyApps.data.services.interfaces.SearchServiceInterface;
import org.bashtan.MyApps.exceptions.BadRequestException;
import org.bashtan.MyApps.exceptions.DatabaseException;
import org.bashtan.MyApps.exceptions.NotFoundException;
import org.bashtan.MyApps.mappers.PeopleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class PeopleService implements PeopleServiceInterface {
    private final PeopleRepository peopleRepository;
    private final SearchServiceInterface searchService;

    @Override
    public void createPeople(CreatePeopleDTO peopleDTO) {
        validatePeopleDTO(peopleDTO);
        PeopleEntity createPeople = PeopleEntity
                .builder()
                .firstName(peopleDTO.getFirstName())
                .lastName(peopleDTO.getLastName())
                .gender(peopleDTO.getGender())
                .birthday(peopleDTO.getBirthday())
                .phone(peopleDTO.getPhone())
                .build();
        try {
            peopleRepository.save(createPeople);
        } catch (Exception e) {
            log.error("Error while create people", e);
            throw new DatabaseException("Error create people", e);        }
    }

    @Override
    public void editPeople(PeopleDTO peopleDTO) {
        PeopleDTO findPeople = getPeopleByID(peopleDTO.getId());
        if (peopleDTO.getFirstName() != null && !peopleDTO.getFirstName().isBlank()) {
            findPeople.setFirstName(peopleDTO.getFirstName());
        }
        if (peopleDTO.getLastName() != null && !peopleDTO.getLastName().isBlank()) {
            findPeople.setLastName(peopleDTO.getLastName());
        }
        if (peopleDTO.getBirthday() != null) {
            findPeople.setBirthday(peopleDTO.getBirthday());
        }
        if (peopleDTO.getGender() != null) {
            findPeople.setGender(peopleDTO.getGender());
        }
        if (peopleDTO.getPhone() != null && !peopleDTO.getPhone().isBlank()) {
            findPeople.setPhone(peopleDTO.getPhone());
        }
        peopleRepository.findByFirstNameAndLastNameAndBirthday(
                        findPeople.getFirstName(),
                        findPeople.getLastName(),
                        findPeople.getBirthday()
                ).filter(person-> person.getId()!=findPeople.getId())
                .ifPresent(person -> {
                    throw new BadRequestException("Such a person already exists..");
                });
        try {
            peopleRepository.save(PeopleMapper.toPeopleEntity(peopleDTO));
        } catch (Exception e) {
            log.error("Error while edit user", e);
            throw new DatabaseException("Error edit user", e);
        }
    }

    @Override
    public void deletePeople(DeletePeopleRequestDTO deletePeopleRequestDTO) {
        if (!deletePeopleRequestDTO.isCheckDelete()) {
            log.warn("Attempt to delete user without confirmation check");
            throw new BadRequestException("Confirmation check not installed: `checkDelete` must be true");
        }
//        PeopleDTO findPeople = getPeopleByID(deletePeopleRequestDTO.getId());
//        try {
//            peopleRepository.delete(
//                    peopleDTOMapper.toPeopleEntity(
//                            getPeopleByID(deletePeopleRequestDTO.getId())));
//        } catch (Exception e) {
//            log.error("Error while deleting user", e);
//            throw new DatabaseException("Error deleting people from database", e);
//        }
    }

    @Override
    public List<ResponsePeopleDTO> fetchAllPeople() {
        return peopleRepository
                .findAll()
                .stream()
                .map(PeopleMapper::toResponsePeopleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponsePeopleDTO> fetchPeopleBySymbol(String targetSymbol) {
        return searchService.searchPeopleBySymbol(targetSymbol);
    }

    @Override
    public PeopleDTO getPeopleByID(long id) {
        return peopleRepository.findById(id).map(PeopleMapper::toPeopleDTO)
                .orElseThrow(() -> new NotFoundException("People not found with ID: " + id));
    }

    private void validatePeopleDTO(CreatePeopleDTO peopleDTO) {
        if (peopleDTO.getFirstName()== null || peopleDTO.getFirstName().trim().isEmpty()) {
            throw new BadRequestException("First Name cannot be null or empty.");
        }
        if (peopleDTO.getLastName() == null || peopleDTO.getLastName().trim().isEmpty()) {
            throw new BadRequestException("Last Name cannot be null or empty.");
        }
        if (peopleDTO.getBirthday() == null) {
            throw new BadRequestException("Birthday cannot be null or empty.");
        }
        if (peopleDTO.getGender() == null) {
            throw new BadRequestException("Gender cannot be null or empty.");
        }
                    peopleRepository.findByFirstNameAndLastNameAndBirthday(
                            peopleDTO.getFirstName(),
                            peopleDTO.getLastName(),
                            peopleDTO.getBirthday()
                    ).ifPresent(person->{
                        throw new BadRequestException("Such a person already exists..");
        });
    }

}
