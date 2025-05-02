package websitePackage;

import java.sql.SQLException;

public class MainClass {
    public static void main(String[] args){

        try {
            Business business=new Business("My shop",1);
            DatabaseConnection.insertBusiness(business);

//            SalesSystem.createNewBusiness();

            //  CLIENT INSERTION
//            SalesSystem.insertClient("Zizi","Gooloo","09190000000","zizi@gmail.com",
//                    CitiesAndStates.City.TEHRAN.toString(),"thisIsZiziGooloo");
//            SalesSystem.insertClient("Meli","Blue","09190000001","meli@gmail.com",
//                    CitiesAndStates.City.TEHRAN.toString(),"thisIsMeliBlue");
//            SalesSystem.insertClient("Elmira","Panahi","09190000002","elmira.oktw.2003@gmail.com",
//                    CitiesAndStates.City.TEHRAN.toString(),"elmiraaaaa");


            //  ADMIN INSERTION
//            SalesSystem.insertAdmin("Elmira","Panahi","09190000002",
//                    "elmira.oktw.2003@gmail.com","elmiraaaaa");
//            SalesSystem.insertAdmin("Melika","Borhani","09190000003",
//                    "melika@gmail.com","melikaaaaa");
//            SalesSystem.insertAdmin("Zeinab","Ebadi","09190000004",
//                    "zeinab@gmail.com","zeinabbbbb");


            //  LOGGING IN & INSERTING POST & SEEING YOUR OWN POSTS
//            SalesSystem.logInClientWithEmail("elmira.oktw.2003@gmail.com");
//            SalesSystem.addNewPost();
//            SalesSystem.seeYouOwnPosts();

            //LOGGING IN AND INSERTING A RESIDENTAL POST
//            SalesSystem.logInClientWithPassword("09190000002","incorrectPassword");
//            SalesSystem.logInClientWithPassword("09190000002","elmiraaaaa");
//            System.out.println("Client On Board: "+SalesSystem.clientOnBoard);
//            SalesSystem.addNewPost();
//            System.out.println("----------------");
//            SalesSystem.seeYouOwnPosts();

            //  CLEAR ALL DATABASE
//        DatabaseConnection.clearAllDatabase();

            //  LOGIN CLIENT USING REDIS
//            long startTime = System.nanoTime();
//            SalesSystem.clientOnBoard=JedisDatabaseConnection.getClient("09190000002");
//            long endTime = System.nanoTime();
//            System.out.println("In milliseconds: "+(double)(endTime-startTime)/1_000_000);
//
//            long startTime1 = System.nanoTime();
//            SalesSystem.clientOnBoard=JedisDatabaseConnection.getClient("09190000002");
//            long endTime1 = System.nanoTime();
//            System.out.println("In milliseconds: "+(double)(endTime1-startTime1)/1_000_000);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
