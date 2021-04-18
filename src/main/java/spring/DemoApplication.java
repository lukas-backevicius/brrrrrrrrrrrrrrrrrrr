package spring;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootApplication
@RestController
public class DemoApplication {

    private static Connection con;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        connectDB();
    }

    public static void connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/java", "root", "");
            System.out.println("Connected to database!");
        } catch (Exception e) {
            System.out.println("Couldn't connect to database!");
        }
    }

    @RestController
    public static class employee {
        @RequestMapping(value = "/employee", method = RequestMethod.GET)
        public List<Map<String, Object>> getEmployees(@RequestParam(value = "id", defaultValue = "def") String id) {
            try {
                String query = "select * from employee ";
                PreparedStatement st = con.prepareStatement(query);
                if (!id.equals("def")) {
                    query = query + "where id = " + id;
                }
                ResultSet rs = st.executeQuery(query);
                List<Map<String, Object>> rows = new ArrayList<>();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String colName = rsmd.getColumnName(i);
                        Object colVal = rs.getObject(i);
                        row.put(colName, colVal);
                    }
                    rows.add(row);
                }
                return rows;
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }



        @RequestMapping(value = "/employee", method = RequestMethod.POST)
        public String postEmployee(@RequestBody String json) {
            try {
                JSONObject obj = new JSONObject(json);
            String resp;
            if (obj.has("id")) {

                    String query = "select * from employee where id = " + obj.getString("id");
                    PreparedStatement st = con.prepareStatement(query);
                    ResultSet rs = st.executeQuery();
                    boolean exists = rs.next();
                    if(!exists) {
                        String queryUpd = "insert into employee values (?, ?, ?, ?, ?, ?, ?)";
                        st = con.prepareStatement(queryUpd);
                        st.setString(1, obj.getString("id"));
                        st.setString(2, obj.getString("login"));
                        st.setString(3, obj.getString("pass"));
                        st.setString(4, obj.getString("fname"));
                        st.setString(5, obj.getString("lname"));
                        st.setString(6, obj.getString("phone"));
                        st.setString(7, obj.getString("email"));
                        st.executeUpdate();
                        return "Added!";
                    }
                    else
                    {
                        return "ID already exists";
                    }
            } else {
                resp = "ID Can't be null";
                return resp;
            }

            } catch (Exception e) {
                System.out.println(e);
            }
        return null;
        }

            @RequestMapping(value = "/employee", method = RequestMethod.PUT)
        public String editEmployees(@RequestBody String json) {
                try {
                    JSONObject obj = new JSONObject(json);
                    String resp;
                    if (obj.has("id")) {

                    String query = "select * from employee where id = " + obj.getString("id");
                    PreparedStatement st = con.prepareStatement(query);
                    ResultSet rs = st.executeQuery();
                    boolean exists = rs.next();
                    if(exists) {
                        String queryUpd = "update employee set login = ?, pass = ?, firstName = ?, lastName = ?, phone = ?, email = ? where id = ?";
                        st = con.prepareStatement(queryUpd);
                        st.setString(1, obj.getString("login"));
                        st.setString(2, obj.getString("pass"));
                        st.setString(3, obj.getString("fname"));
                        st.setString(4, obj.getString("lname"));
                        st.setString(5, obj.getString("phone"));
                        st.setString(6, obj.getString("email"));
                        st.setString(7, obj.getString("id"));
                        st.executeUpdate();
                    }
                    else
                    {
                        return "ID dosen't exist";
                    }

                resp = "Added!";

            } else {
                resp = "ID Can't be null";
            }
            return resp;
            } catch (Exception e) {
                    System.out.println("ERROR");
                }
            return null;
        }

        @RequestMapping(value = "/employee", method = RequestMethod.DELETE)
        public String deleteEmployees(@RequestParam(value = "id", defaultValue = "def") String id) {
            String resp;
            try {
                String queryUpd = "delete from employee where id = ?";
                PreparedStatement st = con.prepareStatement(queryUpd);
                st.setString(1, id);
                st.executeUpdate();
            } catch (Exception e) {
                System.out.println("ERROR");
            }
            resp = "Deleted!";

            return resp;
        }



    }



    @RestController
    public static class category {

        @GetMapping("/category")
        public List<Map<String, Object>> getCategory(@RequestParam(value = "id", defaultValue = "def") String id) {
            try {
                String query = "select * from category ";
                PreparedStatement st = con.prepareStatement(query);
                if (!id.equals("def")) {
                    query = query + "where id = " + id;
                }
                ResultSet rs = st.executeQuery(query);
                List<Map<String, Object>> rows = new ArrayList<>();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String colName = rsmd.getColumnName(i);
                        Object colVal = rs.getObject(i);
                        row.put(colName, colVal);
                    }
                    rows.add(row);
                }
                return rows;
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }

        @RequestMapping(value = "/category", method = RequestMethod.POST)
        public String postCategory(@RequestBody String json) {
            try {
                JSONObject obj = new JSONObject(json);
                String resp;
                if (obj.has("id")) {
                    String query = "select * from category where id = " + obj.getString("id");
                    PreparedStatement st = con.prepareStatement(query);
                    ResultSet rs = st.executeQuery();
                    boolean exists = rs.next();
                    if(!exists) {
                        String queryUpd = "insert into category values (?, ?, ?, ?, ?)";
                        st = con.prepareStatement(queryUpd);
                        st.setString(1, obj.getString("id"));
                        st.setString(2, obj.getString("rfname"));
                        st.setString(3, obj.getString("rlname"));
                        st.setString(4, obj.getString("name"));
                        st.setString(5, obj.getString("pid"));
                        st.executeUpdate();
                    }
                    else
                    {
                        return "ID already exists";
                    }
                resp = "Added!";
            } else {
                resp = "ID Can't be null";
            }
                return resp;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
        }

        @RequestMapping(value = "/category", method = RequestMethod.PUT)
        public String editCategory(@RequestBody String json) {
                try {
                    JSONObject obj = new JSONObject(json);
                    String resp;
                    if (obj.has("id")) {
                    String query = "select * from category where id = " + obj.getString("id");
                    PreparedStatement st = con.prepareStatement(query);
                    ResultSet rs = st.executeQuery();
                    boolean exists = rs.next();
                    if(exists) {
                        String queryUpd = "update category set responsiblePersonFirstName = ?, responsiblePersonLastName = ?, name = ?, parentCategoryId = ? where id = ?";
                        st = con.prepareStatement(queryUpd);
                        st.setString(1, obj.getString("rfname"));
                        st.setString(2, obj.getString("rlname"));
                        st.setString(3, obj.getString("name"));
                        st.setString(4, obj.getString("pid"));
                        st.setString(5, obj.getString("id"));
                        st.executeUpdate();
                    }
                    else
                    {
                        return "ID dosen't exist";
                    }
    
                resp = "Added!";
            } else {
                resp = "ID Can't be null";
            }
                    return resp;
                } catch (Exception e) {
                    System.out.println(e);
                }
            return null;
        }

        @RequestMapping(value = "/category", method = RequestMethod.DELETE)
        public String deleteCategory(@RequestParam(value = "id", defaultValue = "def") String id) {
            String resp;
            try {
                String queryUpd = "delete from category where id = ?";
                PreparedStatement st = con.prepareStatement(queryUpd);
                st.setString(1, id);
                st.executeUpdate();
            } catch (Exception e) {
                System.out.println("ERROR");
            }
            resp = "Deleted!";

            return resp;
        }


    }




    @RestController
    public static class company {
        @GetMapping("/company")
        public List<Map<String, Object>> getCompanies(@RequestParam(value = "id", defaultValue = "def") String id) {
            try {
                String query = "select * from company ";
                PreparedStatement st = con.prepareStatement(query);
                if (!id.equals("def")) {
                    query = query + "where id = " + id;
                }
                ResultSet rs = st.executeQuery(query);
                List<Map<String, Object>> rows = new ArrayList<>();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String colName = rsmd.getColumnName(i);
                        Object colVal = rs.getObject(i);
                        row.put(colName, colVal);
                    }
                    rows.add(row);
                }
                return rows;
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }

        @RequestMapping(value = "/company", method = RequestMethod.POST)
        public String postCompany(@RequestBody String json) {
            try {
                JSONObject obj = new JSONObject(json);
                String resp;
                if (obj.has("id")) {
                    String query = "select * from company where id = " + obj.getString("id");
                    PreparedStatement st = con.prepareStatement(query);
                    ResultSet rs = st.executeQuery();
                    boolean exists = rs.next();
                    if(!exists) {
                        String queryUpd = "insert into company values (?, ?, ?, ?, ?, ?, ?, ?)";
                        st = con.prepareStatement(queryUpd);
                        st.setString(1, obj.getString("id"));
                        st.setString(2, obj.getString("login"));
                        st.setString(3, obj.getString("pass"));
                        st.setString(4, obj.getString("name"));
                        st.setString(5, obj.getString("address"));
                        st.setString(6, obj.getString("phone"));
                        st.setString(7, obj.getString("lname"));
                        st.setString(8, obj.getString("email"));
                        st.executeUpdate();
                    }
                    else
                    {
                        return "ID already exists";
                    }

                resp = "Added!";
            } else {
                resp = "ID Can't be null";
            }
                return resp;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
        }

        @RequestMapping(value = "/company", method = RequestMethod.PUT)
        public String editCompany(@RequestBody String json) {
            try {
                JSONObject obj = new JSONObject(json);
                String resp;
                if (obj.has("id")) {
                    String query = "select * from company where id = " + obj.getString("id");
                    PreparedStatement st = con.prepareStatement(query);
                    ResultSet rs = st.executeQuery();
                    boolean exists = rs.next();
                    if(exists) {
                        String queryUpd = "update company set login = ?, pass = ?, name = ?, address = ?, phone = ?, lastName = ?, email = ? where id = ?";
                        st = con.prepareStatement(queryUpd);
                        st.setString(1, obj.getString("login"));
                        st.setString(2, obj.getString("pass"));
                        st.setString(3, obj.getString("name"));
                        st.setString(4, obj.getString("address"));
                        st.setString(5, obj.getString("phone"));
                        st.setString(6, obj.getString("lname"));
                        st.setString(7, obj.getString("email"));
                        st.setString(8, obj.getString("id"));
                        st.executeUpdate();
                    }
                    else
                    {
                        return "ID dosen't exist";
                    }
                resp = "Added!";
            } else {
                resp = "ID Can't be null";
            }
                return resp;
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }

        @RequestMapping(value = "/company", method = RequestMethod.DELETE)
        public String deleteCompany(@RequestParam(value = "id", defaultValue = "def") String id) {
            String resp;
            try {
                String queryUpd = "delete from company where id = ?";
                PreparedStatement st = con.prepareStatement(queryUpd);
                st.setString(1, id);
                st.executeUpdate();
            } catch (Exception e) {
                System.out.println("ERROR");
            }
            resp = "Deleted!";

            return resp;
        }

    }



    @RestController
    public static class expense {
        @GetMapping("/expense")
        public List<Map<String, Object>> getExpenses(@RequestParam(value = "id", defaultValue = "def") String id) {
            try {
                String query = "select * from expense ";
                PreparedStatement st = con.prepareStatement(query);
                if (!id.equals("def")) {
                    query = query + "where id = " + id;
                }
                ResultSet rs = st.executeQuery(query);
                List<Map<String, Object>> rows = new ArrayList<>();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String colName = rsmd.getColumnName(i);
                        Object colVal = rs.getObject(i);
                        row.put(colName, colVal);
                    }
                    rows.add(row);
                }
                return rows;
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }

        @RequestMapping(value = "/expense", method = RequestMethod.POST)
        public String postExpense(@RequestBody String json) {
            try {
                JSONObject obj = new JSONObject(json);
                String resp;
                if (obj.has("id")) {
                    String query = "select * from expense where id = " + obj.getString("id");
                    PreparedStatement st = con.prepareStatement(query);
                    ResultSet rs = st.executeQuery();
                    boolean exists = rs.next();
                    if(!exists) {
                        String queryUpd = "insert into expense values (?, ?, ?, ?)";
                        st = con.prepareStatement(queryUpd);
                        st.setString(1, obj.getString("id"));
                        st.setString(2, obj.getString("categoryID"));
                        st.setString(3, obj.getString("acq"));
                        st.setString(4, obj.getString("amount"));
                        st.executeUpdate();
                    }
                    else
                    {
                        return "ID already exists";
                    }

                resp = "Added!";
            } else {
                resp = "ID Can't be null";
            }
                return resp;
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }

        @RequestMapping(value = "/expense", method = RequestMethod.PUT)
        public String editExpense(@RequestBody String json) {
            try {
                JSONObject obj = new JSONObject(json);
                String resp;
                if (obj.has("id")) {
                    String query = "select * from expense where id = " + obj.getString("id");
                    PreparedStatement st = con.prepareStatement(query);
                    ResultSet rs = st.executeQuery();
                    boolean exists = rs.next();
                    if(exists) {
                        String queryUpd = "update expense set categoryId = ?, howAcquired = ?, amount = ? where id = ?";
                        st = con.prepareStatement(queryUpd);
                        st.setString(1, obj.getString("categoryID"));
                        st.setString(2, obj.getString("xa"));
                        st.setString(3, obj.getString("amount"));
                        st.setString(4, obj.getString("id"));
                        st.executeUpdate();
                    }
                    else
                    {
                        return "ID dosen't exist";
                    }

                resp = "Added!";
            } else {
                resp = "ID Can't be null";
            }
                return resp;
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }

        @RequestMapping(value = "/expense", method = RequestMethod.DELETE)
        public String deleteExpense(@RequestParam(value = "id", defaultValue = "def") String id) {
            String resp;
            try {
                String queryUpd = "delete from expense where id = ?";
                PreparedStatement st = con.prepareStatement(queryUpd);
                st.setString(1, id);
                st.executeUpdate();
            } catch (Exception e) {
                System.out.println("ERROR");
            }
            resp = "Deleted!";

            return resp;
        }

    }



    @RestController
    public static class income {
        @GetMapping("/income")
        public List<Map<String, Object>> getIncomes(@RequestParam(value = "id", defaultValue = "def") String id) {
            try {
                String query = "select * from income ";
                PreparedStatement st = con.prepareStatement(query);
                if (!id.equals("def")) {
                    query = query + "where id = " + id;
                }
                ResultSet rs = st.executeQuery(query);
                List<Map<String, Object>> rows = new ArrayList<>();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String colName = rsmd.getColumnName(i);
                        Object colVal = rs.getObject(i);
                        row.put(colName, colVal);
                    }
                    rows.add(row);
                }
                return rows;
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;

        }
    }

    @RequestMapping(value = "/income", method = RequestMethod.POST)
    public String postIncome(@RequestBody String json) {
                try {
                    JSONObject obj = new JSONObject(json);
                    String resp;
                    if (obj.has("id")) {
                String query = "select * from income where id = " + obj.getString("id");
                PreparedStatement st = con.prepareStatement(query);
                ResultSet rs = st.executeQuery();
                boolean exists = rs.next();
                if(!exists) {
                    String queryUpd = "insert into income values (?, ?, ?, ?)";
                    st = con.prepareStatement(queryUpd);
                    st.setString(1, obj.getString("id"));
                    st.setString(2, obj.getString("categoryID"));
                    st.setString(3, obj.getString("used"));
                    st.setString(4, obj.getString("amount"));
                    st.executeUpdate();
                }
                else
                {
                    return "ID already exists";
                }
 
            resp = "Added!";
        } else {
            resp = "ID Can't be null";
        }
                    return resp;
                } catch (Exception e) {
                    System.out.println(e);
                }
        return null;
    }

    @RequestMapping(value = "/income", method = RequestMethod.PUT)
    public String editIncome(@RequestBody String json) {
                try {
                    JSONObject obj = new JSONObject(json);
                    String resp;
                    if (obj.has("id")) {
                String query = "select * from income where id = " + obj.getString("id");
                PreparedStatement st = con.prepareStatement(query);
                ResultSet rs = st.executeQuery();
                boolean exists = rs.next();
                if(exists) {
                    String queryUpd = "update income set categoryId = ?, howUsed = ?, amount = ? where id = ?";
                    st = con.prepareStatement(queryUpd);
                    st.setString(1, obj.getString("categoryID"));
                    st.setString(2, obj.getString("used"));
                    st.setString(3, obj.getString("amount"));
                    st.setString(4, obj.getString("id"));
                    st.executeUpdate();
                }
                else
                {
                    return "ID dosen't exist";
                }

            resp = "Added!";
        } else {
            resp = "ID Can't be null";
        }
                    return resp;
                } catch (Exception e) {
                    System.out.println(e);
                }
        return null;
    }

    @RequestMapping(value = "/income", method = RequestMethod.DELETE)
    public String deleteIncome(@RequestParam(value = "id", defaultValue = "def") String id) {
        String resp;
        try {
            String queryUpd = "delete from income where id = ?";
            PreparedStatement st = con.prepareStatement(queryUpd);
            st.setString(1, id);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR");
        }
        resp = "Deleted!";

        return resp;
    }

}
//