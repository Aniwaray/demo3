package com.example.kr3demo;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private final String host = "127.0.0.1";
    private final String port = "3306";
    private final String dbName = "trade";
    private final String login = "root";
    private final String password = "vanessa2020k";

    private static int max;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?characterEncoding=UTF8";
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(connStr, login, password);
    }

    public int getUsers(String log, String pas) throws SQLException, ClassNotFoundException {
        String sql = "SELECT count(*) as n FROM user where UserLogin=? and UserPassword=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, log);
        statement.setString(2, pas);
        ResultSet resultSet = statement.executeQuery();
        int count = 0;
        while(resultSet.next()) {
            count = resultSet.getInt(1);
        }
        return count;
    }

    public int getRole(String log, String pass) throws SQLException, ClassNotFoundException {
        String sql = "SELECT UserRole FROM user where UserLogin=? and UserPassword=? group by UserRole";

        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, log);
        statement.setString(2, pass);
        ResultSet res = statement.executeQuery();

        int role = 0;
        while (res.next()) {
            role = res.getInt("UserRole");
        }
        return role;
    }

    public ArrayList<ProductData> getProduct() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM product ORDER BY `ProductArticleNumber` DESC";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<ProductData> product = new ArrayList<>();
        while(res.next())
            product.add(new ProductData(res.getString("ProductName"), res.getString("ProductPhoto"), res.getInt("ProductCost"), res.getString("ProductDescription"), res.getString("ProductManufacturer"), res.getInt("ProductQuantityInStock")));
        return product;
    }
    public String getName(String log) throws SQLException, ClassNotFoundException {
        String sql = "SELECT UserName FROM user where UserLogin='"+log+"' ";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        String name = "";
        while (resultSet.next()) {
            name = resultSet.getString("UserName");
        }
        return name;
    }
    public String getSurname(String log) throws SQLException, ClassNotFoundException {
        String sql = "SELECT UserSurname FROM user where UserLogin='"+log+"' ";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        String name = "";
        while (resultSet.next()) {
            name = resultSet.getString("UserSurname");
        }
        return name;
    }
    public String getPatronymic(String log) throws SQLException, ClassNotFoundException {
        String sql = "SELECT UserPatronymic FROM user where UserLogin='"+log+"' ";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        String name = "";
        while (resultSet.next()) {
            name = resultSet.getString("UserPatronymic");
        }
        return name;
    }
    public ArrayList<ProductData> getProductSortASC() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM product ORDER BY ProductCost ASC";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<ProductData> stud = new ArrayList<>();
        while(res.next())
            stud.add(new ProductData(res.getString("ProductName"), res.getString("ProductPhoto"), res.getInt("ProductCost"), res.getString("ProductDescription"), res.getString("ProductManufacturer"), res.getInt("ProductQuantityInStock")));
        return stud;
    }

    public ArrayList<ProductData> getProductSortDES() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM product ORDER BY ProductCost DESC";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<ProductData> stud = new ArrayList<>();
        while(res.next())
            stud.add(new ProductData(res.getString("ProductName"), res.getString("ProductPhoto"), res.getInt("ProductCost"), res.getString("ProductDescription"), res.getString("ProductManufacturer"), res.getInt("ProductQuantityInStock")));
        return stud;
    }

    public void productAdd(int cost, String disc, String manu, String name, int stock, String categ) throws SQLException, ClassNotFoundException  {
        String sql = "insert into product values (?,?,?,?,null,?,?,null,?,null)";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(max));
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, disc);
        preparedStatement.setString(4, categ);
        preparedStatement.setString(5, manu);
        preparedStatement.setInt(6, cost);
        preparedStatement.setInt(7, stock);

        preparedStatement.executeUpdate();
    }

    public Integer maxProduct() throws SQLException, ClassNotFoundException {
        String sql = "SELECT count(ProductName) FROM product";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        max=0;
        while(res.next()) {
            max = res.getInt(1);
        }
        max+=1;
        return max;
    }

    public ArrayList<String> getCategory() throws SQLException, ClassNotFoundException {
        String sql = "SELECT DISTINCT ProductCategory FROM product";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        ArrayList<String> tasks = new ArrayList<>();
        while (res.next()) {
            tasks.add(res.getString("ProductCategory"));
        }    return tasks;
    }

//    public ArrayList<String> getStatus() throws SQLException, ClassNotFoundException {
//        String sql = "SELECT ProductStatus FROM product where ";
//        Statement statement = getDbConnection().createStatement();
//        ResultSet res = statement.executeQuery(sql);
//        ArrayList<String> tasks = new ArrayList<>();
//        while (res.next()) {
//            tasks.add(res.getString("ProductCategory"));
//        }    return tasks;
//    }
}
