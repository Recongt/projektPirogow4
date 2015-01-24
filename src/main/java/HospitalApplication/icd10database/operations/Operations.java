package HospitalApplication.icd10database.operations;

import Exception.FilloException;
import Fillo.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GUCIA on 2014-12-04.
 */
public class Operations {
    public static Map<String, String> generateIcd10Dictionary(){

        Map<String, String> words = new HashMap<String, String>();
        Recordset recordset = null;
        Connection conn = null;
        String key = null;
        String value = null;

        try {
            Fillo fillo = new Fillo();
            conn = fillo.getConnection("Icd10_final.xlsx");
            String query = "SELECT * FROM Sheet1";
            recordset = conn.executeQuery(query);
            recordset.next();
            key = recordset.getField("key");
            value = recordset.getField("value");

            while(recordset.next()){
                words.put(recordset.getField("key"),recordset.getField("value"));
                words.put(recordset.getField("value"),recordset.getField("key"));
            }
            recordset.close();
            conn.close();
        } catch (FilloException ex) {
            ex.printStackTrace();
        }
        return words;
        }
    }
