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
    private final LoginResponseCallback loginResponseCallback;
    private final ServerMessagesCallback serverMessagesCallback;
    private long lastLoginRequestId;

    public SessionStatusChangeCallback(
            ActiveTickServerAPI serverAPI,
            String userId,
            String password,
            LoginResponseCallback loginResponseCallback,
            ServerMessagesCallback serverMessagesCallback
    ) {
        super();
        this.serverApi = serverAPI;
        this.userId = userId;
        this.password = password;
        this.loginResponseCallback = loginResponseCallback;
        this.serverMessagesCallback = serverMessagesCallback;
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
            apiSession.getLoginResponseCallback()
        );

        boolean rc = serverApi.ATSendRequest(
            session,
            lastLoginRequestId,
            ActiveTickServerAPI.DEFAULT_REQUEST_TIMEOUT,
            apiSession.getServerMessagesCallback()
        );

        message = "Login request [" + userId + "] (rc = " + (char) Helpers.ConvertBooleanToByte(rc) + ")";
        Helper.LogRequest(lastLoginRequestId, message);
    }
}
