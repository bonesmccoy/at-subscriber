package org.bones.alphai.subscriber.activetick;

import at.shared.ATServerAPIDefines;

public class Helper
{
    public static void PrintOut(String message)
    {
        System.out.println(message);
    }

    public static void PrintResponse(long requestId, String message)
    {
        PrintOut(String.format("RECEIVED request[%d]: %s", requestId, message));
    }

    public static void PrintRequest(long requestId, String message)
    {
        PrintOut(String.format("SEND request[%d]: %s", requestId, message));
    }

    public static String getSessionStatusDescription(ATServerAPIDefines.ATSessionStatusType sessionStatusType) {

        String statusDescription;

        switch(sessionStatusType.m_atSessionStatusType)
        {
            case ATServerAPIDefines.ATSessionStatusType.SessionStatusConnected:
                statusDescription = "Connected";
                break;
            case ATServerAPIDefines.ATSessionStatusType.SessionStatusDisconnected:
                statusDescription = "Disconnected";
                break;
            case ATServerAPIDefines.ATSessionStatusType.SessionStatusDisconnectedInactivity:
                statusDescription = "Inactive";
                break;
            case ATServerAPIDefines.ATSessionStatusType.SessionStatusDisconnectedDuplicateLogin:
                statusDescription = "Disconnected for Duplicated login";
                break;
            default:
                statusDescription = "Unknown";
                break;
        }

        return statusDescription;
    }

    public static String getLoginResponseDescription(ATServerAPIDefines.ATLOGIN_RESPONSE response) {

        String loginResponseDescription;

        switch (response.loginResponse.m_atLoginResponseType) {
            case ATServerAPIDefines.ATLoginResponseType.LoginResponseSuccess:
                loginResponseDescription = "LoginResponseSuccess";
                break;
            case ATServerAPIDefines.ATLoginResponseType.LoginResponseInvalidUserid:
                loginResponseDescription = "LoginResponseInvalidUserid";
                break;
            case ATServerAPIDefines.ATLoginResponseType.LoginResponseInvalidPassword:
                loginResponseDescription = "LoginResponseInvalidPassword";
                break;
            case ATServerAPIDefines.ATLoginResponseType.LoginResponseInvalidRequest:
                loginResponseDescription = "LoginResponseInvalidRequest";
                break;
            case ATServerAPIDefines.ATLoginResponseType.LoginResponseLoginDenied:
                loginResponseDescription = "LoginResponseLoginDenied";
                break;
            case ATServerAPIDefines.ATLoginResponseType.LoginResponseServerError:
                loginResponseDescription = "LoginResponseServerError";
                break;
            default:
                loginResponseDescription = "unknown";
                break;
        }

        return loginResponseDescription;
    }
}
