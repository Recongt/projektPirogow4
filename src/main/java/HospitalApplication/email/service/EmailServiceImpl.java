package HospitalApplication.email.service;

import HospitalApplication.email.model.Email;
import HospitalApplication.email.operations.EmailOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


/**
 * Created by GUCIA on 2014-11-30.
  */

@Service("emailService")
public class EmailServiceImpl implements EmailService {

    @Autowired
    EmailOp emailOp;

    @Override
    public boolean checkEmail(Email email) {
         return emailOp.checkEmail(email);
    }

    @Override
    public ArrayList<String> downloadExcelFile() {
        return emailOp.downloadExcelFile();
    }

    @Override
    public ArrayList<String> reloadDatabase() {
        return emailOp.reloadDatabase();
    }

}
