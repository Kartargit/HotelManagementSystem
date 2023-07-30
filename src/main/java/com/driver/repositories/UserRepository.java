package com.driver.repositories;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class UserRepository {
    HashMap<String, Hotel> hotelDb = new HashMap<>();
    HashMap<Integer, User> userDb = new HashMap<>();
    HashMap<String, Booking> bookingDb = new HashMap<>();
    HashMap<Integer,Integer> userBooking = new HashMap<>();
    public String addHotels(Hotel hotel){
        if(hotel==null||hotel.getHotelName()==null||hotelDb.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }
        else{
            hotelDb.put(hotel.getHotelName(),hotel);
            return "SUCCESS";
        }
    }
    public void addUser(Integer adhaarNo, User user){
       userDb.put(adhaarNo,user);
    }
public HashMap<String,Hotel> getHotelDb(){
        return hotelDb;
}
    public String getHotelWithMostFacilities() {
        int facilityCount = 0;
        String hotelName = "zzzz" ;
        for(String name:hotelDb.keySet()){
            if(hotelDb.get(name).getFacilities().size()>facilityCount){
                hotelName = name;
                facilityCount = hotelDb.get(name).getFacilities().size();
            }
            else if (hotelDb.get(name).getFacilities().size()==facilityCount) {
                if(hotelName.compareTo(name)>0){
                    hotelName = name;
                }
            }
        }
        if(facilityCount==0)return "";
        return hotelName;
    }

    public HashMap<String, Booking> getBookingDb() {
        return bookingDb;
    }
    public void addBooking(String bookingId,Booking booking,Integer adhaarNo){
        bookingDb.put(bookingId,booking);
        userBooking.put(adhaarNo,userBooking.getOrDefault(adhaarNo,0)+1);
    }
    public int getBookingList(Integer aadharNo){
        return userBooking.get(aadharNo);
    }

}
