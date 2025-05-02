package websitePackage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;
import com.google.gson.Gson;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JedisDatabaseConnection{
    private static final String URL="jdbc:mysql://localhost:3306/databaseFinalProject";
    private static final String USER="Elmira";
    private static final String PASSWORD="elmiraoktw";
//    private static JedisPool jedisPool=new JedisPool(new JedisPoolConfig(),"localhost",6379);


    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }

    public static String serializeClient(Client client) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(client);
    }

    public static Client deserializeClient(String clientJSON) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(clientJSON, Client.class);
    }

    public static String serializeAdmin(Admin admin) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(admin);
    }

    public static Admin deserializeAdmin(String adminJSON) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(adminJSON, Admin.class);
    }

    public static String serializePost(Post post) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(post);
    }

    public static Post deserializePost(String postJSON) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(postJSON, Post.class);
    }

    public static String serializePosts(ArrayList<Post> posts) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(posts);
    }

    public static ArrayList<Post> deserializePosts(String postsJSON) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<ArrayList<Post>> typeRef = new TypeReference<ArrayList<Post>>() {};
        return mapper.readValue(postsJSON, typeRef);
    }

    public static String serializeReport(Report report) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(report);
    }

    public static Report deserializeReport(String reportJSON) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(reportJSON,Report.class);
    }






    public static Client getClient(String phoneNumber){
        Client client=null;
        try(Jedis jedis=RedisConfig.getResource()){
            String key="client:"+phoneNumber;
            String cachedClientJSON=jedis.get(key);
            if(cachedClientJSON!=null){
                client = deserializeClient(cachedClientJSON);
            } else{
                client=DatabaseConnection.getClient("", phoneNumber);
                if (client != null){
                    jedis.set(key,serializeClient(client));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return client;
    }

    public static Admin getAdmin(String phoneNumber){
//        Admin admin = null;
//        String query = "";
//        String key = "";
//        query = "SELECT * FROM admin WHERE phone_number=?";
//        key = "admin:phoneNumber:" + phoneNumber;
//        try (Jedis jedis = RedisConfig.getResource();
//             Connection connection=getConnection();
//             PreparedStatement preparedStatement=connection.prepareStatement(query)){
//            preparedStatement.setString(1,phoneNumber);
//            try (ResultSet resultSet=preparedStatement.executeQuery()){
//                if (resultSet.next()){
//                    int adminID=resultSet.getInt("adminID");
//                    String firstName=resultSet.getString("firstName");
//                    String lastName=resultSet.getString("lastName");
//                    String phone=resultSet.getString("phone_number");
//                    String mail=resultSet.getString("email");
//                    int numberOfRejects=resultSet.getInt("number_of_rejects");
//                    int numberOfChecks=resultSet.getInt("number_of_checks");
//                    String password=resultSet.getString("password");
//
//                    admin = new Admin(firstName,lastName,phone,mail);
//                    admin.setAdminID(adminID);
//                    admin.setNumberOfRejects(numberOfRejects);
//                    admin.setNumberOfChecks(numberOfChecks);
//                    admin.setPassword(password);
//
//                    // Cache the admin in Redis for future requests
//                    jedis.set(key,serializeAdmin(admin));
//                }
//            } catch (JsonProcessingException e){
//                e.printStackTrace();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return admin;
        Admin admin=null;
        try(Jedis jedis=RedisConfig.getResource()){
            String key="admin:"+phoneNumber;
            String cachedClientJSON=jedis.get(key);
            if(cachedClientJSON!=null){
                admin=deserializeAdmin(cachedClientJSON);
            } else{
                admin=DatabaseConnection.getAdmin("", phoneNumber);
                if (admin != null){
                    jedis.set(key,serializeAdmin(admin));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return admin;

    }

    public static ArrayList<Post> getAllPosts(){
        ArrayList<Post> posts = new ArrayList<>();
        try (Jedis jedis = RedisConfig.getResource()) {
            String pattern = "post*";
            Set<String> keys = jedis.keys(pattern);
            if(!keys.isEmpty()) {
                for (String key : keys) {
                    String cachedPostJSON = jedis.get(key);
                    if (cachedPostJSON != null) {
                        Post post = deserializePost(cachedPostJSON);
                        posts.add(post);
                    }
                }
            }else{
                posts = DatabaseConnection.getAllPosts();
                if (!posts.isEmpty()) {
                    for (Post post : posts) {
                        String postKey = "post" + post.getPostID();
                        jedis.set(postKey, serializePost(post));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public static ArrayList<Report> getAllReports(){
        ArrayList<Report> reports = new ArrayList<>();
        try (Jedis jedis = RedisConfig.getResource()) {
            String pattern = "report*";
            Set<String> keys = jedis.keys(pattern);
            if(!keys.isEmpty()) {
                for (String key : keys) {
                    String cachedPostJSON = jedis.get(key);
                    if (cachedPostJSON != null) {
                        Report report = deserializeReport(cachedPostJSON);
                        reports.add(report);
                    }
                }
            }else{
                reports = DatabaseConnection.getAllReports();
                if (!reports.isEmpty()) {
                    for (Report report : reports) {
                        String reportKey = "post" + report.getPostID();
                        jedis.set(reportKey, serializeReport(report));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reports;
    }



    public static void clearRedisCache(){
        try (Jedis jedis=RedisConfig.getResource()) {
            jedis.flushAll();
            System.out.println("Redis cache cleared successfully.");
        } catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void insertPost(Post post){
        String query = "INSERT INTO post (price,title,region,tag,isActive," +
                " insertionTime, description, photos, clientID, businessID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){
            ArrayList<String> imageUrls=new ArrayList<>();
            for (String image:post.getPhotos()){
                imageUrls.add(image);
            }
            ObjectMapper mapper=new ObjectMapper();
            String json=mapper.writeValueAsString(imageUrls);
            preparedStatement.setDouble(1,post.getPrice());
            preparedStatement.setString(2,post.getTitle());
            preparedStatement.setString(3,post.getRegion());
            preparedStatement.setString(4,post.getTag());
            preparedStatement.setBoolean(5,post.isActive());
            preparedStatement.setTimestamp(6,post.getInsertionTime());
            preparedStatement.setString(7,post.getDescription());
            preparedStatement.setString(8,json);
            preparedStatement.setInt(9,post.getClientID());
            preparedStatement.setInt(10,post.getBusinessID());

            preparedStatement.executeUpdate();

            try(Jedis jedis=RedisConfig.getResource()){
                String key="post:"+post.getPostID();
                String cachedClientJSON=jedis.get(key);
                if(cachedClientJSON!=null){
                    jedis.del(key);
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        } catch (SQLException|JsonProcessingException e){
            e.printStackTrace();
        }
    }

    public static void insertBusiness(Business business){
        String sql="INSERT INTO business (category, name, address, registerNumber, clientID) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection=getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(sql)){

            preparedStatement.setString(1, business.getCategory());
            preparedStatement.setString(2, business.getName());
            preparedStatement.setString(3, business.getAddress());
            preparedStatement.setInt(4, business.getRegisterNumber());
            preparedStatement.setInt(5, business.getClientID());

            preparedStatement.executeUpdate();

            try(Jedis jedis=RedisConfig.getResource()){
                String key="business:"+business.getBusinessID();
                String cachedClientJSON=jedis.get(key);
                if(cachedClientJSON!=null){
                    jedis.del(key);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertReport(Report report){
        String sql="INSERT INTO report (content, title, postID, clientID) VALUES (?, ?, ?, ?)";

        try (Connection connection=getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(sql)){

            preparedStatement.setString(1, report.getContent());
            preparedStatement.setString(2, report.getTitle());
            preparedStatement.setInt(3, report.getPostID());
            preparedStatement.setInt(4, report.getClientID());

            preparedStatement.executeUpdate();

            try(Jedis jedis=RedisConfig.getResource()){
                String key="report:"+report.getReportID();
                String cachedClientJSON=jedis.get(key);
                if(cachedClientJSON!=null){
                    jedis.del(key);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setPostActivenessByAdmin(int postID,boolean active,String adminNote,int adminID){
        String updatePostSQL="UPDATE post SET isActive = ?, adminNote = IF(? = false, ?, adminNote) WHERE postID = ?";
        String updateAdminSQL = "UPDATE admin SET number_of_checks = number_of_checks + 1, " +
                "number_of_rejects = IF(? = false, number_of_rejects + 1, number_of_rejects) WHERE adminID = ?";

        try (Connection connection=getConnection();
             PreparedStatement updatePostStmt=connection.prepareStatement(updatePostSQL);
             PreparedStatement updateAdminStmt=connection.prepareStatement(updateAdminSQL)){

            updatePostStmt.setBoolean(1, active);
            updatePostStmt.setBoolean(2, active);
            updatePostStmt.setString(3, adminNote);
            updatePostStmt.setInt(4, postID);
            updatePostStmt.executeUpdate();

            updateAdminStmt.setBoolean(1, active);
            updateAdminStmt.setInt(2, adminID);
            updateAdminStmt.executeUpdate();

            try(Jedis jedis=RedisConfig.getResource()){
                String key="post:"+postID;
                String cachedClientJSON=jedis.get(key);
                if(cachedClientJSON!=null){
                    jedis.del(key);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

