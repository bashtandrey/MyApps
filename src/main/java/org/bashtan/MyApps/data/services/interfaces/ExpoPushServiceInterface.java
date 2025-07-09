package org.bashtan.MyApps.data.services.interfaces;

import org.bashtan.MyApps.data.dto.PushMessage;

public interface ExpoPushServiceInterface {
    void sendPushNotificationAllToken(PushMessage pushMessage);

    void sendPush(PushMessage pushMessage);

    void sendAdminRegisterNewEmail(String email);
}
