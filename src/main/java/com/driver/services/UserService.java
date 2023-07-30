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
import java.util.UUID;
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

        UserRepository bookingObj = new UserRepository();
        HashMap<String, Hotel> hotels = bookingObj.getHotelDb();

        String hotelName = booking.getHotelName();

        int availableRoom = hotels.get(hotelName).getAvailableRooms();

        if(availableRoom>=booking.getNoOfRooms()){
            int price = hotels.get(hotelName).getPricePerNight();

            String uuid = String.valueOf(UUID.randomUUID());
            int totalPrice = booking.getNoOfRooms()*price;

            booking.setBookingId(uuid);
            booking.setAmountToBePaid(totalPrice);

            bookingObj.addBooking(uuid,booking,booking.getBookingAadharCard());

            hotels.get(hotelName).setAvailableRooms(availableRoom-booking.getNoOfRooms());

            return totalPrice;
        }
        return -1;
    }

    public int getBookings(Integer aadharNo){
        UserRepository userBookingList = new UserRepository();
        return userBookingList.getBookingList(aadharNo);
    }
    public Hotel getUpdatedFacilities(String hotelName,List<Facility> newFacilities){
        UserRepository obj = new UserRepository();
        Hotel hotel = obj.getHotelDb().get(hotelName);

        List<Facility> hotelFacility = hotel.getFacilities();
        for(Facility facility: newFacilities){
            if(hotelFacility.contains(facility)){
                continue;
            }
            else {
                hotelFacility.add(facility);
            }
        }
        hotel.setFacilities(hotelFacility);
        UserRepository obj2 = new UserRepository();

        return hotel;
    }
}
