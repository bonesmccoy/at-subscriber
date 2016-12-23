package org.bones.alphai.subscriber.activetick.callback;

import at.feedapi.ATCallback;
import at.shared.ATServerAPIDefines;
import at.utils.jlib.OutputMessage;
import org.bones.alphai.subscriber.activetick.Helper;

public class ServerMessagesCallback extends ATCallback implements ATCallback.ATServerTimeUpdateCallback,
        ATCallback.ATRequestTimeoutCallback, ATCallback.ATOutputMessageCallback
{
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
