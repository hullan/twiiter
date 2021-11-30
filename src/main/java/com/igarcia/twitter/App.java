package com.igarcia.twitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class App {
	public static void main(String[] args) {
		
		Twitter twitter = TwitterFactory.getSingleton();

		Path filePath = Paths.get("test.txt");

		try {
			
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			
			Files.writeString(filePath, "fecha/hora;aplicacion;usuario;contenido\n", StandardOpenOption.CREATE);

			twitter.getHomeTimeline().stream().iterator().forEachRemaining(t -> {
				try {
					Files.writeString(filePath,
							format.format(t.getCreatedAt()) + ";"
									+ t.getSource().substring(t.getSource().indexOf(">") + 1,
											t.getSource().indexOf("</"))
									+ ";" + t.getUser().getName() + ";"
									+ t.getText().replace("\n", "").replace("\r", "") + "\n",
							StandardOpenOption.APPEND);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

		} catch (TwitterException | IOException e) {
			e.printStackTrace();
		}
	}
}
