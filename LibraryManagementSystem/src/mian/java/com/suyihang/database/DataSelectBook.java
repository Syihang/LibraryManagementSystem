package com.suyihang.database;

import com.suyihang.entity.Book;
import com.suyihang.entity.Borrow;
import com.suyihang.entity.Type;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataSelectBook {
    // 查找书籍,精确查找，模糊查找

    // 查找全部书籍信息
    public static ArrayList<Book> selectAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 打开数据库连接
            con = DataControl.openData();

            // 创建Statement对象
            stmt = con.createStatement();

            // 执行查询SQL语句
            String query = "SELECT ISBN, title, author, publisher, publication_date, total, price, type_id FROM book";
            rs = stmt.executeQuery(query);

            // 处理查询结果
            while (rs.next()) {
                String isbn = rs.getString("ISBN");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                Date publicationDate = rs.getDate("publication_date");
                int total = rs.getInt("total");
                double price = rs.getDouble("price");
                int typeId = rs.getInt("type_id");

                Book book = new Book(isbn, title, author, publisher, publicationDate, total, price, typeId);
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            // 关闭结果集、声明和连接
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) DataControl.closeData(con);
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return books;
    }

    /**
     * 根据书籍ISBN编号查找书籍
     *
     * @param isbn 书籍的ISBN编号
     * @return 找到的书籍对象，如果未找到则返回null
     */
    public static Book selectBookByISBN(String isbn) {
        Book book = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 打开数据库连接
            con = DataControl.openData();

            // 创建PreparedStatement对象
            String query = "SELECT ISBN, title, author, publisher, publication_date, total, price, type_id FROM book WHERE ISBN = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, isbn);

            // 执行查询SQL语句
            rs = pstmt.executeQuery();

            // 处理查询结果
            if (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                Date publicationDate = rs.getDate("publication_date");
                int total = rs.getInt("total");
                double price = rs.getDouble("price");
                int typeId = rs.getInt("type_id");

                book = new Book(isbn, title, author, publisher, publicationDate, total, price, typeId);
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            // 关闭结果集、声明和连接
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) DataControl.closeData(con);
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return book;
    }

    // 查找书籍类别，返回值Map<int, String>

    // 查找书籍全部类别返回值ArrayList<Type>
    public static ArrayList<Type> getAllType() {
        ArrayList<Type> types = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 打开数据库连接
            con = DataControl.openData();

            // 创建Statement对象
            stmt = con.createStatement();

            // 执行查询SQL语句
            String query = "SELECT type_id, type FROM type";
            rs = stmt.executeQuery(query);

            // 处理查询结果
            while (rs.next()) {
                int typeId = rs.getInt("type_id");
                String typeName = rs.getString("type");

                // 创建Type对象并添加到列表中
                Type type = new Type(typeId, typeName);
                types.add(type);
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            // 关闭结果集、声明和连接
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) DataControl.closeData(con);
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return types;
    }

    // 查找类别id在书籍表中出现的次数
    public static int findTypeIDinBookTable(int id) {
        int count = 0;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 打开数据库连接
            con = DataControl.openData();

            // 创建PreparedStatement对象，使用参数化查询防止SQL注入攻击
            String query = "SELECT COUNT(*) AS count FROM book WHERE type_id = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);

            // 执行查询
            rs = stmt.executeQuery();

            // 处理查询结果
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            // 关闭结果集、声明和连接
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) DataControl.closeData(con);
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return count;
    }

    // 查找类别id在借阅表中出现的次数
    public static int findTypeIDinBorrowTable(int id) {
        int count = 0;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 打开数据库连接
            con = DataControl.openData();

            // 创建PreparedStatement对象，使用参数化查询防止SQL注入攻击
            String query = "SELECT COUNT(*) AS count FROM borrow b INNER JOIN book bk ON b.ISBN = bk.ISBN WHERE bk.type_id = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);

            // 执行查询
            rs = stmt.executeQuery();

            // 处理查询结果
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            // 关闭结果集、声明和连接
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) DataControl.closeData(con);
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return count;
    }

    // 查找类别表中的全部信息并以键值对形式存储
    /**
     * 查询所有书籍类型及其在书籍表中出现的次数
     * @return 包含类型名称及出现次数的映射
     */
    public static Map<String, Integer> findAllTypeinBookTableNum() {
        Map<String, Integer> typeCounts = new HashMap<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 打开数据库连接
            con = DataControl.openData();

            // 创建Statement对象
            stmt = con.createStatement();

            // 执行查询SQL语句，获取所有类型
            String query = "SELECT type_id, type FROM type";
            rs = stmt.executeQuery(query);

            // 遍历每种类型
            while (rs.next()) {
                int typeId = rs.getInt("type_id");
                String typeName = rs.getString("type");

                // 查询该类型在书籍表中出现的次数
                int count = findTypeIDinBookTable(typeId);

                // 将类型名称及其出现次数存入映射中
                typeCounts.put(typeName, count);
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            // 关闭结果集、声明和连接
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) DataControl.closeData(con);
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return typeCounts;
    }

    // 查找所有类型书籍被借阅次数
    public static Map<String, Integer> findAllTypeinBorrowTableNum() {
        Map<String, Integer> typeCounts = new HashMap<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 打开数据库连接
            con = DataControl.openData();

            // 创建Statement对象
            stmt = con.createStatement();

            // 执行查询SQL语句，获取所有类型
            String query = "SELECT type_id, type FROM type";
            rs = stmt.executeQuery(query);

            // 遍历每种类型
            while (rs.next()) {
                int typeId = rs.getInt("type_id");
                String typeName = rs.getString("type");

                // 查询该类型在借阅表中出现的次数
                int count = findTypeIDinBorrowTable(typeId);

                // 将类型名称及其出现次数存入映射中
                typeCounts.put(typeName, count);
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            // 关闭结果集、声明和连接
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) DataControl.closeData(con);
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return typeCounts;
    }

    // 查找所有类型书籍被某学生借阅次数
    public static Map<String, Integer> findAllTypeinBorrowTableNumByStudentID(String studentID) {
        Map<String, Integer> typeCounts = new HashMap<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 打开数据库连接
            con = DataControl.openData();

            // 创建Statement对象
            stmt = con.createStatement();

            // 定义SQL查询语句
            String query = "SELECT t.type AS category, COUNT(*) AS borrow_count " +
                    "FROM borrow b " +
                    "JOIN book bk ON b.ISBN = bk.ISBN " +
                    "JOIN type t ON bk.type_id = t.type_id " +
                    "WHERE b.student_id = '" + studentID + "' " +
                    "GROUP BY t.type_id, t.type";

            // 执行查询
            rs = stmt.executeQuery(query);

            // 处理结果集
            while (rs.next()) {
                String category = rs.getString("category");
                int borrowCount = rs.getInt("borrow_count");
                typeCounts.put(category, borrowCount);
            }

        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            // 关闭结果集、声明和连接
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) DataControl.closeData(con);
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return typeCounts;
    }

    public static Map<String, Integer> findAllType() {
        Map<String, Integer> typeCounts = new HashMap<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 打开数据库连接
            con = DataControl.openData();

            // 创建Statement对象
            stmt = con.createStatement();

            // 执行查询SQL语句，获取所有类型
            String query = "SELECT type_id, type FROM type";
            rs = stmt.executeQuery(query);

            // 遍历每种类型
            while (rs.next()) {
                int typeId = rs.getInt("type_id");
                String typeName = rs.getString("type");

                // 将类型名称及其出现次数存入映射中
                typeCounts.put(typeName, typeId);
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            // 关闭结果集、声明和连接
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) DataControl.closeData(con);
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return typeCounts;
    }

    // 按条件查找书籍
    public static ArrayList<Book> findBookByCriteria(String title, String author, int typeID) {
        ArrayList<Book> books = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 打开数据库连接
            con = DataControl.openData();

            // 构建查询语句
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM book WHERE 1=1");
            if (title != null && !title.isEmpty()) {
                queryBuilder.append(" AND title LIKE ?");
            }
            if (author != null && !author.isEmpty()) {
                queryBuilder.append(" AND author LIKE ?");
            }
            if (typeID != 0) {
                queryBuilder.append(" AND type_id = ?");
            }

            // 创建PreparedStatement对象，使用参数化查询
            stmt = con.prepareStatement(queryBuilder.toString());

            // 设置参数
            int paramIndex = 1;
            if (title != null && !title.isEmpty()) {
                stmt.setString(paramIndex++, "%" + title + "%");
            }
            if (author != null && !author.isEmpty()) {
                stmt.setString(paramIndex++, "%" + author + "%");
            }
            if (typeID != 0) {
                stmt.setInt(paramIndex, typeID);
            }

            // 执行查询
            rs = stmt.executeQuery();

            // 处理查询结果
            while (rs.next()) {
                String ISBN = rs.getString("ISBN");
                String bookTitle = rs.getString("title");
                String bookAuthor = rs.getString("author");
                String publisher = rs.getString("publisher");
                Date publicationDate = rs.getDate("publication_date");
                int total = rs.getInt("total");
                Double price = rs.getDouble("price");
                int typeId = rs.getInt("type_id");

                // 创建Book对象并添加到列表中
                Book book = new Book(ISBN, bookTitle, bookAuthor, publisher, publicationDate, total, price, typeId);
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            // 关闭结果集、声明和连接
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) DataControl.closeData(con);
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return books;
    }

    public static String findBookTypeByID(int id) {
        String typeName = null;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 打开数据库连接
            con = DataControl.openData();

            // 创建Statement对象
            stmt = con.createStatement();

            // 执行查询SQL语句，获取所有类型
            String query = "SELECT type FROM type WHERE type_id = " + id;
            rs = stmt.executeQuery(query);

            // 遍历每种类型
            if (rs.next()) {
                typeName = rs.getString("type");
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            // 关闭结果集、声明和连接
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) DataControl.closeData(con);
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return typeName;
    }

    // 获取当前书籍被借出的数量
    // 获取当前书籍被借出的数量
    public static int getNumberofBooksLent(String ISBN) {
        int numberOfBooksLent = 0;
        String sql = "SELECT COUNT(*) AS times_borrowed " +
                "FROM borrow " +
                "WHERE ISBN = ? " +
                "AND actual_return_time IS NULL";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ISBN);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                numberOfBooksLent = rs.getInt("times_borrowed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return numberOfBooksLent;
    }

    public static ArrayList<Borrow> findAllBorrows() {
        ArrayList<Borrow> borrows = new ArrayList<>();
        String sql = "SELECT borrow_id, student_id, ISBN, borrow_time, return_time, actual_return_time FROM borrow";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int borrowId = rs.getInt("borrow_id");
                String studentId = rs.getString("student_id");
                String isbn = rs.getString("ISBN");
                Date borrowTime = rs.getDate("borrow_time");
                Date returnTime = rs.getDate("return_time");
                Date actualReturnTime = rs.getDate("actual_return_time");

                Borrow borrow = new Borrow(borrowId, studentId, isbn, borrowTime, returnTime, actualReturnTime);
                borrows.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return borrows;
    }

    // 根据学生ID查找
    public static ArrayList<Borrow> findAllBorrowsByStudentID(String studentID) {
        ArrayList<Borrow> borrows = new ArrayList<>();
        String sql = "SELECT borrow_id, student_id, ISBN, borrow_time, return_time, actual_return_time " +
                "FROM borrow WHERE student_id = ?";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int borrowId = rs.getInt("borrow_id");
                    String studentId = rs.getString("student_id");
                    String isbn = rs.getString("ISBN");
                    Date borrowTime = rs.getDate("borrow_time");
                    Date returnTime = rs.getDate("return_time");
                    Date actualReturnTime = rs.getDate("actual_return_time");

                    Borrow borrow = new Borrow(borrowId, studentId, isbn, borrowTime, returnTime, actualReturnTime);
                    borrows.add(borrow);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return borrows;
    }

    public static Date findActualReturnTimeByBorrowID(int borrowID) {
        Date actualReturnTime = null;
        String sql = "SELECT actual_return_time FROM borrow WHERE borrow_id = ?";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, borrowID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    actualReturnTime = rs.getDate("actual_return_time");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actualReturnTime;
    }

    // 在借阅表与书籍表中进行查找,返回为Map<String, Integer> 类型,其中String,存储书籍名称, Integer存储该睡觉在表中出现的次数
    public static Map<String, Integer> findAllBookNumInBorrow() {
        Map<String, Integer> bookCounts = new HashMap<>();
        String sql = "SELECT bk.title, COUNT(b.ISBN) AS borrow_count " +
                "FROM borrow b " +
                "JOIN book bk ON b.ISBN = bk.ISBN " +
                "GROUP BY bk.title";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String title = rs.getString("title");
                int count = rs.getInt("borrow_count");
                bookCounts.put(title, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookCounts;
    }

    // 在借阅表与书籍表中通过学生ID进行查找,返回为Map<String, Integer> 类型,其中String,存储书籍名称, Integer存储该睡觉在表中出现的次数
    public static Map<String, Integer> findAllBookNumInBorrowByStudentID(String studentID) {
        Map<String, Integer> bookCounts = new HashMap<>();
        String sql = "SELECT bk.title, COUNT(b.ISBN) AS borrow_count " +
                "FROM borrow b " +
                "JOIN book bk ON b.ISBN = bk.ISBN " +
                "WHERE b.student_id = ? " +
                "GROUP BY bk.title";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String title = rs.getString("title");
                    int count = rs.getInt("borrow_count");
                    bookCounts.put(title, count);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookCounts;
    }

    public static void main(String[] args) {
        String studentID = "suyihang"; // 示例学生ID
        Map<String, Integer> bookNumInBorrowByStudent = findAllBookNumInBorrowByStudentID(studentID);
        bookNumInBorrowByStudent.forEach((title, count) -> {
            System.out.println("Book Title: " + title + ", Borrow Count: " + count);
        });
    }

}
