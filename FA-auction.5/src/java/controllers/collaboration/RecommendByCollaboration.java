/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.collaboration;


import dao.AuctionDAO;
import dao.CategoryDAO;
import dao.UserDAO;
import entities.Auction;
import entities.Category;
import entities.User;
import java.io.PrintWriter;
import static java.lang.Math.abs;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import static java.lang.Math.abs;


/**
 *
 * @author lumberjack
 */
public class RecommendByCollaboration {
    String username;
  
    public List<Auction> findRecommendations(int k, String username) {
        UserDAO userDao = new UserDAO();
        CategoryDAO categoryDao = new CategoryDAO();
        AuctionDAO auctionDao = new AuctionDAO();
        
        List<User> users = userDao.getUsers();
        User recomadationUser = userDao.select(username);
        
        List<Category> categories = categoryDao.getCategories();
        List<Auction> allAuctions = auctionDao.getAuctions();
        List<Auction> activeAuctions = auctionDao.getAllActiveAuctions(new Date());
        
        Map<User, Map<Category, Float>> userCategoryMap = new HashMap<>();
        
        Map<User, Map<Auction, Float>> userAuctionMap = new HashMap<>();
        
        Map<Auction,Float> averageRatingMap= new HashMap<>();
        
        Map<User,Float> userSimilMap = new HashMap<>();
        
        List<RecommendedAuction> recommendationList = new ArrayList<>(); 
        
        
        
        for (Auction a : allAuctions) {    
            averageRatingMap.put(a, 0.0f); 
        }
        
        
        
        for (User u : users) {                                    
            userCategoryMap.put(u, new HashMap<Category, Float>());
            userAuctionMap.put(u, new HashMap<Auction, Float>());
            userSimilMap.put(u, 0.0f);
            
            for (Category c : categories) {
                userCategoryMap.get(u).put(c, 0.0f);
            }
            
            for (Auction a : allAuctions) {
                userAuctionMap.get(u).put(a, 0.0f);
            }
            
        }
        
        for (User u : users) {
            List<Category> interestingCategories = categoryDao.getInterestingCategories(u.getUsername());
            
            Map<Category, Float> m =  userCategoryMap.get(u);
            
            for (Category c : interestingCategories) {
                m.put(c, m.get(c)+1);
                
            }
        }
        
        
        
        // Rating - Bias of each user for all activeauctions
        for (User u : users) {
            Map<Category, Float> m =  userCategoryMap.get(u);
            Map<Auction, Float> auct = userAuctionMap.get(u);
            
            if(u.getUsername().compareTo(username)==0) continue;
            
            for (Auction a : activeAuctions) {
                
                Collection<Category> ca = a.getCategoryCollection();
                List<Category> itemCategories;
                
                if (ca instanceof List)
                   itemCategories = (List)ca;
                else
                    itemCategories = new ArrayList(ca);
                
                
                float avg = 0;
                for (Category c : itemCategories) {
                    avg += m.get(c);
                }
                avg /= itemCategories.size();
                
                auct.put(a, avg);
            }   
        }
        
        // Average rating of all users for each auction 
        for(Auction a : allAuctions ){
            
            Float avgR = 0.0f;
            
            
            for(User u: users){
                Map<Auction, Float> auct = userAuctionMap.get(u);
                avgR += auct.get(a); 
            }
            
            avgR /= users.size();
            averageRatingMap.put(a, avgR);
        }
        
        
        for(User u: users){
            Map<Auction, Float> auction = userAuctionMap.get(u);
            
            for(Auction a : allAuctions ){
                
                Float rate = auction.get(a);
                Float bias = averageRatingMap.get(a);
                Float temp = rate -bias;
                auction.put(a,abs(temp));
            }
        }
        
        
        //Similarity of each user with the recomadating user 
        for(User u : users){
            Map<Category, Float> recomendedUserRateMap = userCategoryMap.get(recomadationUser); 
            Map<Category, Float> cRateMap = userCategoryMap.get(u);
            
            if(u.getUsername().compareTo(username)==0) continue;
            
            Integer counter=0;
            Float simil = 0.0f;
            for(Category c: categories){
                Float recUserRate = recomendedUserRateMap.get(c);
                Float compareToUser = cRateMap.get(c);
                if(recUserRate == 0.0f && compareToUser==0.0f){
                    continue;
                }
                counter ++;
                Float temp = recUserRate - compareToUser;  
                simil+= abs(temp);
            }
            
            if(counter == 0) return new ArrayList<>();
            
            simil /= counter;
            simil = 1/simil;
            userSimilMap.put(u, simil);
        }
        
        
        for(Auction a: activeAuctions){
            
            Float collRate=0.0f;
            Float similAbsolute = 0.0f;
            
            
            for(User u: users){
               collRate += userSimilMap.get(u)* userAuctionMap.get(u).get(a);
               similAbsolute += userSimilMap.get(u);
            }
            
            Float collaborationRating = collRate/similAbsolute + averageRatingMap.get(a);
            
            RecommendedAuction ra = new RecommendedAuction(a,collaborationRating); 
            recommendationList.add(ra);
        }
        
       
//        Auction a = new Auction();
//        recommendationList.add(new RecommendedAuction(a,5.8f));
//        recommendationList.add(new RecommendedAuction(a,3.3f));
//        recommendationList.add(new RecommendedAuction(a,6.5f));
//        recommendationList.add(new RecommendedAuction(a,2.1f));
//        recommendationList.add(new RecommendedAuction(a,4.9f));
       
        Collections.sort(recommendationList,new RecommendedAuction());
        
        List<Auction> recommended = new ArrayList<>();
        for(int i=0; i<k; i++){
           if(i==recommendationList.size()) break; 
           recommended.add(recommendationList.get(i).getA()); 
        }
        // debug:
        //for (User u : users) {                                    
            //for (Category c : categories) {
                //out.println(u.getUsername() + " " + c.getName() + " " + userCategoryMap.get(u).get(c) +"<BR/>");
            //}
        //}
        
        return recommended;
    }
    
    
    private class RecommendedAuction implements Comparator<RecommendedAuction>, Comparable<RecommendedAuction> {
        
        private Auction a;
        private float rating;

        public RecommendedAuction() {
        }

        

        public RecommendedAuction(Auction a, float rating) {
            this.a = a;
            this.rating = rating;
        }

        public Auction getA() {
            return a;
        }

        public float getRating() {
            return rating;
        }
        
        

        @Override
        public int compare(RecommendedAuction o1, RecommendedAuction o2) {
            Float com = o1.getRating() - o2.getRating();
            if(com<=0.000001 && com>=-0.000001){
                return 0;
            }
            else if(com > 0 ) return -1;
            else return 1;
        }

        @Override
        public int compareTo(RecommendedAuction o) {
            return (this.getA().getItemId()).compareTo(o.getA().getItemId());
        }
    
    }
    
    
    
}
