package HospitalApplication.layout;

import HospitalApplication.database.model.Dawca;
import HospitalApplication.database.service.DawcaCheckEmail;
import HospitalApplication.database.service.DawcaReload;
import HospitalApplication.database.service.DawcaService;
import HospitalApplication.database.service.SpringConfigure;
import HospitalApplication.icd10database.operations.AutoSuggestor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
/*
public class DawcyOkienko extends JFrame {


}
*/


public class DawcyOkienko extends JFrame{
    final private  Map<String, String> words;
    List<Dawca> dawcy2;
    DawcaService daService;
    Timer timer;
    JTable table;
    ListSelectionListener lSelectionListener;

    public DawcyOkienko(List<Dawca> dawcy,final Map<String, String> words, final DawcaService daService){

        this.words = words;
        dawcy2 = dawcy;
        this.daService = daService;

        /**********************************************************************/
        /*ROZMIAR OKNA*/
        int windowWidth = 800;
        int windowHeight = 600;
        /**********************************************************************/
        /*ROZMIARY TABELKI*/
        final String[] columnNames = {"PESEL", 
                                "WIEK", 
                                "PŁEĆ",
                                "DATA PRZYJECIA NA OIOM",
                                "ILOSC DNI NA OIOM",
                                "OSTANIA AKTUALIZACJA",
                                "ROZPOZNANIE ICD10",
                                "ROZPOZNANIE ICD10",
                                "ROZPOZNANIE ICD10",
                                "ROZPOZNANIE ICD10",
                                "SKALA GSC",
                                "KATEGORIA PACJENTA",
                                "UWAGI"};
        final int columns = columnNames.length;
        final int rows = dawcy.size();
        /**********************************************************************/
        /*MODEL TABELKI*/
        /*wyrzucilem to
        * model tabelki ustawiam przez set.table*/

       /**********************************************************************/
        /*INICJALIZACJA TABELKI*/
        table = new JTable();
        //= new JTable(dataModel);
       // table.getModel().addTableModelListener(new MyTableModelListener(table));


        dawcy2.sort(new Comparator<Dawca>(){
            @Override
            public int compare(Dawca o1, Dawca o2) {
                return o2.getUpdateDate().compareTo(o1.getUpdateDate());
            }
        });

        table.setModel(new DefaultTableModel() {
            @Override
            public int getRowCount() {
                return rows;
            }

            @Override
            public int getColumnCount() {
                return columns;
            }

            @Override //ustawia zawartosc tablicy
            public Object getValueAt(int rowIndex, int columnIndex) {
                Dawca dawca = dawcy2.get(rowIndex);
                switch (columnIndex) {
                    case 0:
                        return dawca.getPesel();
                    case 1:
                        return dawca.getAge();
                    case 2:
                        return dawca.getSex();
                    case 3:
                        return dawca.getFirsDate();
                    case 4:
                        return dawca.getHowLongOiom();
                    case 5:
                        return dawca.getDateUpdate().get(dawca.getDateUpdate().size() - 1);
                    case 6:
                        return dawca.getIcd10_1();
                    case 7:
                        return dawca.getIcd10_2();
                    case 8:
                        return dawca.getIcd10_3();
                    case 9:
                        return dawca.getIcd10_4();
                    default:
                        return null;
                }
            }


            @Override
            public void setValueAt(Object object, int rowIndex, int columnIndex) {

            }

            @Override
            public String getColumnName(int index) {
                return columnNames[index];
            }

            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }

        });


        lSelectionListener = new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
                if(event.getValueIsAdjusting()){ //zeby dzialalo tylko na klikniecie (na puszczenie myszki nie)
                    final Dawca dawca = dawcy2.get(table.getSelectedRow());
                    final JFrame popup = new JFrame("popup");
                    popup.setSize(new Dimension(400, 720));
                    popup.setAlwaysOnTop(false);
                    popup.setTitle(dawca.getPesel());
                    popup.setResizable(false);

                    JPanel panel = new JPanel();
                    panel.setLayout(null);

                    JToolBar toolBar = new JToolBar();
                    toolBar.setFloatable(false);

                    JLabel szukajkaLabel = new JLabel("ICD10: ");
                    JFrame szukajkaFrame = new JFrame();
                    final JTextField szukajka = new JTextField();
        /*OBSŁUGA DLA SZUKAJKI ICD10*/
                    AutoSuggestor autoSuggestor = new AutoSuggestor(szukajka, szukajkaFrame, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) {
                        @Override
                        protected boolean wordTyped(String typedWord) {

                            setDictionary(words);

                            return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
                        }
                    };

                    szukajka.setPreferredSize(new Dimension(120, 20));
                    szukajka.setMaximumSize(szukajka.getPreferredSize());

                    JLabel szukajkaLabel2 = new JLabel("ICD10: ");
                    JFrame szukajkaFrame2 = new JFrame();
                    final JTextField szukajka2 = new JTextField();
        /*OBSŁUGA DLA SZUKAJKI ICD10*/
                    AutoSuggestor autoSuggestor2 = new AutoSuggestor(szukajka2, szukajkaFrame2, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) {
                        @Override
                        protected boolean wordTyped(String typedWord) {

                            setDictionary(words);

                            return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
                        }
                    };

                    szukajka2.setPreferredSize(new Dimension(120, 20));
                    szukajka2.setMaximumSize(szukajka2.getPreferredSize());


                    JLabel szukajkaLabel3 = new JLabel("ICD10: ");
                    JFrame szukajkaFrame3 = new JFrame();
                    final JTextField szukajka3 = new JTextField();
        /*OBSŁUGA DLA SZUKAJKI ICD10*/
                    AutoSuggestor autoSuggestor3 = new AutoSuggestor(szukajka3, szukajkaFrame3, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) {
                        @Override
                        protected boolean wordTyped(String typedWord) {

                            setDictionary(words);

                            return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
                        }
                    };

                    szukajka3.setPreferredSize(new Dimension(120, 20));
                    szukajka3.setMaximumSize(szukajka3.getPreferredSize());


                    JLabel szukajkaLabel4 = new JLabel("ICD10: ");
                    JFrame szukajkaFrame4 = new JFrame();
                    final JTextField szukajka4 = new JTextField();
        /*OBSŁUGA DLA SZUKAJKI ICD10*/
                    AutoSuggestor autoSuggestor4 = new AutoSuggestor(szukajka4, szukajkaFrame3, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) {
                        @Override
                        protected boolean wordTyped(String typedWord) {

                            setDictionary(words);

                            return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
                        }
                    };


                    szukajka4.setPreferredSize(new Dimension(120, 20));
                    szukajka4.setMaximumSize(szukajka4.getPreferredSize());


                    szukajkaLabel.setBounds(20, 10, 40, 20);
                    panel.add(szukajkaLabel);
                    szukajka.setBounds(70, 10, 120, 20);
                    panel.add(szukajka);
                    szukajkaLabel2.setBounds(20, 40, 40, 20);
                    panel.add(szukajkaLabel2);
                    szukajka2.setBounds(70, 40, 120, 20);
                    panel.add(szukajka2);
                    szukajkaLabel3.setBounds(20, 70, 40, 20);
                    panel.add(szukajkaLabel3);
                    szukajka3.setBounds(70, 70, 120, 20);
                    panel.add(szukajka3);
                    szukajkaLabel4.setBounds(20, 100, 40, 20);
                    panel.add(szukajkaLabel4);
                    szukajka4.setBounds(70, 100, 120, 20);
                    panel.add(szukajka4);

                    JTextArea tInfo = new JTextArea();
                    tInfo.setLineWrap(true);
                    tInfo.setWrapStyleWord(true);
                    tInfo.setEditable(false);
                    tInfo.setFont(tInfo.getFont().deriveFont(11f));

                    JScrollPane scrollPaneInfo = new JScrollPane(tInfo);
                    scrollPaneInfo.setBounds(20,140,350,530);

                    JButton zapisz = new JButton("Zapisz");
                    zapisz.setBounds(200,10,100,25);
                    zapisz.addActionListener(new ActionListener(){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigure.class);
                            DawcaService daService = (DawcaService) context.getBean("dawcaService");


                            String icd10_1 = szukajka.getText().toString();
                            if(szukajka.getText().toString().equals("")){

                            }else{
                                dawca.setIcd10_1(icd10_1);
                            }

                            String icd10_2 = szukajka2.getText().toString();
                            if(szukajka2.getText().toString().equals("")){

                            }else{
                                dawca.setIcd10_2(icd10_2);
                            }

                            String icd10_3 = szukajka3.getText().toString();
                            if(szukajka3.getText().toString().equals("")){

                            }else{
                                dawca.setIcd10_3(icd10_3);
                            }

                            String icd10_4 = szukajka4.getText().toString();
                            if(szukajka4.getText().toString().equals("")){

                            }else{
                                dawca.setIcd10_4(icd10_4);
                            }

                            daService.updateDawca(dawca);

                            table.getSelectionModel();
                            popup.dispose();
                        }
                    });
                    panel.add(zapisz);

                    panel.add(scrollPaneInfo);

                    popup.add(panel);
                    //popup.add(toolBar,BorderLayout.NORTH);

                    for(int i = 0 ; i < dawca.getDateUpdate().size() ; i++) {

                        String dateUpdate = dawca.getDateUpdate().get(i);
                        tInfo.append(dateUpdate+"\n");

                        if(dawca.getWywiad().size() > i)
                            tInfo.append("Wywiad\n" + dawca.getWywiad().get(i)+"\n\n");

                        if(dawca.getRozpoznanie().size() > i)
                            tInfo.append("Rozpoznanie\n" + dawca.getRozpoznanie().get(i)+"\n\n");

                        if(dawca.getObserwacje().size() > i)
                            tInfo.append("Obserwacje\n" + dawca.getObserwacje().get(i)+"\n\n");
                    }
                    popup.setAlwaysOnTop(true);
                    popup.setVisible(true);
                }

            }
        };

        table.getSelectionModel().addListSelectionListener(lSelectionListener);


        table.setValueAt("", 0, 0);

        /*table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {

                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    System.out.println(target.getModel().getValueAt(row,column));
                }
            }
        });*/

        JScrollPane scrollpane = new JScrollPane(table);
        /**********************************************************************/
        /*MENUBAR*/
        JMenuBar menuBar = new JMenuBar();
        JMenu menuMenu = new JMenu("Menu");

        /***REALOAD DATABASE***/
        JMenuItem menuItem = new JMenuItem("Reload Database");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dawcy2 = DawcaReload.reloadDatabase();

                final int rows2 = dawcy2.size();

                table.setModel(new DefaultTableModel() {
                    @Override
                    public int getRowCount() {
                        return rows2;
                    }

                    @Override
                    public int getColumnCount() {
                        return columns;
                    }

                    @Override //ustawia zawartosc tablicy
                    public Object getValueAt(int rowIndex, int columnIndex) {

                        /***WRZUCENIE DANYCH DO JTABLE TAK SAMO JAK ZA PIERWSZYM USTAWIENIEM DO MODELU WYŻEJ***/
                        Dawca dawca = dawcy2.get(rowIndex);
                        switch (columnIndex) {
                            case 0:
                                return dawca.getPesel();
                            case 1:
                                return dawca.getAge();
                            case 2:
                                return dawca.getSex();
                            case 3:
                                return dawca.getFirsDate();
                            case 4:
                                return dawca.getHowLongOiom();
                            case 5:
                                return dawca.getDateUpdate().get(dawca.getDateUpdate().size() - 1);
                            case 6:
                                return dawca.getIcd10_1();
                            case 7:
                                return dawca.getIcd10_2();
                            case 8:
                                return dawca.getIcd10_3();
                            case 9:
                                return dawca.getIcd10_4();
                            default:
                                return null;
                        }
                    }

                    @Override
                    public String getColumnName(int index) {
                        return columnNames[index];
                    }

                    @Override
                    public boolean isCellEditable(int row, int column){
                        return false;
                    }
                });
            }
        });
        menuMenu.add(menuItem);
        JMenu menuUstawienia = new JMenu("Ustawienia");
        menuBar.add(menuMenu);
        menuBar.add(menuUstawienia);
        this.setJMenuBar(menuBar);
        /**********************************************************************/
        /*TOOLBAR + BUTTONY*/
        //ramka na buttony
        Border buttonBorder = new LineBorder(Color.GRAY, 1);
        //toolbar
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        JButton testBtn1 = new JButton();
        //try {
        //    Image img = ImageIO.read(getClass().getResource("settings_icon.png"));
        //    testBtn1.setIcon(new ImageIcon(img));
            testBtn1.setBorder(buttonBorder);
       // } catch (IOException ex) {
        //    System.out.println(ex);
        //}
        
        JLabel szukajkaLabel = new JLabel("ICD10: ");
        JFrame szukajkaFrame = new JFrame();
        JTextField szukajka = new JTextField();
        /*OBSŁUGA DLA SZUKAJKI ICD10*/
        AutoSuggestor autoSuggestor = new AutoSuggestor(szukajka, szukajkaFrame, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) {
            @Override
            protected boolean wordTyped(String typedWord) {

                setDictionary(words);

                return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
            }
        };

        szukajka.setPreferredSize(new Dimension(120, 20));
        szukajka.setMaximumSize(szukajka.getPreferredSize());
        
        toolBar.add(testBtn1);
        toolBar.add(Box.createHorizontalGlue());
        //toolBar.add(szukajkaLabel);
        //toolBar.add(szukajka);
        /**********************************************************************/
        this.add(toolBar, BorderLayout.NORTH);
        this.add(scrollpane);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(windowWidth, windowHeight);
	    this.setVisible(true);

        timer = new Timer(30000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.getSelectionModel().removeListSelectionListener(lSelectionListener);
                dawcy2 = DawcaCheckEmail.checkEmail();

                dawcy2.sort(new Comparator<Dawca>(){
                    @Override
                    public int compare(Dawca o1, Dawca o2) {
                        return o2.getUpdateDate().compareTo(o1.getUpdateDate());
                    }
                });



                String timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                System.out.println(timeStamp);

                final int rows2 = dawcy2.size();
                table.setModel(new DefaultTableModel() {
                    @Override
                    public int getRowCount() { return rows2;}

                    @Override
                    public int getColumnCount() {
                        return columns;
                    }

                    @Override //ustawia zawartosc tablicy
                    public Object getValueAt(int rowIndex, int columnIndex) {

                        /***WRZUCENIE DANYCH DO JTABLE TAK SAMO JAK ZA PIERWSZYM USTAWIENIEM DO MODELU WYŻEJ***/
                        Dawca dawca = dawcy2.get(rowIndex);
                        switch (columnIndex) {
                            case 0:
                                return dawca.getPesel();
                            case 1:
                                return dawca.getAge();
                            case 2:
                                return dawca.getSex();
                            case 3:
                                return dawca.getFirsDate();
                            case 4:
                                return dawca.getHowLongOiom();
                            case 5:
                                return dawca.getDateUpdate().get(dawca.getDateUpdate().size() - 1);
                            case 6:
                                return dawca.getIcd10_1();
                            case 7:
                                return dawca.getIcd10_2();
                            case 8:
                                return dawca.getIcd10_3();
                            case 9:
                                return dawca.getIcd10_4();
                            default:
                                return null;
                        }
                    }
                    @Override
                    public String getColumnName(int index) {
                        return columnNames[index];
                    }

                    @Override
                    public boolean isCellEditable(int row, int column){
                        return false;
                    }
                });
                table.getSelectionModel().addListSelectionListener(lSelectionListener);
            }
        });
     timer.start();
    }

    public void setSelectionListener(){
        table.getSelectionModel().addListSelectionListener(lSelectionListener);
    }


}
