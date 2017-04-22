

/**
 * Created by Kiran on 03-04-2017.
 */
package utilities;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.AbstractList;
        import java.util.ArrayList;
        import java.util.Arrays;

        import net.sf.json.JSONArray;
        import net.sf.json.JSONObject;

/**
 *
 * @author 1691620
 */
public class database {

    private static Connection conn;
    private static String[] syntaxTexts = {"/a/","/c/"};
    public static Connection connect(String dbURL, String username, String password) {
        try {
            //step1 load the driver class
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, username, password);
            //conn = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE");
            if (conn != null) {
                System.out.println("Database Connected...");
            } else {
                System.out.println("Error in database connection...");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return conn;

    }

    public static void closeConnection() throws SQLException {
        conn.close();
    }

    public static ResultSet runQuery(String sqlQuery, ArrayList<String> params) {
        PreparedStatement stmt;
        ResultSet rs=null;
        String query = sqlQuery;
        try {
            stmt = conn.prepareStatement(query);
            int k=1;
            for(int i=0;i<params.size();i++){
                stmt.setString(k,params.get(i));
                k++;
            }
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);

        }
        return rs;
    }
    public static String createResponse(String format,ResultSet data) throws SQLException{
        String json=null;
        ResultSet duplicateData = data; // duplicating the resultSet
        JSONObject jo = new JSONObject(); // the main JSONObject declared in global scope of the function
        format = format.substring(1,format.length()-1); //stripping off the initial JSONObject from the fomat specification
        ArrayList<String> mainObjectKeys = new ArrayList<>((Arrays.asList(format.split(",")))); // Splitting the format specification to obtain all the main object keys
        int k=0; // counter var for the global data key's in JSON
        while(duplicateData.next()){
            String key = mainObjectKeys.get(k);
            System.out.print(isSyntax(key));
            if(!isSyntax(key)) {
                String value = duplicateData.getString(key);
                k++;
                jo.accumulate(key, value);
            }
        }
        for(int j=0;j<mainObjectKeys.size();j++) {
            if(mainObjectKeys.get(j).indexOf("/c/")>-1){
                String constantString = mainObjectKeys.get(j).substring(mainObjectKeys.get(j).indexOf("/c/")+3);
                String[] keyValue = constantString.split(":");
                jo.accumulate(keyValue[0],keyValue[1]);
            }
            if(mainObjectKeys.get(j).indexOf("/a/")>-1) {
                JSONArray ja = new JSONArray();
                for (int i = 0; i < mainObjectKeys.size(); i++) {
                    String arrayFormat = format.substring(format.indexOf("[{") + 2, format.indexOf("}]"));
                    ArrayList<String> arrayKeys = new ArrayList<>((Arrays.asList(arrayFormat.split("&"))));

                    System.out.println("Inside");
                    JSONObject subJo = new JSONObject();
                    duplicateData.beforeFirst(); //Resetting the next() counter on duplicateData result set;
                    while(duplicateData.next()){
                        for(int l=0;l<arrayKeys.size();l++) {
                            String key = arrayKeys.get(l);
                            String value = duplicateData.getString(key);
                            subJo.accumulate(key, value);
                        }
                        ja.add(subJo);
                        subJo.clear();
                    }
                }
                String key = mainObjectKeys.get(j).substring(mainObjectKeys.indexOf("/a/")+4,mainObjectKeys.get(j).indexOf("[{"));
                jo.accumulate(key,ja);
            }
        }
        json = jo.toString();
        return json;
    }
    static boolean isSyntax(String veryHugeString){
        boolean foundAtLeastOne = false;
        for (String word : syntaxTexts) {
            if (veryHugeString.indexOf(word) > -1) {
                foundAtLeastOne = true;
                break;
            }
        }
        return foundAtLeastOne;
    }
}