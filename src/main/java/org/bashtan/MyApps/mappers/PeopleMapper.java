package org.bashtan.MyApps.mappers;

import org.bashtan.MyApps.data.dto.library.people.PeopleDTO;
import org.bashtan.MyApps.data.dto.library.people.ResponsePeopleDTO;
import org.bashtan.MyApps.data.entities.library.PeopleEntity;
import org.springframework.stereotype.Component;

@Component
public class PeopleMapper {
    public static ResponsePeopleDTO toResponsePeopleDTO(PeopleEntity peopleEntity){
        return ResponsePeopleDTO.builder()
                .id(peopleEntity.getId())
                .firstName(peopleEntity.getFirstName())
                .lastName(peopleEntity.getLastName())
                .birthday(peopleEntity.getBirthday())
                .gender(peopleEntity.getGender())
                .phone(peopleEntity.getPhone())
                .build();
    }
    public static PeopleDTO toPeopleDTO(PeopleEntity peopleEntity){
        if (peopleEntity == null) {
            return null;
        }
        return PeopleDTO.builder()
                .id(peopleEntity.getId())
                .firstName(peopleEntity.getFirstName())
                .lastName(peopleEntity.getLastName())
                .birthday(peopleEntity.getBirthday())
                .gender(peopleEntity.getGender())
                .phone(peopleEntity.getPhone())
                .remotePeople(peopleEntity.isRemotePeople())
                .build();
    }
    public static PeopleEntity toPeopleEntity(PeopleDTO peopleDTO){
        if (peopleDTO == null) {
            return null;
        }
        return PeopleEntity.builder()
                .id(peopleDTO.getId())
                .firstName(peopleDTO.getFirstName())
                .lastName(peopleDTO.getLastName())
                .birthday(peopleDTO.getBirthday())
                .gender(peopleDTO.getGender())
                .phone(peopleDTO.getPhone())
                .remotePeople(peopleDTO.isRemotePeople())
                .build();
    }
}
