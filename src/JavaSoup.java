import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JavaSoup extends JFrame implements ActionListener {

    private Container contentPane = this.getContentPane();      //content pane of jframe

    private JButton convertButton = new JButton("Convert");     //button onclick convert currency

    private String [] selection = {"EUR", "GBP", "USD", "CNY", "JPY"};  //array of currencies to select from

    private JComboBox<String> cb1 = new JComboBox<>(selection);     //combo box for currency1
    private JComboBox<String> cb2 = new JComboBox<>(selection);     //combo box for currency2

    private JTextField f1 = new JTextField();       //field for the currency, for entry
    private JTextField f2 = new JTextField();       //field for the currency, for output

    public JavaSoup()
    {
        this.setSize(400, 400);     //set size of window
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);   //remove constraits on close
        this.setTitle("Currency Converter Convert");        //set title

    }

    public void init()
    {
        cb2.setSelectedIndex(1);        //init dropdown to the second value in array
        f2.setEditable(false);          //set entrypane2 to not be editable (its used for output)

        convertButton.addActionListener(this);  //add action listener to convertbutton

        contentPane.setLayout(new GridBagLayout());     //add gridbaglayout

        GridBagConstraints c = new GridBagConstraints();    //set c to gridbagconstraints

        c.insets = new Insets(0,5,0,5);     //init insets of gridbagconstraints

        c.gridx = 0;    //set grid x/y of gridbagconstaints
        c.gridy = 0;

        contentPane.add(cb1, c);        //add dropdown1 and gridbagconstraints to content pane

        c.gridx = 1;        //set gridbagconstraints as 1 for x

        contentPane.add(cb2, c);    //add dropdown2 and gridbagconstraints to content pane
                                    //we did the same for cb1, this needs to happen so we can apply gridbagconstraints to the dropdowns

        c.fill = GridBagConstraints.HORIZONTAL;     //fill gbc horizontally
        c.gridx = 0;
        c.gridy = 1;            // set y to 1

        contentPane.add(f1, c);     //add entry1 to content pane with gbc

        c.gridx = 1;            //gbc x = 1

        contentPane.add(f2, c);     //add entry 2 to content pane with gbc

        c.gridx = 0;
        c.gridy = 2;            //set grid
        c.gridwidth = 2;

        contentPane.add(convertButton, c);      //add convert button to content pane with gbc


        this.setVisible(true);          //set to be visible
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double amount = Double.parseDouble(f1.getText());       //parse entry1
            String from = (String)cb1.getSelectedItem();    //from and to string
            String to = (String)cb2.getSelectedItem();

            convert(amount, from, to);      //convert amount(gotten from entry1), with from (dropdown1s currency) and to(dropdown2 currency)

            System.out.println("You Entered: " +amount+ " Converting from " + from + " To " +to );
        }
        catch (Exception ex)        //exceptionex
        {
            System.out.println("expecting double try again");       //print entry if user input is not a double
        }

    }

    public void convert(double amount, String fromValue, String toValue)        //variables are amt, fromvalue and tovalue
    {
        try {

            //doc is connected to jsoup, jsoup is connected to xe.com for the realtime currency convert values
            Document doc = Jsoup.connect(String.format("https://www.xe.com/currencyconverter/convert/?Amount=%f&From=%s&To=%s", amount, fromValue, toValue)).get();

            //doc select p
            Elements elements = doc.select("p");
            for (Element element : elements) {          //for loop, for elements
                String classes = element.className();   //classes are element.classname
                if(classes.contains("result__BigRate")) //if this class contains result__bigrate
                {
                    //set entry2 to be element substring0, add string "." +3, replace "," with empty string
                    f2.setText(element.text().substring(0,element.text().indexOf(".")+3).replaceAll(",", ""));
                }

            }

        }
        catch(Exception e)      //exception e
        {
            e.printStackTrace();        //print stacktrace
        }
    }

    public static void main(String[] args) {
        new JavaSoup().init();   ///main method, run javasoup gui
    }
}