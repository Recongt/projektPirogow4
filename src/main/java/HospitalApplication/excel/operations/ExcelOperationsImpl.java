package HospitalApplication.excel.operations;

import Exception.FilloException;
import Fillo.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by GUCIA on 2014-12-04.
 */
@Repository("excelOp")
public class ExcelOperationsImpl implements ExcelOperations {

    private String path;

    @Override
    public ArrayList<String> getWywiad() {
        Recordset recordset = null;
        Connection conn = null;
        ArrayList<String> wywiad = new ArrayList<String>();
        String columnName = "wywiad";

        try {
            Fillo fillo = new Fillo();
            conn = fillo.getConnection(path);
            String query = "SELECT wywiad FROM Sheet1";
            recordset = conn.executeQuery(query);
            recordset.next();
            wywiad.add(recordset.getField(columnName));

            String field = null;
            while(recordset.next()){
                field = recordset.getField(columnName);
                if(!field.equals(""))
                    wywiad.add(field);
            }

            recordset.close();
            conn.close();
        } catch (FilloException ex) {
            System.out.println("Nie znaleziono aktualizacji do: "+columnName);
        }
        return wywiad;
    }

    @Override
    public ArrayList<String> getRozpoznanie() {
        Recordset recordset = null;
        Connection conn = null;
        ArrayList<String> rozpoznanie = new ArrayList<String>();

        try {
            Fillo fillo = new Fillo();
            conn = fillo.getConnection(path);
            String query = "SELECT ROZPOZNANIE FROM chora_1 WHERE ROZPOZNANIE";

            recordset = conn.executeQuery("Select rozpoznanie from Sheet1");
            recordset.next();
            rozpoznanie.add(recordset.getField("rozpoznanie"));

            String field = null;
            while(recordset.next()){
                field = recordset.getField("rozpoznanie");
                if(!field.equals(""))
                    rozpoznanie.add(field);
            }

            recordset.close();
            conn.close();
        } catch (FilloException ex) {
            ex.printStackTrace();
        }
        return rozpoznanie;
    }

    @Override
    public ArrayList<String> getObserwacje() {
        Recordset recordset = null;
        Connection conn = null;
        ArrayList<String> obserwacje = new ArrayList<String>();

        try {
            Fillo fillo = new Fillo();
            conn = fillo.getConnection(path);
            String query = "SELECT obserwacje FROM Sheet1";
            recordset = conn.executeQuery(query);
            recordset.next();
            obserwacje.add(recordset.getField("obserwacje"));

            String field = null;
            while(recordset.next()){
                field = recordset.getField("obserwacje");
                if(!field.equals(""))
                    obserwacje.add(field);
            }

            recordset.close();
            conn.close();
        } catch (FilloException ex) {
            ex.printStackTrace();
        }
        return obserwacje;
    }

    @Override
    public String getPesel() {
        Recordset recordset = null;
        Connection conn = null;
        String pesel = null;

        String columnName = "pesel";
        try {
            Fillo fillo = new Fillo();
            conn = fillo.getConnection(path);
            String query = "SELECT pesel FROM Sheet1";
            recordset = conn.executeQuery(query);
            recordset.next();
            pesel = recordset.getField(columnName);

            String field = null;
            while(recordset.next()){
                field = recordset.getField(columnName);
                if((!field.equals("")) && (field.length() == 11))
                    pesel = field;
            }

            recordset.close();
            conn.close();
        } catch (FilloException ex) {
            ex.printStackTrace();
        }
        return pesel;
    }

    @Override
    public String getDate() {
        Recordset recordset = null;
        Connection conn = null;
        String date = null;

        String columnName = "data";
        try {
            Fillo fillo = new Fillo();
            conn = fillo.getConnection(path);
            String query = "SELECT data FROM Sheet1";
            recordset = conn.executeQuery(query);
            recordset.next();
            date = recordset.getField(columnName);

            String field = null;
            while(recordset.next()){
                field = recordset.getField(columnName);
                if(!field.equals(""))
                    date = field;
            }
            recordset.close();
            conn.close();
        } catch (FilloException ex) {
            System.out.println("Nie znaleziono aktualizacji do: "+columnName);
        }
        return date;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return this.path;
    }
}
