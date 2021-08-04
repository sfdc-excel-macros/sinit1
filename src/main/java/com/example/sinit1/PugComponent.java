package com.example.sinit1;

import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.ResultSet;
import java.sql.Connection;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import java.sql.Statement;
import javax.sql.DataSource;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.jdbc.core.JdbcTemplate;


@Component
@WebServlet(name = "pug", urlPatterns = "/pug")
public class PugComponent extends HttpServlet {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            setupTable();
            doFourth(req, resp);
            doSeventh(req, resp);
            doNinth(req, resp);
            doTenth(req, resp);
            doEleventh(req, resp);
            doTwelfth(req, resp);
            doThirteenth(req, resp);
            doFourteenth(req, resp);
            doFifteenth(req, resp);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void setupTable() throws Exception {
        jdbcTemplate.execute("DROP TABLE foo if exists");
        jdbcTemplate.execute("CREATE TABLE foo (id int, b varchar(1024), c varchar(1024))");
        jdbcTemplate.execute("INSERT INTO foo (id, b, c) VALUES (1337, 'hi', 'yo')");
    }
    private void doFourth(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String bar = req.getParameter("bar");
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        String qry = "SELECT ID FROM FOO";
        qry = qry + "WHERE C = '";
        qry = qry + bar;
        qry = qry + "'";

        PreparedStatement statement = conn.prepareStatement(qry);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            System.out.println("row: " + rs.getString(1));
        }
    }


    private void doSeventh(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String foo = req.getParameter("foo");
        String bar = req.getParameter("bar");
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        String qry = "INSERT INTO FOO (id, b, c) VALUES(1338, '" + foo + "','" + bar + "')";

        jdbcTemplate.execute(qry);
    }


    private void doSixth(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String foo = req.getParameter("foo");
        String bar = req.getParameter("bar");
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        String qry = "SELECT ID FROM " + foo + " WHERE C ='" + bar + "'";

        PreparedStatement statement = conn.prepareStatement(qry);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            System.out.println("row: " + rs.getString(1));
        }
    }
    private void doTenth(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String foo = req.getParameter("foo");
        String bar = req.getParameter("bar");
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        stmt.addBatch("INSERT INTO foo (id, b, c) VALUES (1337, ' " + foo + " ', 'prefix" + bar + "postfix')");
        int[] res = stmt.executeBatch();
        System.out.println("10 res: " + res.toString());
    }

    private void doNinth(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String foo = req.getParameter("foo");
        String bar = req.getParameter("bar");
        List<Map<String, Object>> res = jdbcTemplate.queryForList("select id from foo where b = '" + foo + "' and c = '" + bar + "'");
        for(var map: res) {
            for(Map.Entry<String, Object> entry: map.entrySet()) {
                System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
            }

        }
        System.out.println("9 done");
    }
    private void doEleventh(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String foo = req.getParameter("foo");
        String bar = req.getParameter("bar");
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("select id from foo where b = '" + foo + "' and c = '" + bar + "'");
        System.out.println("11 done");
    }
    private void doTwelfth(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String foo = req.getParameter("foo");
        String bar = req.getParameter("bar");
        SqlRowSet srs = jdbcTemplate.queryForRowSet("select id from foo where b = '" + foo + "' and c = '" + bar + "'");
        while(srs.next()) {
            System.out.println("row: " + srs.getString(1));
        }
        System.out.println("12 done");
    }

    private void doThirteenth(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        PrintWriter reader = resp.getWriter();

        String foo = req.getParameter("foo");
        String bar = req.getParameter("bar");

        DataSource ds = jdbcTemplate.getDataSource();
        Connection conn = ds.getConnection();

        PreparedStatement statement = conn.prepareStatement("select id from foo where b = '" + foo + "' or b = 'ffff' and c = '" + bar + "' or c = 'x" + bar + "' or c='" + bar.toUpperCase() + "'");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            System.out.println("row: " + resultSet.getLong(1));
        }
        System.out.println("done");

    }


    private void doFourteenth(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        PrintWriter reader = resp.getWriter();

        String foo = req.getParameter("foo");
        String bar = req.getParameter("bar");

        DataSource ds = jdbcTemplate.getDataSource();
        Connection conn = ds.getConnection();

        PreparedStatement statement = conn.prepareStatement("select id from foo where b = '" + foo + "' or b = '" + foo.substring(1) + "' or b = '" + foo + "' or b='" + foo + "' or c = '" + bar + "' or c = '" + bar +"'");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            System.out.println("row: " + resultSet.getLong(1));
        }
        System.out.println("done");
    }

    private void doFifteenth(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        PrintWriter reader = resp.getWriter();

        String foo = req.getParameter("foo");
        String bar = req.getParameter("bar");

        DataSource ds = jdbcTemplate.getDataSource();
        Connection conn = ds.getConnection();

        var str = "select id from foo where b = '" + foo + "' or b = '" + foo.substring(1) + "' or b = '" + foo + "' or b='" + foo + "' or c = '" + bar + "' or c = '" + bar +"'";
        PreparedStatement statement = conn.prepareStatement(str);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            System.out.println("row: " + resultSet.getLong(1));
        }
        System.out.println("done");
    }



}
