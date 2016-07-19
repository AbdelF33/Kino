package userControllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


public class Folder {

	private String FULL_PATH_FOLDER;
	private String folderName;
	private String targetHD;
	private String myFile;
	private String extension;
	private String fileName;
	private List<String> lines;
	
	public Folder(String folderName, String targetHD) {
		this.folderName = folderName;
		this.targetHD = targetHD;
		FULL_PATH_FOLDER = targetHD + File.separator + folderName + File.separator ;
		this.lines = new ArrayList<>();
	}
	
	//Creation de dossiers
	public void createFolder(){
		Path target = Paths.get(getFullPathFolder());
		try {
			Files.createDirectory(target);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Creation des fichiers
	public void createFile(String monFichier){
		this.myFile = monFichier;
		try {
			Files.createFile(Paths.get(getFullPathFolder() + File.separator + monFichier));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Parcourir les fichiers
	public void browseFolder(String Ext){
		Path directory = Paths.get(getFullPathFolder());
		this.extension = Ext;
		
		if (Ext == null) {Ext = "*";}
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, Ext)){
			for (Path path: stream){
				System.out.println(path.getFileName());
			}
		} catch (IOException x) {
			System.out.println(x);
		}
	}
	
	//Ecrire dans un fichier
	public void writeFile(String fileName, String[] myLines){
		this.fileName = fileName;
		Path fileRead = Paths.get(getFullPathFolder() + File.separator + fileName);
		
		for (String text : myLines) {
			this.lines.add(text);
		}
		
		try {
			Files.write(fileRead, this.lines, Charset.defaultCharset(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Lire dans un fichier
	public void readFile(String fileName){
		this.fileName = fileName;
		Path fileRead = Paths.get(getFullPathFolder() + File.separator + fileName);

		try {
			this.lines = Files.readAllLines(fileRead,Charset.defaultCharset());
			for (String string : this.lines) {
				System.out.println(string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getFullPathFolder() {
		return FULL_PATH_FOLDER;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getTargetHD() {
		return targetHD;
	}

	public void setTargetHD(String targetHD) {
		this.targetHD = targetHD;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getMyFile() {
		return myFile;
	}

	public void setMyFile(String myFile) {
		this.myFile = myFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
