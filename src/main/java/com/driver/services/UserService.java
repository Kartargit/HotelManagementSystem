package com.driver.services;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class UserService {

    UserRepository repositoryObj = new UserRepository();
    public String addHotel(Hotel hotel){

            return repositoryObj.addHotels(hotel);
    }
    public Integer addUser(User user){
        int adhaarNo = user.getaadharCardNo();
        UserRepository userObj = new UserRepository();
        userObj.addUser(adhaarNo,user);
        return adhaarNo;
    }
    public String getHotelWithMostFacilities(){
       return repositoryObj.getHotelWithMostFacilities();
    }


    public int bookRoom(Booking booking){
        return repositoryObj.bookRoom(booking);
    }

    public int getBookings(Integer aadharNo){

        return repositoryObj.getBookingList(aadharNo);
    }
    public Hotel getUpdatedFacilities(String hotelName,List<Facility> newFacilities){
        return repositoryObj.getUpdatedFacilities(hotelName,newFacilities);
    }
}
