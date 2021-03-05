package com.serverapplication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Scanner;

public class JavaServer {
    private static Integer SERVER_PORT = 9991;

    public JavaServer() {
        startServer();
    }

    public static void main(String[] args) {
        startServer();
        //Connection conn = connectDB("root", "", "practice");
        //executeQuery(conn);
    }

    private static void startServer() {
        //Try connect to the server on an unused port eg 9991. A successful connection will return a socket
        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            Socket connectionSocket = serverSocket.accept();

            //Create Input&Outputstreams for the connection
            InputStream inputToServer = connectionSocket.getInputStream();
            OutputStream outputFromServer = connectionSocket.getOutputStream();

            Scanner scanner = new Scanner(inputToServer, "UTF-8");
            PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);

            serverPrintOut.println("Hello World! Enter Peace to exit.");

            //Have the server take input from the client and echo it back
            //This should be placed in a loop that listens for a terminator text e.g. bye
            boolean done = false;

            while(!done && scanner.hasNextLine()) {
                String line = scanner.nextLine();
                serverPrintOut.println("Echo from <Your Name Here> Server: " + line);

                if(line.toLowerCase().trim().equals("peace")) {
                    done = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void executeQuery(Connection conn) {

        try {
            String query = "SELECT * FROM example";
            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                String id = rs.getString("id");
                System.out.println(id);

            }

        } catch (SQLException ex) {
            System.err.println("SQLException thrown");
            System.err.println(ex.getMessage());
        }

    }

    private static Connection connectDB(String username, String password, String dbName) {
        Connection conn = null;
        // Declaring all variables
        String jdbcDriver = "com.mysql.cj.jdbc.Driver";
        String host = "localhost";
        //String host = "192.168.64.2";
        String port = "8080";
        String connectionString = "jdbc:mysql://"+ host +":"+ port + "/";
        //String connectionString = "jdbc:mysql://http://"+ host +"/";

        try {
            //check jdbc driver (mysql connector / j). Make sure the connector is configured correctly (added to libraries) before checking it.
            Class.forName(jdbcDriver);
            System.out.println("Driver Loaded");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Failed To Load");
            System.out.println(ex.getMessage());
        }

        try {
            //connecting to xampp server (Apache Server)
            conn = DriverManager.getConnection(connectionString + dbName, username, password);
            System.out.println("Connected To Server Successfully");
        } catch (SQLException ex) {
            System.out.println("Failed To Connect To Server Successfully");
            System.out.println(ex.getMessage());

        }
        return conn;
    }
}
