// Author: Ming Jin
// Contact: jinming0106g@gmail.com
// Created Date: 22th Aug, 2021

package ming.task;
 
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import ming.task.model.Data;

public class RestServlet extends HttpServlet
{
    private DataSource sqlDataSource = null;
    private Connection connection = null;

    @Resource(name="jdbc/mysqldb", type=DataSource.class)
    private void setDataSource(DataSource ds)
    {
        System.out.println("---- data source linking ---");
        sqlDataSource = ds;
        try {
            connection = sqlDataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void preDestroyMethod()
    {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("---- get request ---");
        String uri = request.getRequestURI();
        if (uri.equals("/rest/data")) {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);

            try {
                Statement stmt = connection.createStatement();

                List<Data> dataArray = new ArrayList<Data>();
                ResultSet rs = stmt.executeQuery("SELECT * FROM data");

                while (rs.next()) {
                    Data data = new Data();
                    data.setId(rs.getInt("id"));
                    data.setData(rs.getString("data"));
                    data.setCreatedAt(rs.getString("created_at"));
                    data.setUpdatedAt(rs.getString("updated_at"));

                    dataArray.add(data);
                }
                stmt.close();

                String dataListJsonString = new Gson().toJson(dataArray);

                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(dataListJsonString);
                out.flush();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("---- post request ---");
        String uri = request.getRequestURI();
        if (uri.equals("/rest/data")) {

            String data = request.getParameterValues("data")[0];
            try {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO data(data) VALUE(?)", Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, data);
                stmt.executeUpdate();

                ResultSet generatedKeys = stmt.getGeneratedKeys();
                generatedKeys.next();
                Long insertedId = generatedKeys.getLong(1);

                Data newData = new Data();
                ResultSet rs = stmt.executeQuery("SELECT * FROM data WHERE id=" + insertedId);

                if (rs.next()) {
                    newData.setId(rs.getInt("id"));
                    newData.setData(rs.getString("data"));
                    newData.setCreatedAt(rs.getString("created_at"));
                    newData.setUpdatedAt(rs.getString("updated_at"));
                }

                stmt.close();

                String newDataJsonString = new Gson().toJson(newData);

                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(newDataJsonString);
                out.flush();

            } catch (Exception e) {
                e.printStackTrace();

            }

        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("---- put request ---");
        String uri = request.getRequestURI();
        if (uri.equals("/rest/data")) {

            String data = request.getParameterValues("data")[0];
            String id = request.getParameterValues("id")[0];
            try {
                PreparedStatement stmt = connection.prepareStatement("UPDATE data SET data = ? WHERE id = ?");
                stmt.setString(1, data);
                stmt.setString(2, id);
                stmt.executeUpdate();
                stmt.close();

                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print("{\"success\":true}");
                out.flush();

            } catch (Exception e) {
                e.printStackTrace();

            }

        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("---- delete request ---");
        String uri = request.getRequestURI();
        if (uri.equals("/rest/data")) {

            String id = request.getParameterValues("id")[0];
            try {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM data WHERE id = ?");
                stmt.setString(1, id);
                stmt.executeUpdate();
                stmt.close();

                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print("{\"success\":true}");
                out.flush();

            } catch (Exception e) {
                e.printStackTrace();

            }

        }
    }
}
