package dyds.dictionary.alpha.fulllogic;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainWindow {
  private JTextField textField1;
  private JButton goButton;
  private JPanel contentPane;
  private JTextPane textPane1;

  public MainWindow() {

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://en.wikipedia.org/w/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build();

    WikipediaAPI wikiAPI = retrofit.create(WikipediaAPI.class);

    textPane1.setContentType("text/html");

    goButton.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {

        new Thread(new Runnable() {
          @Override public void run() {

            String text = DataBase.getMeaning(textField1.getText());


            if (text != null) { // exists in db

              text = "[*]" + text;
            } else { // get from service
              Response<String> callResponse;
              try {
                callResponse = wikiAPI.getTerm(textField1.getText()).execute();

                System.out.println("JSON " + callResponse.body());

                Gson gson = new Gson();
                JsonObject jobj = gson.fromJson(callResponse.body(), JsonObject.class);
                JsonObject query = jobj.get("query").getAsJsonObject();
                JsonObject pages = query.get("pages").getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
                Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
                JsonObject page = first.getValue().getAsJsonObject();
                JsonElement extract = page.get("extract");

                if (extract == null) {
                  text = "No Results";
                } else {
                  text = extract.getAsString().replace("\\n", "\n");
                  text = textToHtml(text, textField1.getText());

                  // save to DB  <o/

                  DataBase.saveTerm(textField1.getText(), text);
                }

              } catch (IOException e1) {
                e1.printStackTrace();
              }
            }

            textPane1.setText(text);
          }
        }).start();

      }
    });

  }

  public static void main(String[] args) {

    JFrame frame = new JFrame("Online Dictionary");
    frame.setContentPane(new MainWindow().contentPane);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);

    DataBase.createNewDatabase();
    DataBase.saveTerm("test", "sarasa");


    System.out.println(DataBase.getMeaning("test"));
    System.out.println(DataBase.getMeaning("nada"));
  }

  public static String textToHtml(String text, String term) {

    StringBuilder builder = new StringBuilder();

    builder.append("<font face=\"arial\">");

    String textWithBold = text
        .replace("'", "`")
        .replaceAll("(?i)" + term, "<b>" + term +"</b>");

    builder.append(textWithBold);

    builder.append("</font>");

    return builder.toString();
  }

}
