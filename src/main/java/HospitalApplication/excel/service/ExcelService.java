package HospitalApplication.excel.service;

import java.util.ArrayList;

/**
 * Created by GUCIA on 2014-12-04.
 */
public interface ExcelService {
    ArrayList<String> getWywiad();

    ArrayList<String> getRozpoznanie();

    ArrayList<String> getObserwacje();

    String getPesel();

    String getDate();

    void setPath(String path);

    String getPath();
}
