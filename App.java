
import java.util.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class App {

    public static void main(String... args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "1234");
            Statement stmt = con.createStatement();

            String name, password;
            String name1 = null, password1 = null;

            Scanner sc = new Scanner(System.in);
            Console cnsl = System.console();

            System.out.println("\tWelcome.... \n\t1 to login \n\t2 to create new account");
            System.out.print("\tEnter choice: ");

            int choice = sc.nextInt();

            if (choice == 1) {
                int x = 1, flag = 0;
                while (x > 0) {
                    System.out.print("\n\tEnter name:");
                    name = cnsl.readLine();

                    System.out.print("\tEnter Password: ");
                    char ch[] = cnsl.readPassword();
                    password = new String(ch);

                    ResultSet rs = stmt.executeQuery("SELECT * FROM login");

                    while (rs.next()) {

                        name1 = rs.getString("name");
                        // System.out.println(name1);

                        password1 = rs.getString("password");
                        // System.out.println(password1);

                        if (name.equals(name1) && password.equals(password1)) {
                            flag++;
                            break;
                        }
                    }
                    if (flag == 0) {
                        System.out.println("\n\tname dosent exist");
                        break;
                    }

                    else {
                        System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------------------");
                        System.out.println("\n\tlogin successful");
                        int l = 0;
                        while (l == 0) {
                            App app = new App();
                            System.out.println("\n\t1 rent book");
                            System.out.println("\t2 see available books");
                            System.out.println("\t3 previous year paper available");
                            System.out.println("\t4 exit");
                            System.out.print("\n\tenter choice: ");
                            int choice1;
                            choice1 = sc.nextInt();
                            switch (choice1) {
                                case 1:
                                    app.rent();
                                    break;
                                case 2:
                                    app.books();
                                    break;
                                case 3:
                                    app.paper();
                                    break;
                                case 4:
                                    return;
                                default:
                                    System.out.println("enter correct choice");

                            }
                        }

                    }
                }
            }

            else if (choice == 2) {
                System.out.print("\n\tEnter name:");
                name = cnsl.readLine();

                System.out.print("\tCreate Password: ");
                password = cnsl.readLine();

                String sql = "INSERT INTO login VALUES('" + name + "','" + password + "');";
                stmt.executeUpdate(sql);
                System.out.println("\tlogin created Successfully..");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    void rent() {
        try {
            int srno, flag = 0;
            Scanner sc = new Scanner(System.in);
            Console cnsl = System.console();
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "1234");
            Statement stmt = con.createStatement();

            System.out.print("\n\t Enter book srno.: ");
            srno = sc.nextInt();

            ResultSet rs = stmt.executeQuery("SELECT * FROM books");

            while (rs.next()) {
                int srno1;
                srno1 = rs.getInt("srno");
                // System.out.println(srno");

                if (srno1 == srno) {
                    flag++;
                    break;
                }
            }
            if (flag == 0) {
                System.out.println("\n\tname dosent exist");
            } else {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                String name, d, d1;
                int days;
                System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("\n\t Book found please enter following details");
                System.out.print("\t Enter your name: ");
                name = cnsl.readLine();
                System.out.print("\t Enter for how many days you want to issue book for: ");
                days = sc.nextInt();
                d = sdf.format(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, days);
                d1 = sdf.format(cal.getTime());
                String sql = "INSERT INTO rent VALUES('" + name + "','" + d + "','" + d1 + "');";
                stmt.executeUpdate(sql);
                System.out.println("\tAdded to Rented books Successfully..");
                System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------------------");
            }
            ;

        } catch (Exception e) {
            System.out.println("\n\tcouldn't connect to database");
        }

    }

    void books() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "1234");
            Statement stmt = con.createStatement();

            String name1, author1;
            int srno1;
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("\n%10s| %20s| %20s|","SRNO","NAME OF BOOK","NAME OF AUTHOR");
            while (rs.next()) {
                srno1 = rs.getInt("srno");
                name1 = rs.getString("name");
                author1 = rs.getString("authorname");

                System.out.printf("\n%10s| %20s| %20s|", srno1, name1, author1);
            }
            System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("\n");

        } catch (Exception e) {
            System.out.println("\n\tcouldn't connect to database");
            e.printStackTrace();
        }
    }

    void paper() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "1234");
            Statement stmt = con.createStatement();

            String board1, year1;
            int srno1, std1;
            ResultSet rs = stmt.executeQuery("SELECT * FROM paper");
            System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("\n\t%10s| %20s| %20s| %20s| ","SRNO","BOARD","STD","YEAR");
            while (rs.next()) {
                srno1 = rs.getInt("srno");
                board1 = rs.getString("board");
                std1 = rs.getInt("std");
                year1 = rs.getString("year");

                System.out.printf("\n\t%10s| %20s| %20s| %20s|", srno1, board1, std1, year1);
            }
            System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("\n");

        } catch (Exception e) {
            System.out.println("\n\tcouldn't connect to database");
        }
    }
}
