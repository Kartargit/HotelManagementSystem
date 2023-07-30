package com.driver.repositories;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.UUID;

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

    public int bookRoom(Booking booking){

        String hotelName = booking.getHotelName();

        int availableRoom = hotelDb.get(hotelName).getAvailableRooms();

        if(availableRoom>=booking.getNoOfRooms()){
            int price = hotelDb.get(hotelName).getPricePerNight();

            String uuid = String.valueOf(UUID.randomUUID());
            int totalPrice = booking.getNoOfRooms()*price;

            booking.setBookingId(uuid);
            booking.setAmountToBePaid(totalPrice);

            bookingDb.put(uuid,booking);
            userBooking.put(booking.getBookingAadharCard(),userBooking.getOrDefault(booking.getBookingAadharCard(),0)+1);
            hotelDb.get(hotelName).setAvailableRooms(availableRoom-booking.getNoOfRooms());

            return totalPrice;
        }
        return -1;
    }
    public int getBookingList(Integer aadharNo){
        return userBooking.get(aadharNo);
    }
    public Hotel getUpdatedFacilities(String hotelName,List<Facility> newFacilities){

        Hotel hotel = hotelDb.get(hotelName);

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
        hotelDb.put(hotelName,hotel);

        return hotel;
    }
}
