package HospitalApplication.database.model;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by GUCIA on 2014-11-24.
 */

@Entity
@Table(name = "DAWCA")
public class Dawca implements Serializable {
    //@Id
    //@Column(name = "ID", nullable = true)
    //private int id;

    @Id
    @Column(name = "PESEL", nullable = true)
    private String pesel;

    @Column(name = "WIEK", nullable = true)
    private long age;

    @Column(name = "PLEC", nullable = true)
    private String sex;

    @Column(name = "DATA_PRZYJECIA_NA_OIOM", nullable = true)
    private Date date;

    @Column(name = "DATA_AKTUALIZACJA", nullable = true)
    private  ArrayList<String> dateUpdate = new ArrayList<String>();

    @Column(name = "ILE_NA_OIOMIE", nullable = true)
    private long howLongOiom;

    @Column(name = "ICD10_1", nullable = true)
    private String icd10_1;

    @Column(name = "ICD10_2", nullable = true)
    private String icd10_2;

    @Column(name = "ICD10_3", nullable = true)
    private String icd10_3;

    @Column(name = "ICD10_4", nullable = true)
    private String icd10_4;

    @Column(name = "GCS", nullable = true)
    private ArrayList<Integer> gcs = new ArrayList<Integer>();

    @Column(name = "KATEGORIA_DAWCY", nullable = true)
    private String patientCategory;

    @Column(name = "UWAGI", nullable = true)
    private String uwagi;

    @Column(name = "rozpoznanie", nullable = true)
    private ArrayList<String> rozpoznanie = new ArrayList<String>();

    @Column(name = "wywiad", nullable = true)
    private ArrayList<String> wywiad = new ArrayList<String>();

    @Column(name = "obserwacje", nullable = true)
    private ArrayList<String> obserwacje = new ArrayList<String>();

    public Dawca() {

    }

    @ElementCollection
    @CollectionTable(name = "Obserwacje", joinColumns = @JoinColumn(name = "ID"))
    @Column(name = "obserwacje")
    public ArrayList<String> getObserwacje() {
        return obserwacje;
    }

    public void setObserwacje(ArrayList<String> obserwacje) {
        for (String obserwacja : obserwacje)
            this.obserwacje.add(obserwacja);
    }

    @ElementCollection
    @CollectionTable(name = "Rozpoznanie", joinColumns = @JoinColumn(name = "ID"))
    @Column(name = "rozpoznanie")
    public ArrayList<String> getRozpoznanie() {
        return rozpoznanie;
    }

    public void setRozpoznanie(ArrayList<String> rozpoznania) {
        for (String rozpoznanie : rozpoznania)
            this.rozpoznanie.add(rozpoznanie);
    }

    @ElementCollection
    @CollectionTable(name = "Wywiady", joinColumns = @JoinColumn(name = "ID"))
    @Column(name = "wywiad")
    public ArrayList<String> getWywiad() {
        return wywiad;
    }

    public void setWywiad(ArrayList<String> wywiady) {
        for (String wywiad : wywiady)
            this.wywiad.add(wywiad);
    }

    //public int getId() {
    //   return id;
    //}

    //public void setId(int id) {
    //   this.id = id;
    //}

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public long getAge() {
        return age;
    }

    public void setAge() {
        int yearCalendar = Calendar.getInstance().get(Calendar.YEAR);
        int yearPesel;
        int month;
        yearPesel = 10 * Integer.parseInt(pesel.substring(0, 1));
        yearPesel += Integer.parseInt(pesel.substring(1, 2));
        month = 10 * Integer.parseInt(pesel.substring(2, 3));
        month += Integer.parseInt(pesel.substring(3, 4));
        if (month > 80 && month < 93) {
            yearPesel += 1800;
        } else if (month > 0 && month < 13) {
            yearPesel += 1900;
        } else if (month > 20 && month < 33) {
            yearPesel += 2000;
        } else if (month > 40 && month < 53) {
            yearPesel += 2100;
        } else if (month > 60 && month < 73) {
            yearPesel += 2200;
        }

        age = yearCalendar - yearPesel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex() {
        if (Integer.parseInt(pesel.substring(10, 11)) % 2 == 1) {
            sex = "M";
        } else {
            sex = "K";
        }
    }

    public long getHowLongOiom() {
        return howLongOiom;
    }

    public void setHowLongOiom(){
        Date oldDate = this.getDate();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date currentDate = new Date();
        this.howLongOiom = TimeUnit.DAYS.convert((currentDate.getTime() - oldDate.getTime()), TimeUnit.MILLISECONDS);
    }

    public String getIcd10_1() {
        return icd10_1;
    }

    public void setIcd10_1(String icd10_1) {
        this.icd10_1 = icd10_1;
    }

    public String getIcd10_2() {
        return icd10_2;
    }

    public void setIcd10_2(String icd10_2) {
        this.icd10_2 = icd10_2;
    }

    public String getIcd10_3() {
        return icd10_3;
    }

    public void setIcd10_3(String icd10_3) {
        this.icd10_3 = icd10_3;
    }

    public String getIcd10_4() {
        return icd10_4;
    }

    public void setIcd10_4(String icd10_4) {
        this.icd10_4 = icd10_4;
    }

    public String getPatientCategory() {
        return patientCategory;
    }

    public void setPatientCategory(String patientCategory) {
        this.patientCategory = patientCategory;
    }

    public String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }

    public Date getDate() {
        return date;
    }

    public String getFirsDate(){
        Date data = date;
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String firstDate = df.format(data);
        return firstDate;
    }

    public void setDate(String date) {
        try {
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
            this.date = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @ElementCollection
    @CollectionTable(name = "DATA_AKTUALIZACJI", joinColumns = @JoinColumn(name = "ID"))
    @Column(name = "dateUpdate")
    public ArrayList<String> getDateUpdate() {
        return dateUpdate;
    }

    public Date getUpdateDate(){
        try {
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
            Date updateDate = format.parse(dateUpdate.get(dateUpdate.size()-1));
            return updateDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setDateUpdate(String dateUpdate) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        this.dateUpdate.add(dateUpdate);
    }

    @ElementCollection
    @CollectionTable(name = "GCS", joinColumns = @JoinColumn(name = "ID"))
    @Column(name = "gcs")
    public ArrayList<Integer> getGcs() {
        return gcs;
    }

    public void setGcs(int gcs) {
        this.gcs.add(gcs);
    }
}
