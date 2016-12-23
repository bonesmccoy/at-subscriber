package org.bones.alphai.subscriber.activetick;

import at.feedapi.ATCallback;
import at.feedapi.ActiveTickServerAPI;
import at.feedapi.Helpers;
import at.feedapi.Session;
import at.utils.jlib.Errors;
import at.utils.jlib.OutputMessage;
import at.shared.ATServerAPIDefines;
import org.bones.alphai.subscriber.Configuration;

public class APISession extends ATCallback implements
        ATCallback.ATLoginResponseCallback, ATCallback.ATServerTimeUpdateCallback,
        ATCallback.ATRequestTimeoutCallback, ATCallback.ATSessionStatusChangeCallback,
        ATCallback.ATOutputMessageCallback

{
    at.feedapi.Session session;
    ActiveTickServerAPI serverApi;
    protected Configuration configuration;
    ServerRequester serverRequester;
    StreamListener streamListener;

    long lastLoginRequestId;
    String userId;
    String password;
    ATServerAPIDefines.ATGUID apiKey;

    public APISession(ActiveTickServerAPI serverApiObject, Configuration configuration)
    {
        serverApi = serverApiObject;
        this.configuration = configuration;
    }

    public ActiveTickServerAPI GetServerAPI()
    {
        return serverApi;
    }

    public at.feedapi.Session GetSession()
    {
        return session;
    }

    public StreamListener GetStreamer()
    {
        return streamListener;
    }

    public ServerRequester getServerRequester()
    {
        return serverRequester;
    }

    public boolean connect()
    {
        boolean success = false;
        if(configuration.getApiKey().length() != 32) {
            System.out.println("Warning! \n\tApiUserIdGuid should be 32 characters long and alphanumeric only.");

            return success;
        }

        ATServerAPIDefines.ATGUID atGuId = (new ATServerAPIDefines()).new ATGUID();
        atGuId.SetGuid(configuration.getApiKey());

        success = initialize(
                atGuId,
                configuration.getAtHostName(),
                configuration.getAtPort(),
                configuration.getUsername(),
                configuration.getPassword()
        );

        String message = String.format("\nConnection %s", (success == true) ? "ok" : "fail");
        Helper.Log(message);

        return success;
    }

    public boolean initialize(ATServerAPIDefines.ATGUID apiKey, String serverHostname, int serverPort, String userId, String password)
    {
        boolean initSuccessful = false;

        if(session != null) {
            serverApi.ATShutdownSession(session);
        }

        session = serverApi.ATCreateSession();

        streamListener = new StreamListener(this, configuration.getCollectorUrl());
        serverRequester = new ServerRequester(this, streamListener);

        this.userId = userId;
        this.password = password;
        this.apiKey = apiKey;

        long apiKeyResult = serverApi.ATSetAPIKey(session, this.apiKey);
        if (apiKeyResult == Errors.ERROR_SUCCESS) {

            session.SetServerTimeUpdateCallback(this);
            session.SetOutputMessageCallback(this);

            initSuccessful = serverApi.ATInitSession(session, serverHostname, serverHostname, serverPort, this);

            Helper.Log(serverApi.GetAPIVersionInformation());
            Helper.Log("--------------------------------------------------------------------");

        } else {
            Helper.Log(String.format("Invalid API KEY. ERROR %d", apiKeyResult));
        }

        return initSuccessful;
    }

    public boolean UnInit()
    {
        if(session != null)
        {
            serverApi.ATShutdownSession(session);
            session = null;
        }

        return true;
    }

    //ATLoginResponseCallback
    public void process(Session session, long requestId, ATServerAPIDefines.ATLOGIN_RESPONSE response)
    {
        String loginResponseDescription = Helper.getLoginResponseDescription(response);

        Helper.LogResponse(requestId, String.format("Login Response [%s]", loginResponseDescription));
    }

    /**
     * ATServerTimeUpdateCallback
     *
     * @param serverTime
     */
    public void process(ATServerAPIDefines.SYSTEMTIME serverTime)
    {
    }

    /**
     * ATRequestTimeoutCallback
     *
     * @param requestId long
     */
    public void process(long requestId)
    {
        Helper.LogResponse(requestId, "Request Time Out");
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
            this
        );

        boolean rc = serverApi.ATSendRequest(
            session,
            lastLoginRequestId,
            ActiveTickServerAPI.DEFAULT_REQUEST_TIMEOUT,
            this
        );

        message = "Login request [" + userId + "] (rc = " + (char) Helpers.ConvertBooleanToByte(rc) + ")";
        Helper.LogRequest(lastLoginRequestId, message);
    }

    /**
     * OutputMessageCallback
     * @param outputMessage outputMessage
     */
    public void process(OutputMessage outputMessage)
    {
        Helper.Log(outputMessage.GetMessage());
    }


}
