package org.bashtan.MyApps.data.services.interfaces;

import org.bashtan.MyApps.data.dto.android.AndroidDTO;

import java.util.List;

public interface AndroidServiceInterface {
    void testRequest(AndroidDTO androidDTO);

//    void edit(AndroidDTO androidDTO);

    List<AndroidDTO> fetchAll();

    void sendRequest(AndroidDTO androidDTO);
}
