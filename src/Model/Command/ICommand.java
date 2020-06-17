/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import java.util.ArrayList;

/**
 *
 * @author Paulo
 */
public interface ICommand {
    void execute(ArrayList params);
    String getCommandText();
}
