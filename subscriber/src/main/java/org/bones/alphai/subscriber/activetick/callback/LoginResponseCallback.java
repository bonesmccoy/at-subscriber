package org.bones.alphai.subscriber.activetick.callback;

import at.feedapi.ATCallback;
import at.feedapi.Session;
import at.shared.ATServerAPIDefines;
import org.bones.alphai.subscriber.activetick.Helper;

public class LoginResponseCallback extends ATCallback implements ATCallback.ATLoginResponseCallback
{
    //ATLoginResponseCallback
    public void process(Session session, long requestId, ATServerAPIDefines.ATLOGIN_RESPONSE response)
    {
        String loginResponseDescription = Helper.getLoginResponseDescription(response);

        Helper.LogResponse(requestId, String.format("Login Response [%s]", loginResponseDescription));
    }
}
