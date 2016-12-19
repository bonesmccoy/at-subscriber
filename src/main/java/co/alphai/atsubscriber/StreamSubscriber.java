package co.alphai.atsubscriber;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StreamSubscriber extends Thread
{

    public StreamSubscriber()
    {
        start();
    }

    public void run()
    {
        System.out.println("I'm running");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true)
        {
            try {
                String line = br.readLine();
                if (line.length() > 0) {
                    if (line.startsWith("quit"))
                        System.out.println("Bye");
                        break;
                }
            } catch (IOException e) {
                System.out.println("IO error trying to read your input!");
            }
        }
    }

    public static void main(String args[])
    {
        StreamSubscriber subscriber = new StreamSubscriber();
    }
}
