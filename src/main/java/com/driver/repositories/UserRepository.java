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
    HashMap<Integer,List<Booking>> userBooking = new HashMap<Integer, List<Booking>>();
    public void addHotel(String name, Hotel hotel){

        hotelDb.put(name,hotel);

    }
    public void addUser(Integer adhaarNo, User user){
       userDb.put(adhaarNo,user);
    }

    public HashMap<String, Hotel> getHotelDb() {
        return hotelDb;
    }

    public HashMap<String, Booking> getBookingDb() {
        return bookingDb;
    }
    public void addBooking(String bookingId,Booking booking){
        bookingDb.put(bookingId,booking);
        List<Booking> bookingList = userBooking.getOrDefault(booking.getBookingAadharCard(),new ArrayList<>());
        bookingList.add(booking);
        userBooking.put(booking.getBookingAadharCard(), bookingList);
    }
    public int getBookingList(Integer aadharNo){
        return userBooking.get(aadharNo).size();
    }
    public Hotel getHotel(String hotelName){
        return hotelDb.get(hotelName);
    }
}
