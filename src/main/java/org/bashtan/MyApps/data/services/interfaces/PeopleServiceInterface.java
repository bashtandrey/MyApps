package org.bashtan.MyApps.data.services.interfaces;


import org.bashtan.MyApps.data.dto.library.people.CreatePeopleDTO;
import org.bashtan.MyApps.data.dto.library.people.DeletePeopleRequestDTO;
import org.bashtan.MyApps.data.dto.library.people.PeopleDTO;
import org.bashtan.MyApps.data.dto.library.people.ResponsePeopleDTO;

import java.util.List;


public interface PeopleServiceInterface {
    void createPeople(CreatePeopleDTO peopleDTO);

    void editPeople(PeopleDTO peopleDTO);

    void deletePeople(DeletePeopleRequestDTO deletePeopleRequestDTO);

    PeopleDTO getPeopleByID(long id);

    List<ResponsePeopleDTO> fetchAllPeople();

    List<ResponsePeopleDTO> fetchPeopleBySymbol(String targetSymbol);
}
