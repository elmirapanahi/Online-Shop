package websitePackage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {
    private static final String URL="jdbc:mysql://localhost:3306/databaseFinalProject";
    private static final String user="Elmira";
    private static final String password="elmiraoktw";

    // Method to establish a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,user,password);
    }


    public static void insertClient(String firstName,String lastName,String phoneNumber,String email,String city,Timestamp registrationTime,String password) throws SQLException{
        String query="INSERT INTO Client (firstName,lastName,phoneNumber,email,city,registerationTime,password,isActive)" +
                     "VALUES (?,?,?,?,?,?,?,?)";

        try (Connection connection=getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query)){
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, phoneNumber);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, city);
            preparedStatement.setTimestamp(6, registrationTime);
            preparedStatement.setString(7, password);
            preparedStatement.setBoolean(8, true);

            preparedStatement.executeUpdate();
        }
    }

    public static Client getClient(String email,String phoneNumber){
        Client client=null;
        String query="";
        if (phoneNumber.equals("")){
            query="SELECT * FROM client WHERE email=?";
        } else if (email.equals("")){
            query="SELECT * FROM client WHERE phoneNumber=?";
        }
        try(Connection connection=getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query)){
            if(phoneNumber.equals("")){
                preparedStatement.setString(1,email);
            }else if(email.equals("")){
                preparedStatement.setString(1,phoneNumber);
            }
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                if(resultSet.next()){
                    int clientID=resultSet.getInt("clientID");
                    String firstName=resultSet.getString("firstName");
                    String lastName=resultSet.getString("lastName");
                    String phone=resultSet.getString("phoneNumber");
                    String mail=resultSet.getString("email");
                    String city=resultSet.getString("city");
                    Timestamp registrationTime=resultSet.getTimestamp("registerationTime");
                    String password=resultSet.getString("password");
                    boolean isActive = resultSet.getBoolean("isActive");

                    client=new Client(firstName, lastName, mail, phone, password);
                    client.setClientID(clientID);
                    client.setCity(city);
                    client.setRegistrationTime(registrationTime);
                    client.setActive(isActive);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }

    public static void updateClient(Client lastClient,Client updatedClient){
        //Everything except ID and phone number and registration time can change
        String query="UPDATE Client " +
                "SET firstName = ?," +
                "    lastName = ?," +
                "    email = ?," +
                "    city = ?," +
                "    password = ?," +
                "    isActive = ? " +
                "WHERE phoneNumber = ?;";

        try (Connection connection=getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query)){
            preparedStatement.setString(1, updatedClient.getFirstName());
            preparedStatement.setString(2, updatedClient.getLastName());
            preparedStatement.setString(3, updatedClient.getEmail());
            preparedStatement.setString(4, updatedClient.getCity());
            preparedStatement.setString(5, updatedClient.getPassword());
            preparedStatement.setBoolean(6, updatedClient.isActive());
            preparedStatement.setString(7, lastClient.getPhoneNumber());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertAdmin(String firstName,String lastName,String phoneNumber,String email,String password)throws SQLException {
        String query = "INSERT INTO admin (firstName, lastName, phone_number, email, password) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.setString(3,phoneNumber);
            preparedStatement.setString(4,email);
            preparedStatement.setString(5,password);

            preparedStatement.executeUpdate();
        }
    }

    public static Admin getAdmin(String email,String phoneNumber){
        Admin admin=null;
        String query="";
        if (phoneNumber.equals("")){
            query="SELECT * FROM admin WHERE email=?";
        } else if (email.equals("")){
            query="SELECT * FROM admin WHERE phone_number=?";
        }
        try(Connection connection=getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query)){
            if(phoneNumber.equals("")){
                preparedStatement.setString(1,email);
            } else if(email.equals("")){
                preparedStatement.setString(1,phoneNumber);
            }
            try (ResultSet resultSet=preparedStatement.executeQuery()){
                if (resultSet.next()){
                    int adminID=resultSet.getInt("adminID");
                    String firstName=resultSet.getString("firstName");
                    String lastName=resultSet.getString("lastName");
                    String phone=resultSet.getString("phone_number");
                    String mail=resultSet.getString("email");
                    int numberOfRejects=resultSet.getInt("number_of_rejects");
                    int numberOfChecks=resultSet.getInt("number_of_checks");
                    String password=resultSet.getString("password");

                    admin=new Admin(firstName,lastName,phone,mail);
                    admin.setAdminID(adminID);
                    admin.setNumberOfRejects(numberOfRejects);
                    admin.setNumberOfChecks(numberOfChecks);
                    admin.setPassword(password);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return admin;
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

        } catch (SQLException|JsonProcessingException e){
            e.printStackTrace();
        }
    }

    public static void insertVehiclePost(Vehicle vehicle){
        String query="INSERT INTO Vehicle (vehicleRun,hasBodyBeenCrushed,inEngineDamaged," +
                "make,year,isNew,gearBoxType,color,fuleType,postID) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";
        insertPost((Post) vehicle);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1,vehicle.getVehicleRun());
            preparedStatement.setBoolean(2, vehicle.isHasBodyBeenCrushed());
            preparedStatement.setBoolean(3, vehicle.isEngineDamaged());
            preparedStatement.setString(4,vehicle.getMake());
            preparedStatement.setInt(5,vehicle.getYear());
            preparedStatement.setBoolean(6, vehicle.isNew());
            preparedStatement.setString(7, vehicle.getGearBoxType());
            preparedStatement.setString(8, vehicle.getColor());
            preparedStatement.setString(9,vehicle.getFuelType());
            preparedStatement.setInt(10,vehicle.getPostID());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void insertPropertyPost(Property property){
        String query="INSERT INTO Property (postID,area,isForRent,deposit,monthlyRent) "+
                "VALUES (?,?,?,?,?)";
        insertPost((Post) property);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1,property.getPostID());
            preparedStatement.setInt(2,property.getArea());
            preparedStatement.setBoolean(3, property.isForRent());
            preparedStatement.setDouble(4,property.getDeposit());
            preparedStatement.setDouble(5,property.getMonthlyRent());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertClothsPost(Cloths cloths){
        String query="INSERT INTO Cloths (postID,isNew,femalOrMale) "+
                "VALUES (?,?,?)";
        insertPost((Post) cloths);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1,cloths.getPostID());
            preparedStatement.setBoolean(2, cloths.isNew());
            preparedStatement.setBoolean(3, cloths.isFemaleOrMale());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertAnimalPost(Animal animal){
        String query="INSERT INTO Property (postID,age,gender,isHealthy) "+
                "VALUES (?,?,?,?)";
        insertPost((Post) animal);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1,animal.getPostID());
            preparedStatement.setInt(2,animal.getAge());
            preparedStatement.setBoolean(3,animal.isGender());
            preparedStatement.setBoolean(4,animal.isHealthy());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertDigitalDevicePost(DigitalDevice digitalDevice){
        String query="INSERT INTO DigitalDevice(postID,brand,isNew,color) "+
                "VALUES (?,?,?,?)";
        insertPost((Post) digitalDevice);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1,digitalDevice.getPostID());
            preparedStatement.setString(2,digitalDevice.getBrand());
            preparedStatement.setBoolean(3,digitalDevice.isNew());
            preparedStatement.setString(4,digitalDevice.getColor());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertPersonalAndHomeStuffPost(PersonalAndHomeStuff personalAndHomeStuff){
        String query="INSERT INTO PersonalAndHomeStuff(postID,isNew) "+
                "VALUES (?,?)";
        insertPost((Post) personalAndHomeStuff);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1,personalAndHomeStuff.getPostID());
            preparedStatement.setBoolean(2,personalAndHomeStuff.isNew());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertOthersPost(Others others){
        String query="INSERT INTO DigitalDevice(postID) "+
                "VALUES (?)";
        insertPost((Post) others);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1,others.getPostID());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertResidentialPost(Residential residential){
        String query="INSERT INTO Residental (postID,hasBalcony,numOfParkingLots" +
                ",hasDepot,builtYear,numOfRooms,numOfBathrooms) "+
                "VALUES (?,?,?,?,?,?,?)";
        insertPropertyPost((Property) residential);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1,residential.getPostID());
            preparedStatement.setBoolean(2,residential.isHasBalcony());
            preparedStatement.setInt(3,residential.getNumberOfParkingLots());
            preparedStatement.setBoolean(4,residential.isHasDepot());
            preparedStatement.setInt(5,residential.getBuiltYear());
            preparedStatement.setInt(6,residential.getNumberOfRooms());
            preparedStatement.setInt(7,residential.getNumberOfBathrooms());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertCommercialPost(Commercial commercial){
        String query="INSERT INTO Commercial(postID,builtYear,numOfRooms) "+
                "VALUES (?,?,?)";
        insertPropertyPost((Property) commercial);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1,commercial.getPostID());
            preparedStatement.setInt(2,commercial.getBuiltYear());
            preparedStatement.setInt(3,commercial.getNumberOfRooms());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertLandPost(Land land){
        String query="INSERT INTO Land(postID,hasPermissionForConstruction) "+
                "VALUES (?,?)";
        insertPropertyPost((Property) land);
        try (Connection connection=getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1,land.getPostID());
            preparedStatement.setBoolean(2,land.isHasPermissionForConstruction());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertApartmentPost(Apartment apartment){
        String query="INSERT INTO Apartment(postID,floorNumber,hasElevator,numOfAprsPerFloor) "+
                "VALUES (?,?,?,?)";
        insertResidentialPost((Residential) apartment);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1,apartment.getPostID());
            preparedStatement.setInt(2,apartment.getFloorNumber());
            preparedStatement.setBoolean(3,apartment.isHasElevator());
            preparedStatement.setInt(4,apartment.getNumberOfAprsPerFloor());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertHousePost(House house){
        String query="INSERT INTO House(postID,numOfFloors,areaPerFloor) "+
                "VALUES (?,?,?)";
        insertResidentialPost((Residential) house);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1,house.getPostID());
            preparedStatement.setInt(2,house.getNumberOfFloors());
            preparedStatement.setInt(3,house.getAreaPerFloor());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Post> getAllPosts() {
        ArrayList<Post> posts = new ArrayList<>();

        String query = "SELECT p.postID, p.adminNote, p.price, p.title, p.region, p.tag, p.number_of_views, " +
                "p.isActive, p.insertionTime, p.description, p.photos, p.clientID, p.businessID, " +
                "v.vehicleRun, v.hasBodyBeenCrushed, v.isEngineDamaged, v.make, v.year, v.isNew, v.gearBoxType, v.color, v.fuelType, " +
                "pr.area, pr.isForRent, pr.deposit, pr.monthlyRent, " +
                "c.isNew, c.femaleOrMale, " +
                "a.age, a.isHealthy, a.gender, " +
                "d.brand, d.isNew, d.color, " +
                "ph.isNew, " +
                "o.postID, " +
                "re.hasBalcony, re.numOfParkingLots, re.hasDepot, re.builtYear, re.numOfRooms, re.numOfBathrooms, " +
                "co.builtYear, co.numOfRooms, " +
                "l.hasPermissionForConstruction, " +
                "ap.floorNumber, ap.hasElevator, ap.numOfAprsPerFloor, " +
                "h.numOfFloors, h.areaPerFloor " +
                "FROM post p " +
                "LEFT JOIN vehicle v ON p.postID = v.postID " +
                "LEFT JOIN property pr ON p.postID = pr.postID " +
                "LEFT JOIN cloths c ON p.postID = c.postID " +
                "LEFT JOIN animal a ON p.postID = a.postID " +
                "LEFT JOIN digitaldevice d ON p.postID = d.postID " +
                "LEFT JOIN personalandhomestuff ph ON p.postID = ph.postID " +
                "LEFT JOIN others o ON p.postID = o.postID " +
                "LEFT JOIN residental re ON p.postID = re.postID " +
                "LEFT JOIN commercial co ON p.postID = co.postID " +
                "LEFT JOIN land l ON p.postID = l.postID " +
                "LEFT JOIN apartment ap ON p.postID = ap.postID " +
                "LEFT JOIN house h ON p.postID = h.postID )";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Post post = new Post(resultSet.getDouble("price"),resultSet.getString("title"),resultSet.getString("region"),
                        resultSet.getString("tag"),resultSet.getString("description"),resultSet.getInt("clientID"),
                        Post.jsonToPhotoUrls(resultSet.getString("photos")));
                post.setPostID(resultSet.getInt("postID"));
                post.setAdminNote(resultSet.getString("adminNote"));
                post.setNumberOfViews(resultSet.getInt("number_of_views"));
                post.setActive(resultSet.getBoolean("isActive"));
                post.setInsertionTime(resultSet.getTimestamp("insertionTime"));
                post.setBusinessID(resultSet.getInt("businessID"));

                // Set details based on the category
                if (resultSet.getInt("vehicleRun") != 0) {
                    Vehicle vehicle = new Vehicle(resultSet.getString("make"),resultSet.getInt("year"),resultSet.getInt("vehicleRun"),
                            post.getPrice(),post.getTitle(),post.getRegion(),post.getTag(),post.getDescription(),post.getClientID(),
                            post.getPhotos());
                    vehicle.setHasBodyBeenCrushed(resultSet.getBoolean("hasBodyBeenCrushed"));
                    vehicle.setEngineDamaged(resultSet.getBoolean("isEngineDamaged"));
                    vehicle.setNew(resultSet.getBoolean("isNew"));
                    vehicle.setGearBoxType(resultSet.getString("gearBoxType"));
                    vehicle.setColor(resultSet.getString("color"));
                    vehicle.setFuelType(resultSet.getString("fuelType"));
                    posts.add(vehicle);
                } else if (resultSet.getInt("area") != 0) {
                    Property property = new Property(resultSet.getInt("area"),resultSet.getBoolean("isForRent"),
                            post.getPrice(),post.getTitle(),post.getRegion(),post.getTag(),post.getDescription(),post.getClientID(),
                            post.getPhotos());
                    property.setDeposit(resultSet.getDouble("deposit"));
                    property.setMonthlyRent(resultSet.getDouble("monthlyRent"));
                    posts.add(property);
                } else if (resultSet.getString("femaleOrMale") != null) {
                    Cloths cloths = new Cloths(resultSet.getBoolean("isNew"),resultSet.getBoolean("femaleOrMale"),
                            post.getPrice(),post.getTitle(),post.getRegion(),post.getTag(),post.getDescription(),post.getClientID(),
                            post.getPhotos());
                    posts.add(cloths);
                } else if (resultSet.getInt("age") != 0) {
                    Animal animal = new Animal(resultSet.getBoolean("isHealthy"),post.getPrice(),post.getTitle(),post.getRegion(),
                            post.getTag(),post.getDescription(),post.getClientID(),post.getPhotos());
                    animal.setAge(resultSet.getInt("age"));
                    animal.setGender(resultSet.getBoolean("gender"));
                    posts.add(animal);
                } else if (resultSet.getString("brand") != null) {
                    DigitalDevice digitalDevice = new DigitalDevice(resultSet.getBoolean("isNew"),post.getPrice(),post.getTitle(),
                            post.getRegion(),post.getTag(),post.getDescription(),post.getClientID(),post.getPhotos());
                    digitalDevice.setBrand(resultSet.getString("brand"));
                    digitalDevice.setColor(resultSet.getString("color"));
                    posts.add(digitalDevice);
                } else if (resultSet.getInt("isNew") != 0) {
                    PersonalAndHomeStuff personalAndHomeStuff = new PersonalAndHomeStuff(resultSet.getBoolean("isNew"),post.getPrice(),
                            post.getTitle(),post.getRegion(),post.getTag(),post.getDescription(),post.getClientID(),post.getPhotos());
                    posts.add(personalAndHomeStuff);
                } else if (resultSet.getInt("hasBalcony") != 0) {
                    Residential residental = new Residential(resultSet.getInt("area"),resultSet.getBoolean("isForRent"),
                            post.getPrice(),post.getTitle(),post.getRegion(),post.getTag(),post.getDescription(),post.getClientID(),post.getPhotos());
                    residental.setHasBalcony(resultSet.getBoolean("hasBalcony"));
                    residental.setNumberOfParkingLots(resultSet.getInt("numOfParkingLots"));
                    residental.setHasDepot(resultSet.getBoolean("hasDepot"));
                    residental.setBuiltYear(resultSet.getInt("builtYear"));
                    residental.setNumberOfRooms(resultSet.getInt("numOfRooms"));
                    residental.setNumberOfBathrooms(resultSet.getInt("numOfBathrooms"));
                    posts.add(residental);
                } else if (resultSet.getInt("builtYear") != 0) {
                    Commercial commercial = new Commercial(resultSet.getInt("area"),resultSet.getBoolean("isForRent"),
                            post.getPrice(),post.getTitle(),post.getRegion(),post.getTag(),post.getDescription(),post.getClientID(),post.getPhotos());
                    commercial.setBuiltYear(resultSet.getInt("builtYear"));
                    commercial.setNumberOfRooms(resultSet.getInt("numOfRooms"));
                    posts.add(commercial);
                } else if (resultSet.getInt("hasPermissionForConstruction") != 0) {
                    Land land = new Land(resultSet.getInt("area"),resultSet.getBoolean("isForRent"),post.getPrice(),
                            post.getTitle(),post.getRegion(),post.getTag(),post.getDescription(),post.getClientID(),post.getPhotos());
                    land.setHasPermissionForConstruction(resultSet.getBoolean("hasPermissionForConstruction"));
                    posts.add(land);
                } else if (resultSet.getInt("floorNumber") != 0) {
                    Apartment apartment = new Apartment(resultSet.getInt("area"),resultSet.getBoolean("isForRent"),post.getPrice(),
                            post.getTitle(),post.getRegion(),post.getTag(),post.getDescription(),post.getClientID(),post.getPhotos());
                    apartment.setFloorNumber(resultSet.getInt("floorNumber"));
                    apartment.setHasElevator(resultSet.getBoolean("hasElevator"));
                    apartment.setNumberOfAprsPerFloor(resultSet.getInt("numOfAprsPerFloor"));
                    posts.add(apartment);
                } else if (resultSet.getInt("numOfFloors") != 0) {
                    House house = new House(resultSet.getInt("area"),resultSet.getBoolean("isForRent"), post.getPrice(),post.getTitle(),
                            post.getRegion(),post.getTag(),post.getDescription(),post.getClientID(),post.getPhotos());
                    house.setNumberOfFloors(resultSet.getInt("numOfFloors"));
                    house.setAreaPerFloor(resultSet.getInt("areaPerFloor"));
                    posts.add(house);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return posts;
    }

    public static void deletePost(int postID){
        String sql="DELETE FROM post WHERE postID = ?";

        try (Connection connection=getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, postID);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Report> getAllReports(){
        ArrayList<Report> reports = new ArrayList<>();
        String sql = "SELECT reportID, content, title, postID, clientID FROM report";

        try (Connection connection=getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(sql);
             ResultSet resultSet=preparedStatement.executeQuery()){

            while (resultSet.next()){
                int reportID=resultSet.getInt("reportID");
                String content=resultSet.getString("content");
                String title=resultSet.getString("title");
                int postID=resultSet.getInt("postID");
                int clientID=resultSet.getInt("clientID");

                Report report=new Report(title, postID, clientID);
                report.setReportID(reportID);
                report.setContent(content);

                reports.add(report);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return reports;
    }

    public static void clearAllDatabase(){
        String[] sqls={
                "SET FOREIGN_KEY_CHECKS = 0;",
                "TRUNCATE TABLE admin;",
                "TRUNCATE TABLE apartment;",
                "TRUNCATE TABLE house;",
                "TRUNCATE TABLE residental;",
                "TRUNCATE TABLE commercial;",
                "TRUNCATE TABLE land;",
                "TRUNCATE TABLE property;",
                "TRUNCATE TABLE vehicle;",
                "TRUNCATE TABLE cloths;",
                "TRUNCATE TABLE animal;",
                "TRUNCATE TABLE digitaldevice;",
                "TRUNCATE TABLE personalandhomestuff;",
                "TRUNCATE TABLE others;",
                "TRUNCATE TABLE post;",
                "TRUNCATE TABLE client;",
                "TRUNCATE TABLE report;",
                "TRUNCATE TABLE business;",
                "SET FOREIGN_KEY_CHECKS = 1;"
        };

        try (Connection connection=getConnection()){
            for (String sql:sqls) {
                try (PreparedStatement preparedStatement=connection.prepareStatement(sql)){
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }



}
