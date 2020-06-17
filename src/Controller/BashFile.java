/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Joseph Salas
 */
public class BashFile {

    private ArrayList<String> commands;
    
    public BashFile() {
        commands = new ArrayList<String>();
    }
    
    public ArrayList<String> getCommands(File bashFile) throws FileNotFoundException
    {
        Scanner fileReader = new Scanner(bashFile);
        while (fileReader.hasNextLine()) {
        String data = fileReader.nextLine();
        commands.add(data);
        System.out.println(data);
        }
        return commands;
    }
    
    
    
}
