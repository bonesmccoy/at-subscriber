package co.alphai.atsubscriber;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StreamSubscriber extends Thread
{
    Configuration configuration;

    public StreamSubscriber(Configuration configuration)
    {
        this.configuration = configuration;

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
        String configurationFilePath = args[0];
        try {
            String fileContents = new String(Files.readAllBytes(Paths.get(configurationFilePath)));
            Configuration appConfiguration = new Configuration(fileContents);

            System.out.println(appConfiguration.toString());

            StreamSubscriber subscriber = new StreamSubscriber(appConfiguration);

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
