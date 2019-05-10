package dao;

import java.util.List;

public class Ratings {
        private List<Integer> sellerRating ;
        private List<Integer> buyierRating;
        private String username ;
        private List<Integer> item_list;      
        
        public Ratings(List<Integer> sellerRating, List<Integer> buyierRating, String username, List<Integer> item_list) {
            this.sellerRating = sellerRating;
            this.buyierRating = buyierRating;
            this.username = username;
            this.item_list = item_list;
        }
        
        public Ratings(List<Integer> buyierRating, List<Integer> item_list) {
            this.buyierRating = buyierRating;
            this.item_list = item_list;
        }

        public Ratings(List<Integer> item_list) {
            this.buyierRating = buyierRating;
            this.item_list = item_list;
        }
        
        public List<Integer> getItem_list() {
            return item_list;
        }

        public void setItem_list(List<Integer> item_list) {
            this.item_list = item_list;
        }
        public List<Integer> getSellerRating() {
            return sellerRating;
        }

        public void setSellerRating(List<Integer> sellerRating) {
            this.sellerRating = sellerRating;
        }

        public List<Integer> getBuyierRating() {
            return buyierRating;
        }

        public void setBuyierRating(List<Integer> buyierRating) {
            this.buyierRating = buyierRating;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Ratings(List<Integer> sellerRating, List<Integer> buyierRating, String username) {
            this.sellerRating = sellerRating;
            this.buyierRating = buyierRating;
            this.username = username;
        }

    }