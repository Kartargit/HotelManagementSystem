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

    public String addHotel(Hotel hotel){
        if(hotel.getHotelName()==null)return "FAILURE";

        UserRepository hotelObj = new UserRepository();
        if(hotelObj.getHotel()!=null)return "FAILURE";
        hotelObj.addHotel(hotel.getHotelName(),hotel);

        return "SUCCESS";
    }
    public Integer addUser(User user){
        int adhaarNo = user.getaadharCardNo();
        UserRepository userObj = new UserRepository();
        userObj.addUser(adhaarNo,user);
        return adhaarNo;
    }
    public String getHotelWithMostFacilities(){
        UserRepository hotelObj = new UserRepository();
        HashMap<String, Hotel> hotels = hotelObj.getHotelDb();

        int facilityCount = 0;
        HashMap<String,Integer> hotelFacility = new HashMap<>();

        for(String hotel:hotels.keySet()){
            if(hotels.get(hotel).getFacilities().size()!=0){
                if(hotels.get(hotel).getFacilities().size()>facilityCount){
                    facilityCount = hotels.get(hotel).getFacilities().size();
                    hotelFacility.put(hotel,facilityCount);
                }
            }

        }
        if(facilityCount>0){
            return getMostFacilityHotel(facilityCount,hotelFacility);
        }
        return "";
    }
    public String getMostFacilityHotel(Integer count,HashMap<String,Integer> hashMap){
        List<String> hotelNames = new ArrayList<>();
        for (String name: hashMap.keySet()){
            if(hashMap.get(name)==count){
                hotelNames.add(name);
            }
        }
        if(hotelNames.size()>0)Collections.sort(hotelNames);
        return hotelNames.get(0);
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
            bookingObj.addBooking(uuid,booking);
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
        Hotel hotel = obj.getHotel(hotelName);
        List<Facility> facilities = hotel.getFacilities();
        for(Facility fac:newFacilities){
            facilities.add(fac);
        }
        hotel.setFacilities(facilities);
        obj.addHotel(hotelName,hotel);
        return hotel;
    }
}
