	
/*
javac -classpath json-simple-1.1.1.jar CreateJSON.java
java -cp json-simple-1.1.1.jar:./ CreateJSON
*/

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.nio.file.FileVisitResult.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;

//import java.lang.Object;
//import org.json.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CreateJSON {

	public static JSONObject createJson(List<String> filepaths) {

		List<JSONObject> json = new ArrayList<JSONObject>();
		JSONObject root = new JSONObject();

		for(int i = 0; i < filepaths.size(); i++) {
			JSONObject info = new JSONObject();
			info.put("Name", "crunchify.com");
			info.put("Author", "App Shah");
			JSONArray images = new JSONArray();

			
			int counter = 0;
			if(IsMultiple(filepaths.get(i)) == -1){
				JSONObject object = new JSONObject();
				object.put("path", filepaths.get(i));
				images.add(object);
				info.put("images", images);
			}
			else {
				while(IsMultiple(filepaths.get(i)) > counter){
					JSONObject object = new JSONObject();
					object.put("path", filepaths.get(i));
					System.out.println("adding: "  + filepaths.get(i) + " to array. Size: " + images.size());
					images.add(object);
					i++;
				}
				info.put("images", images);
			}
			json.add(info);
		}
		root.put("users", json);
		return root;

	}


	public static int IsMultiple(String pathname) {
		int firstpunct = pathname.indexOf('.');
		try {
			String a = Character.toString(pathname.charAt(firstpunct+1));
			int b = Integer.valueOf(a.toString());
			System.out.println("Tegn: " + b);
			return b;
		} catch(NumberFormatException e) {
			System.out.println("var ikke streng");
		}
		return -1;
		

	}

	public static void main(String[] args) {



		List<String> filepaths = new ArrayList<String>();


		try {
			Files.walk(Paths.get("images")).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					System.out.println(filePath);
					
					
					String jsonImagePath = JSONValue.toJSONString(filePath);				

					if(!jsonImagePath.contains(".DS_Store")) {
						filepaths.add(jsonImagePath);
					}

				}
			});

			JSONObject root = createJson(filepaths);
			System.out.println(root.size());
			try (FileWriter file = new FileWriter("images.json")) {
				file.write(root.toJSONString());
				/*for(int i = 0; i < json.size(); i++) {
					file.write(json.get(i).toJSONString());
				}*/
			}
		} catch (IOException e) {
			System.out.println(e);
		}

	}
}