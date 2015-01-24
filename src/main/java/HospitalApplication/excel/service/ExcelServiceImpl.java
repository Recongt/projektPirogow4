package HospitalApplication.excel.service;

import HospitalApplication.excel.operations.ExcelOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by GUCIA on 2014-12-04.
 */
@Service("excelService")
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    ExcelOperations excelOp;

    @Override
    public ArrayList<String> getWywiad() {
        return excelOp.getWywiad();
    }

    @Override
    public ArrayList<String> getRozpoznanie() {
       return excelOp.getRozpoznanie();
    }

    @Override
    public ArrayList<String> getObserwacje() {
       return excelOp.getObserwacje();
    }

    @Override
    public String getPesel() {
        return excelOp.getPesel();
    }

    @Override
    public String getDate() {
        return excelOp.getDate();
    }

    @Override
    public void setPath(String path) {
        excelOp.setPath(path);
    }

    @Override
    public String getPath() {
        return excelOp.getPath();
    }
}
