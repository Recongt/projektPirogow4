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
 * Created by kuba on 2015-01-17.
 */
public class DawcaReload {
    public static List<Dawca> reloadDatabase(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigure.class);
        Email email = (Email) context.getBean("email");

        EmailService emService = (EmailService) context.getBean("emailService");
        DawcaService daService = (DawcaService) context.getBean("dawcaService");
        ExcelService exService = (ExcelService) context.getBean("excelService");

        System.out.println("Realoading database");
        ArrayList<String> paths = emService.reloadDatabase();
        for(String path : paths){
            exService.setPath(path);
            Dawca da = daService.findDawcaByPesel(exService.getPesel());
            if(da != null){
                da.setDateUpdate(exService.getDate());
                da.setHowLongOiom();
                da.setRozpoznanie(exService.getRozpoznanie());
                da.setObserwacje(exService.getObserwacje());
                da.setWywiad(exService.getWywiad());
                new File(exService.getPath()).delete();
                if(da.getHowLongOiom() <= 130){
                    daService.updateDawca(da);

                }
                else{
                    daService.deleteDawca(da);
                }
            }
            else{
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
                if(da.getHowLongOiom() <= 130){
                    daService.persistDawca(da);
                }
            }
        }
        return daService.list();
    }
}
