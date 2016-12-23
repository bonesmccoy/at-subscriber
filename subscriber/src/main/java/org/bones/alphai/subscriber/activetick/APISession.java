package org.bones.alphai.subscriber.activetick;

import at.feedapi.ATCallback;
import at.feedapi.ActiveTickServerAPI;
import at.feedapi.Session;
import at.utils.jlib.Errors;
import at.utils.jlib.OutputMessage;
import at.shared.ATServerAPIDefines;
import org.bones.alphai.subscriber.Configuration;
import org.bones.alphai.subscriber.activetick.callback.SessionStatusChangeCallback;

public class APISession extends ATCallback implements
        ATCallback.ATLoginResponseCallback, ATCallback.ATServerTimeUpdateCallback,
        ATCallback.ATRequestTimeoutCallback, ATCallback.ATOutputMessageCallback

{
    private at.feedapi.Session session;
    private ActiveTickServerAPI serverApi;
    private Configuration configuration;
    private ServerRequester serverRequester;
    private StreamListener streamListener;

    private long lastLoginRequestId;
    private String userId;
    private String password;
    private ATServerAPIDefines.ATGUID apiKey;

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
        if(configuration.getApiKey().length() != 32) {
            System.out.println("Warning! \n\tApiUserIdGuid should be 32 characters long and alphanumeric only.");

            return false;
        }

        ATServerAPIDefines.ATGUID atGuId = (new ATServerAPIDefines()).new ATGUID();
        atGuId.SetGuid(configuration.getApiKey());

        boolean success = initializeSession(
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

    private boolean initializeSession(ATServerAPIDefines.ATGUID apiKey, String serverHostname, int serverPort, String userId, String password)
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
            initSuccessful = serverApi.ATInitSession(
                    session,
                    serverHostname,
                    serverHostname,
                    serverPort,
                    new SessionStatusChangeCallback(
                            this,
                            serverApi,
                            userId,
                            password
                    )
            );

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
     * OutputMessageCallback
     * @param outputMessage outputMessage
     */
    public void process(OutputMessage outputMessage)
    {
        Helper.Log(outputMessage.GetMessage());
    }
}
