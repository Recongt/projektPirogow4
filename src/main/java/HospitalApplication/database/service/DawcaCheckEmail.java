package HospitalApplication.database.service;

import HospitalApplication.database.model.Dawca;
import HospitalApplication.email.model.Email;
import HospitalApplication.email.service.EmailService;
import HospitalApplication.excel.service.ExcelService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuba on 2015-01-18.
 */
public class DawcaCheckEmail {
    public static List<Dawca> checkEmail() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigure.class);
        Email email = (Email) context.getBean("email");

        EmailService emService = (EmailService) context.getBean("emailService");
        DawcaService daService = (DawcaService) context.getBean("dawcaService");
        ExcelService exService = (ExcelService) context.getBean("excelService");

        if (emService.checkEmail(email) == true) {
            ArrayList<String> paths = emService.downloadExcelFile();
            String pesel = null;
            for (String path : paths) {
                exService.setPath(path);
                Dawca da = daService.findDawcaByPesel(exService.getPesel());
                if (da != null) {
                    da.setDateUpdate(exService.getDate());
                    da.setHowLongOiom();
                    da.setRozpoznanie(exService.getRozpoznanie());
                    da.setObserwacje(exService.getObserwacje());
                    da.setWywiad(exService.getWywiad());
                    new File(exService.getPath()).delete();
                    daService.updateDawca(da);
                } else {
                    da = new Dawca();
                    da.setPesel(exService.getPesel());
                    da.setDate(exService.getDate());
                    da.setDateUpdate(exService.getDate());
                    da.setHowLongOiom();
                    da.setAge();
                    da.setSex();
                    da.setRozpoznanie(exService.getRozpoznanie());
                    da.setObserwacje(exService.getObserwacje());
                    da.setWywiad(exService.getWywiad());
                    new File(exService.getPath()).delete();
                    daService.persistDawca(da);
                }
            }
        } else {
            System.out.println("nothing new");
            List<Dawca> dawcyDateUpdate = daService.list();
            for (Dawca dawca : dawcyDateUpdate) {
                dawca.setHowLongOiom();
                daService.updateDawca(dawca);
                if(dawca.getHowLongOiom() > 130){
                    daService.deleteDawca(dawca);
                }
            }
        }
        return daService.list();
    }
}
