package co.alphai.atsubscriber;

import at.feedapi.ATCallback;
import at.feedapi.ActiveTickServerAPI;
import at.feedapi.Helpers;
import at.feedapi.Session;
import at.utils.jlib.Errors;
import at.utils.jlib.OutputMessage;
import at.shared.ATServerAPIDefines;

public class APISession extends ATCallback implements
        ATCallback.ATLoginResponseCallback, ATCallback.ATServerTimeUpdateCallback,
        ATCallback.ATRequestTimeoutCallback, ATCallback.ATSessionStatusChangeCallback,
        ATCallback.ATOutputMessageCallback

{
    at.feedapi.Session session;
    ActiveTickServerAPI serverApi;
    ServerRequester serverRequester;
    Streamer streamer;

    long lastRequest;
    String userId;
    String password;
    ATServerAPIDefines.ATGUID apiKey;

    public APISession(ActiveTickServerAPI serverApiObject)
    {
        serverApi = serverApiObject;
    }

    public ActiveTickServerAPI GetServerAPI()
    {
        return serverApi;
    }

    public at.feedapi.Session GetSession()
    {
        return session;
    }

    public Streamer GetStreamer()
    {
        return streamer;
    }

    public ServerRequester getServerRequester()
    {
        return serverRequester;
    }

    public boolean Init(ATServerAPIDefines.ATGUID apiKey, String serverHostname, int serverPort, String userId, String password)
    {
        if(session != null)
            serverApi.ATShutdownSession(session);

        session = serverApi.ATCreateSession();
        streamer = new Streamer(this);
        serverRequester = new ServerRequester(this, streamer);

        this.userId = userId;
        this.password = password;
        this.apiKey = apiKey;

        long rc = serverApi.ATSetAPIKey(session, this.apiKey);

        session.SetServerTimeUpdateCallback(this);
        session.SetOutputMessageCallback(this);

        boolean initrc = false;
        if(rc == Errors.ERROR_SUCCESS)
            initrc = serverApi.ATInitSession(session, serverHostname, serverHostname, serverPort, this);

        System.out.println(serverApi.GetAPIVersionInformation());
        System.out.println("--------------------------------------------------------------------");

        return initrc;
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
        String strLoginResponseType = "";
        switch(response.loginResponse.m_atLoginResponseType)
        {
            case ATServerAPIDefines.ATLoginResponseType.LoginResponseSuccess: strLoginResponseType = "LoginResponseSuccess"; break;
            case ATServerAPIDefines.ATLoginResponseType.LoginResponseInvalidUserid: strLoginResponseType = "LoginResponseInvalidUserid"; break;
            case ATServerAPIDefines.ATLoginResponseType.LoginResponseInvalidPassword: strLoginResponseType = "LoginResponseInvalidPassword"; break;
            case ATServerAPIDefines.ATLoginResponseType.LoginResponseInvalidRequest: strLoginResponseType = "LoginResponseInvalidRequest"; break;
            case ATServerAPIDefines.ATLoginResponseType.LoginResponseLoginDenied: strLoginResponseType = "LoginResponseLoginDenied"; break;
            case ATServerAPIDefines.ATLoginResponseType.LoginResponseServerError: strLoginResponseType = "LoginResponseServerError"; break;
            default: strLoginResponseType = "unknown"; break;
        }

        System.out.println("RECV " + requestId + ": Login Response [" + strLoginResponseType + "]");
    }

    //ATServerTimeUpdateCallback
    public void process(ATServerAPIDefines.SYSTEMTIME serverTime)
    {
    }

    //ATRequestTimeoutCallback
    public void process(long origRequest)
    {
        System.out.println("(" + origRequest + "): Request timed-out\n");
    }

    //ATSessionStatusChangeCallback
    public void process(at.feedapi.Session session, ATServerAPIDefines.ATSessionStatusType type)
    {
        String strStatusType = "";
        switch(type.m_atSessionStatusType)
        {
            case ATServerAPIDefines.ATSessionStatusType.SessionStatusConnected: strStatusType = "SessionStatusConnected"; break;
            case ATServerAPIDefines.ATSessionStatusType.SessionStatusDisconnected: strStatusType = "SessionStatusDisconnected"; break;
            case ATServerAPIDefines.ATSessionStatusType.SessionStatusDisconnectedDuplicateLogin: strStatusType = "SessionStatusDisconnectedDuplicateLogin"; break;
            default: break;
        }

        System.out.println("RECV Status change [" + strStatusType + "]");

        //if we are connected to the server, send a login request
        if(type.m_atSessionStatusType == ATServerAPIDefines.ATSessionStatusType.SessionStatusConnected)
        {
            lastRequest = serverApi.ATCreateLoginRequest(session, userId, password, this);
            boolean rc = serverApi.ATSendRequest(session, lastRequest, ActiveTickServerAPI.DEFAULT_REQUEST_TIMEOUT, this);

            System.out.println("SEND (" + lastRequest + "): Login request [" + userId + "] (rc = " + (char) Helpers.ConvertBooleanToByte(rc) + ")");
        }
    }

    public void process(OutputMessage outputMessage)
    {
        System.out.println(outputMessage.GetMessage());
    }
}
