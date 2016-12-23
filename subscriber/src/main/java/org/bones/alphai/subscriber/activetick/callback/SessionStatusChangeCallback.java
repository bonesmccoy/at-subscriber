package org.bones.alphai.subscriber.activetick.callback;

import at.feedapi.ATCallback;
import at.feedapi.ActiveTickServerAPI;
import at.feedapi.Helpers;
import at.shared.ATServerAPIDefines;
import org.bones.alphai.subscriber.activetick.APISession;
import org.bones.alphai.subscriber.activetick.Helper;

public class SessionStatusChangeCallback extends ATCallback implements ATCallback.ATSessionStatusChangeCallback
{
    private final ActiveTickServerAPI serverApi;
    private APISession apiSession;
    private final String userId;
    private final String password;
    private long lastLoginRequestId;

    public SessionStatusChangeCallback(APISession apiSession, ActiveTickServerAPI serverAPI, String userId, String password)
    {
        super();
        this.serverApi = serverAPI;
        this.apiSession = apiSession;
        this.userId = userId;
        this.password = password;
    }

    /**
     * ATSessionStatusChangeCallback
     *
     * @param session session
     * @param type integer
     */
    public void process(at.feedapi.Session session, ATServerAPIDefines.ATSessionStatusType type)
    {
        String statusDescription = Helper.getSessionStatusDescription(type);

        String message = String.format("Received Session status change: %s", statusDescription);
        Helper.LogResponse(lastLoginRequestId, message);

        if(type.m_atSessionStatusType == ATServerAPIDefines.ATSessionStatusType.SessionStatusConnected)
        {
            doLogin(session);
        }
    }

    private void doLogin(at.feedapi.Session session) {
        String message;
        lastLoginRequestId = serverApi.ATCreateLoginRequest(
            session,
            userId,
            password,
            apiSession
        );

        boolean rc = serverApi.ATSendRequest(
            session,
            lastLoginRequestId,
            ActiveTickServerAPI.DEFAULT_REQUEST_TIMEOUT,
            apiSession
        );

        message = "Login request [" + userId + "] (rc = " + (char) Helpers.ConvertBooleanToByte(rc) + ")";
        Helper.LogRequest(lastLoginRequestId, message);
    }
}
