package com.kursovaya.bd;

import com.kursovaya.Car;
import com.kursovaya.User;
import com.kursovaya.tables.MMIDMail;
import com.kursovaya.tables.MakeIDMail;
import com.kursovaya.tables.Tables;

import java.sql.*;
import java.util.ArrayList;

import static com.kursovaya.bd.JDBC.connection;

public class DatabaseController {
    public static User GetUser(String login,String pass){
        Statement stmt;
        int id;
        String name;
        String surname;
        String mail;
        String status;

        User user;
        try {
            JDBC.connect();
            stmt = connection.createStatement();
            ResultSet res1 = stmt.executeQuery("SELECT * FROM black_list WHERE login = '" + login + "'");


            while(res1.next()){
                if(res1.getString("login").equals(login)){
                    User us=new User();
                    us.setLogin("blocked");
                    return us;
                }
                break;
            }

            ResultSet res = stmt.executeQuery("SELECT * FROM users WHERE login = '" + login + "' AND pass = '" + pass + "'");

            while (res.next()){
                id = res.getInt("id");
                name = res.getString("first_name");
                surname = res.getString("last_name");
                mail=res.getString("mail");
                status=res.getString("status");
                user=new User(id,name,surname,mail,login,pass,status);
                return user;
            }
            return new User();
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return new User();
        }
    }

    public static boolean AddUser(String name,String surname,String mail,String login,String pass){
        Statement stmt ;

        User user;
        try {
            JDBC.connect();
            stmt = connection.createStatement();

            int i = stmt.executeUpdate("INSERT INTO users(first_name,last_name,mail,login,pass) VALUES('" + name + "','" + surname + "','" + mail + "','" + login + "','" + pass + "');");
            if(i==0) return false;
            else return true;
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return false;
        }
    }

    public static boolean AddAdmin(String name, String surname, String mail, String login, String pass) {
        Statement stmt;

        User user;
        try {
            JDBC.connect();
            stmt = connection.createStatement();

            int i = stmt.executeUpdate("INSERT INTO users(first_name, last_name, mail, login, pass, status) VALUES('" + name + "', '" + surname + "', '" + mail + "', '" + login + "', '" + pass + "', 'admin')");
            if(i==0) return false;
            else return true;
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Car> GetAllCars() {
        Statement stmt;
        String make,model,body;
        Integer price,userID;

        Car car;
        ArrayList<Car> cars=new ArrayList<>();
        try {
            JDBC.connect();
            stmt = connection.createStatement();

            ResultSet res = stmt.executeQuery("SELECT * FROM cars");

            while (res.next()){
                make = res.getString("make");
                model = res.getString("model");
                body = res.getString("body");
                price=res.getInt("price");
                userID=res.getInt("user_id");
                car=new Car(make,model,body,price,userID);
                cars.add(car);
            }
            return cars;
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return cars;
        }
    }

    public static boolean AddCarToSoldlist(Car car) {
        Statement stmt;
        String make=car.getMake();
        String model=car.getModel();
        String body=car.getBody();

        Integer price=car.getPrice();
        Integer user_id=car.getUserID();
        try {
            JDBC.connect();
            stmt = connection.createStatement();

            int i = stmt.executeUpdate("INSERT INTO cars(make, model, body, price, user_id) VALUES('" + make + "', '"+ model + "', '" + body + "', '" + price + "', '" + user_id + "');");
            if(i==0){
                return false;
            }
            return true;
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return false;
        }
    }

    public static boolean DeleteAccFromDB(User user) {
        Statement stmt;
        Integer id=user.getId();


        try {
            JDBC.connect();
            stmt = connection.createStatement();

            int i = stmt.executeUpdate("DELETE FROM users WHERE id = '" + id + "'");
            if(i==0){
                return false;
            }
            return true;
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return false;
        }
    }

    public static boolean ChangeUserInfo(User user) {
        Statement stmt;
        Integer id=user.getId();
        int i=0;


        try {
            JDBC.connect();
            stmt = connection.createStatement();
            if(!user.getName().isEmpty())
                i += stmt.executeUpdate("UPDATE users SET first_name = '" + user.getName() + "' AND last_name = '" + user.getSurname() + "' WHERE id = '" + id + "'");

            if(!user.getMail().isEmpty())
                i+=stmt.executeUpdate("UPDATE users SET mail = '" + user.getMail() + "' WHERE id = '" + id + "'");

            if(!user.getLogin().isEmpty())
                i+=stmt.executeUpdate("UPDATE users SET login = '" + user.getLogin() + "'  WHERE id = '" + id + "'");

            if(!user.getPass().isEmpty())
                i+=stmt.executeUpdate("UPDATE users SET pass = '" + user.getPass() + "' WHERE id = '" + id + "'");


            ResultSet res = stmt.executeQuery("SELECT * FROM users WHERE id = '" + id + "'");
            String name,surname,mail,login,pass;
            while (res.next()){
                //id = res.getInt("id");
                name = res.getString("first_name");
                surname = res.getString("last_name");
                mail=res.getString("mail");
                login=res.getString("login");
                pass=res.getString("pass");
                //user.User(id,name,surname,mail,login,pass);
                user.setId(id);
                user.setName(name);
                user.setSurname(surname);
                user.setMail(mail);
                user.setLogin(login);
                user.setPass(pass);
                break;
            }
            System.out.println(user.getName()+user.getSurname()+user.getMail()+" "+user.GetLogin()+" "+user.getPass());
            if(i==0){
                return false;
            }
            return true;
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Tables> GetCarsByStatus(String status) {
        Statement stmt;
        String make,model,body,mail;
        Integer price,userID;

        Tables car;
        ArrayList<Tables> cars=new ArrayList<>();
        try {
            JDBC.connect();
            stmt = connection.createStatement();

            ResultSet res = stmt.executeQuery("SELECT cars.make, cars.model, cars.body, cars.price, cars.user_id, users.mail FROM cars INNER JOIN users ON cars.user_id = users.id AND users.status = '" + status + "'");

            while (res.next()){
                make = res.getString("make");
                model = res.getString("model");
                body = res.getString("body");
                price=res.getInt("price");
                userID=res.getInt("user_id");
                mail=res.getString("mail");
                System.out.println(make+" "+model);
                car=new Tables(make,model,body,price,mail,userID);
                cars.add(car);
            }
            return cars;
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return cars;
        }
    }

    public static boolean AddRequest(Integer user, String make, Integer id) {
        Statement stmt = null;

        String make1;
        Integer user_id;
        boolean exist=false;
        try {
            JDBC.connect();
            stmt = connection.createStatement();

            ResultSet res =stmt.executeQuery("SELECT cars.make, cars.model, cars.user_id FROM cars INNER JOIN users ON cars.user_id = users.id AND users.status = 'user'");

            while (res.next()){
                make1 = res.getString("make");
                user_id=res.getInt("user_id");
                System.out.println(make1+" "+make+" "+user_id+" "+id);
                if(make.equals(make1) && user_id.equals(id) ){
                    exist=true;
                    break;
                }
            }
            System.out.println(exist);
            if(!exist)return false;
            else{
                int i = stmt.executeUpdate("INSERT INTO requests(user_id, request_id, make) VALUES('" + id + "', '" + user + "', '" + make + "');");
                if(i==0){
                    return false;
                }
                return true;
            }
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return false;
        }
    }

    public static boolean AddRequestNewCar(Integer request_id, String make, String model) {
        Statement stmt = null;
        String make1,model1;
        Integer admin_id=0;
        boolean exist=false;
        try {
            JDBC.connect();
            stmt = connection.createStatement();
            ResultSet res =stmt.executeQuery("SELECT cars.make, cars.model, cars.user_id FROM cars INNER JOIN users ON cars.user_id = users.id AND users.status = 'admin'");

            while (res.next()){
                make1 = res.getString("make");
                model1 = res.getString("model");
                if(make.equals(make1) && model.equals(model1)){
                    admin_id=res.getInt("user_id");
                    exist=true;
                    break;
                }
            }
            if(!exist) return false;
            else{
                int i = stmt.executeUpdate("INSERT INTO requests(user_id, request_id, make) VALUES('" + admin_id + "', '" + request_id + "', '" + make + "');");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    public static ArrayList<MakeIDMail> GetSended(Integer user_id) {

        Statement stmt = null;
        String make,mail;
        Integer request_id;

        MakeIDMail car;
        ArrayList<MakeIDMail> cars=new ArrayList<>();
        try {
            JDBC.connect();
            stmt = JDBC.connection.createStatement();
            System.out.println(user_id);
            String str=Integer.toString(user_id);
            ResultSet res = stmt.executeQuery("SELECT requests.user_id, requests.make, requests.request_id, requests.status_prin, users.mail FROM users INNER JOIN requests ON requests.user_id = users.id AND requests.request_id = '" + str + "' AND requests.status_prin='0'");
            while (res.next()){
                make = res.getString("make");
                request_id = res.getInt("user_id");
                mail = res.getString("mail");
                System.out.println(make+request_id+mail);
                car=new MakeIDMail(make,request_id,mail);
                cars.add(car);
            }
            return cars;
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return cars;
        }
    }

    public static ArrayList<MakeIDMail> GetIncoming(Integer user_id) {
        Statement stmt = null;
        String make,mail;
        Integer request_id;

        MakeIDMail car;
        ArrayList<MakeIDMail> cars=new ArrayList<>();
        try {
            JDBC.connect();
            stmt = connection.createStatement();
            System.out.println(user_id);
            String str=Integer.toString(user_id);
            ResultSet res = stmt.executeQuery("SELECT requests.user_id, requests.make, requests.request_id, users.mail FROM users INNER JOIN requests ON requests.request_id = users.id AND requests.user_id = '" + str + "'");

            while (res.next()){
                make = res.getString("make");
                mail = res.getString("mail");
                request_id = res.getInt("request_id");
                System.out.println(make+request_id+mail);
                car=new MakeIDMail(make,request_id,mail);
                cars.add(car);
            }
            return cars;
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return cars;
        }
    }

    public static ArrayList<MMIDMail> GetIncoming_() {
        Statement stmt = null;
        String make,model,mail;
        Integer id;

        MMIDMail car;
        ArrayList<MMIDMail> cars=new ArrayList<>();
        try {
            JDBC.connect();
            stmt = connection.createStatement();

            ResultSet res = stmt.executeQuery("SELECT	requests.make, requests.request_id, users.mail FROM users INNER JOIN requests ON requests.user_id=users.id AND users.status='admin' AND requests.status_prin='0'");

            while (res.next()){
                make = res.getString("make");
                mail = res.getString("mail");
                model="";
                id = res.getInt("request_id");

                car=new MMIDMail(make,model,id,mail);
                cars.add(car);
            }
            return cars;
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return cars;
        }
    }

    public static ArrayList<User> GetAllUsers() {
        Statement stmt = null;
        Integer id;
        String name;
        String surname;
        String mail;
        String login;
        ArrayList<User> users=new ArrayList<>();
        User user;
        try {
            JDBC.connect();
            stmt = connection.createStatement();

            ResultSet res = stmt.executeQuery("SELECT * FROM users WHERE status = 'user'");

            while (res.next()){
                id=res.getInt("id");
                name = res.getString("first_name");
                surname = res.getString("last_name");
                mail=res.getString("mail");
                login=res.getString("login");
                user=new User(name,surname,mail,login);
                user.setId(id);
                users.add(user);
            }
            return users;
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return users;
        }
    }

    public static ArrayList<User> GetAllAdmins() {
        Statement stmt = null;
        Integer id;
        String name;
        String surname;
        String mail;
        String login;
        ArrayList<User> users=new ArrayList<>();
        User user;
        try {
            JDBC.connect();
            stmt = connection.createStatement();

            ResultSet res = stmt.executeQuery("SELECT * FROM users WHERE status = 'admin'");

            while (res.next()){
                id=res.getInt("id");
                name = res.getString("first_name");
                surname = res.getString("last_name");
                mail=res.getString("mail");
                login=res.getString("login");
                user=new User(name,surname,mail,login);
                user.setId(id);
                users.add(user);
            }
            return users;
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return users;
        }
    }

    public static boolean BlockUser(int id) {
        PreparedStatement stmt = null;
        String login = "";
        boolean exist = false;

        try {
            JDBC.connect();
            stmt = connection.prepareStatement("SELECT login FROM users where id = ?");
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                login = res.getString("login");
                exist = true;
                break;
            }

            if (!exist) {
                return false;
            } else {
                stmt = connection.prepareStatement("INSERT INTO black_list(id, login) VALUES(?, ?)");
                stmt.setInt(1, id);
                stmt.setString(2, login);
                int i = stmt.executeUpdate();

                return i != 0;
            }
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }

            try {
                JDBC.connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static boolean UnblockUser(Integer id) {
        Statement stmt;

        try {
            JDBC.connect();
            stmt = connection.createStatement();

            int i = stmt.executeUpdate("DELETE FROM black_list WHERE id = '" + id + "'");
            System.out.println(i);
            if (i == 0) {
                return false;
            }
            return true;
        } catch (

                SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            return false;
        }
    }

    public static boolean AcceptRequest(Integer accept_id, int user_id, String make) {
        Statement stmt;

        try {
            JDBC.connect();
            stmt = connection.createStatement();

            int i = stmt.executeUpdate("UPDATE requests SET status_prin = '1' WHERE user_id = '"+ user_id +"' AND request_id = '"+ accept_id +"' AND make = '"+ make +"'");
            System.out.println(i);
            if (i == 0) {
                return false;
            }
            return true;
        } catch (

                SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            return false;
        }
    }

    public static ArrayList<MMIDMail> GetAccepted(Integer user_id) {
        Statement stmt = null;
        String make,model;
        Integer request_id;
        MMIDMail car;
        ArrayList<MMIDMail> cars=new ArrayList<>();
        try {
            JDBC.connect();
            stmt = connection.createStatement();
            System.out.println(user_id);
            String str=Integer.toString(user_id);
            ResultSet res = stmt.executeQuery("SELECT requests.make, cars.model, requests.request_id FROM requests INNER JOIN cars ON requests.make = cars.make AND requests.user_id = '" + str +"' AND requests.status_prin='1'");
            while (res.next()){
                make = res.getString("make");
                model = res.getString("model");
                request_id = res.getInt("request_id");
                car=new MMIDMail(make, model, request_id, "");
                cars.add(car);
            }
        } catch (SQLException se) {
            System.err.println("Error executing query: " + se.getMessage());
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se) {
                System.err.println("Error closing statement or disconnecting: " + se.getMessage());
                se.printStackTrace();
            }
        }
        return cars;
    }}
