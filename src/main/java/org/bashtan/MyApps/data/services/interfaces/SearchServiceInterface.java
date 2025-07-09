package org.bashtan.MyApps.data.services.interfaces;

import org.bashtan.MyApps.data.dto.library.book.ResponseBookDTO;
import org.bashtan.MyApps.data.dto.library.people.ResponsePeopleDTO;
import org.bashtan.MyApps.data.dto.user.ResponseUserDTO;
import org.bashtan.MyApps.data.dto.church.youTube.VideoDTO;

import java.util.List;

public interface SearchServiceInterface {
    List<ResponseUserDTO> searchUserBySymbol(String targetSymbol);
    List<ResponsePeopleDTO> searchPeopleBySymbol(String targetSymbol);
    List<ResponseBookDTO> searchBookBySymbol(String targetSymbol);
    List<VideoDTO> searchStreamBySymbol(String targetSymbol);

}
