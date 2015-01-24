/**
 * Created by GUCIA on 2014-11-24.
 */

import HospitalApplication.database.model.Dawca;
import HospitalApplication.database.service.DawcaService;
import HospitalApplication.email.model.Email;
import HospitalApplication.email.service.EmailService;
import HospitalApplication.excel.service.ExcelService;
import HospitalApplication.icd10database.operations.Operations;
import HospitalApplication.layout.DawcyOkienko;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
import java.io.InputStream;
import java.io.FileInputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
*/
public class App {

    public static void main(String [] args) throws IOException {
        System.out.println("load context");
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigure.class);
        Email email = (Email) context.getBean("email");

        EmailService emService = (EmailService) context.getBean("emailService");
        DawcaService daService = (DawcaService) context.getBean("dawcaService");
        ExcelService exService = (ExcelService) context.getBean("excelService");

        if(emService.checkEmail(email) == true){
            ArrayList<String> paths = emService.downloadExcelFile();
            String pesel = null;
            for(String path : paths){                 exService.setPath(path);
                 Dawca da = daService.findDawcaByPesel(exService.getPesel());
                if(da != null){
                    da.setDateUpdate(exService.getDate());
                    da.setHowLongOiom();
                    da.setRozpoznanie(exService.getRozpoznanie());
                    da.setObserwacje(exService.getObserwacje());
                    da.setWywiad(exService.getWywiad());
                    new File(exService.getPath()).delete();
                    daService.updateDawca(da);
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
                    daService.persistDawca(da);
                }
            }
        }
        else {
            System.out.println("nothing new");
            List<Dawca> dawcyDateUpdate = daService.list();
            for(Dawca dawca : dawcyDateUpdate){
                dawca.setHowLongOiom();
                daService.updateDawca(dawca);
            }
        }

        List<Dawca> dawcy = daService.list();
        /*for(Dawca dawca : dawcy){
            System.out.println("#############################################");
            System.out.println(dawca.getPesel());
            System.out.println(dawca.getDate());
            System.out.println(dawca.getDateUpdate());
            System.out.println(dawca.getHowLongOiom());
            System.out.println(dawca.getAge());
            System.out.println(dawca.getSex());
            System.out.println("Obserwacje: "+dawca.getObserwacje());
            System.out.println("Wywiad: "+dawca.getWywiad());
            System.out.println("Rozpoznanie: "+dawca.getRozpoznanie());
        }*/

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println(ex);
        }
        DawcyOkienko okienko = new DawcyOkienko(dawcy, Operations.generateIcd10Dictionary(),daService);
        }
    }