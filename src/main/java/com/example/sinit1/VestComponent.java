package com.example.sinit1;

import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import java.sql.ResultSet;
import java.sql.Connection;
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
@WebServlet(name = "vest", urlPatterns = "/vest")
public class VestComponent extends HttpServlet {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            setupTable();
            doFirst(req, resp);
            doSecond(req, resp);
            doThird(req, resp);
            doFifth(req, resp);

        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void setupTable() throws Exception {
        jdbcTemplate.execute("DROP TABLE foo if exists");
        jdbcTemplate.execute("CREATE TABLE foo (id int, b varchar(1024), c varchar(1024))");
        jdbcTemplate.execute("INSERT INTO foo (id, b, c) VALUES (1337, 'hi', 'yo')");
    }

    private void doFifth(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String bar = req.getParameter("bar");
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        String qry = "SELECT ID FROM FOO WHERE C ='" + bar + "'";

        PreparedStatement statement = conn.prepareStatement(qry);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            System.out.println("row: " + rs.getString(1));
        }
        System.out.println("5 done");
    }



    private void doThird(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String bar = req.getParameter("bar");
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        PreparedStatement statement = conn.prepareStatement("select id from foo where c = '" + bar + "'");
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            System.out.println("row: " + rs.getString(1));
        }
    }


    private void doSecond(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int foo = 5;
        String bar = req.getParameter("bar");
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        PreparedStatement statement = conn.prepareStatement("select id from foo where id = " + foo + " and c = '" + bar + "'");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            System.out.println("row: " + resultSet.getString(1));
        }

    }

    private void doFirst(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            PrintWriter reader = resp.getWriter();

            reader.println("hello: " + req.getParameter("foo"));
            String foo = req.getParameter("foo");
            String bar = req.getParameter("bar");

            DataSource ds = jdbcTemplate.getDataSource();
            Connection conn = ds.getConnection();

            PreparedStatement statement = conn.prepareStatement("select id from foo where b = '" + foo + "' and c = '" + bar + "'");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                System.out.println("row: " + resultSet.getLong(1));
            }
            System.out.println("done");
        } catch (Exception ex) {
            throw new ServletException(ex);
        }

    }
}
