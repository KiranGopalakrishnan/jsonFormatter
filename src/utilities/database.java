

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
     long timestamp = System.currentTimeMillis() / 1000L;
    public static boolean connect(String dbURL, String username, String password) {
        boolean response=false;
        try {
            //step1 load the driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(dbURL, username, password);
            //conn = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE");
            if (conn != null) {
                response = true;
            } else {
                response = false;
            }
        } 
        catch (ClassNotFoundException | SQLException e) {
          
           return response;
        }
        return response;

    }

    public static void closeConnection() throws SQLException {
        conn.close();
    }

    public static ResultSet runQuery(String sqlQuery, ArrayList<String> params) {
        PreparedStatement stmt;
        ResultSet rs=null;
        String query = sqlQuery;
        try {
            stmt = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
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
        duplicateData.next();
            //System.out.print(isSyntax(key));
            for(int m=0;m<mainObjectKeys.size();m++){
            String key = mainObjectKeys.get(m);
            if(!isSyntax(key)) {
                String value = duplicateData.getString(key);
                jo.accumulate(key, value);
            }
            }
        duplicateData.beforeFirst();
        for(int j=0;j<mainObjectKeys.size();j++) {
            if(mainObjectKeys.get(j).indexOf("/c/")>-1){
                String constantString = mainObjectKeys.get(j).substring(mainObjectKeys.get(j).indexOf("/c/")+3);
                String[] keyValue = constantString.split(":");
                jo.accumulate(keyValue[0],keyValue[1]);
            }
            if(mainObjectKeys.get(j).indexOf("/a/")>-1) {
                JSONArray ja = new JSONArray();
                    String arrayFormat = format.substring(format.indexOf("[{") + 2, format.indexOf("}]"));
                    ArrayList<String> arrayKeys = new ArrayList<>((Arrays.asList(arrayFormat.split("&"))));
                    JSONObject subJo = new JSONObject();
                    duplicateData.beforeFirst(); //Resetting the next() counter on duplicateData result set;
                    //System.out.println(arrayKeys.size());
                    while(duplicateData.next()){
                        for(int l=0;l<arrayKeys.size();l++) {
                            String key = arrayKeys.get(l);
                            String value = duplicateData.getString(key);
                            subJo.accumulate(key, value);
                        }
                        ja.add(subJo);
                        subJo.clear();
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
    public String JsonWrong()
    {
    JSONObject wrong=new JSONObject();
    wrong.accumulate("status", "Wrong");
    wrong.accumulate("timestamp", timestamp);
    wrong.accumulate("Message", "No Results found");
    
    return wrong.toString();
    }
     public String JsonError()
    {
    JSONObject error=new JSONObject();
    error.accumulate("status", "Error");
    error.accumulate("timestamp", timestamp);
    error.accumulate("Message", "Database Connection Error");
    return error.toString();
    }
}
