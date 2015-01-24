package HospitalApplication.email.operations;

import HospitalApplication.email.model.Email;

import java.util.ArrayList;

/**
 * Created by GUCIA on 2014-11-30.
 */
public interface EmailOp {

    boolean checkEmail(Email email);

    ArrayList<String> downloadExcelFile();

    ArrayList<String> reloadDatabase();
}
