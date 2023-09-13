/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.od;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author calebemicael
 */
public class Helper {
    static List<String> tokensInLine(String line){
	String[] tokens = line.split("<|>|([a-z][A-Z][0-9])+");
	ArrayList<String> lista = new ArrayList<>();
	for(String token: tokens){
	    lista.add(token);
	}
	return lista;
    }
}
