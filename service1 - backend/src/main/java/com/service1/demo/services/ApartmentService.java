package com.service1.demo.services;

import com.service1.demo.entities.Address;
import com.service1.demo.entities.Apartment;
import com.service1.demo.entities.City;
import com.service1.demo.entities.User;
import com.service1.demo.repositories.ApartmentRepository;
import com.service1.demo.repositories.CityRepository;
import com.service1.demo.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.transaction.Transactional;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private UserRepository userRepository;
    private CityRepository cityReposiotry;


    public ApartmentService(ApartmentRepository apartmentRepository,
                            UserRepository userRepository,
                            CityRepository cityRepository){
        this.apartmentRepository = apartmentRepository;
        this.userRepository = userRepository;
        this.cityReposiotry = cityRepository;

    }
    public List<Apartment> getAllForUser(String login){
        String username = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        if(login.equals(username)){
            return this.userRepository.findByUsername(login).getApartment();
        }
        return new ArrayList<>();
    }
    public List<Apartment> getAll(){
        return apartmentRepository.findAll();
    }

    public void updateApartmentImage(MultipartHttpServletRequest request, int apartmentId, String username){
        MultipartFile newImage = request.getFile("apartmentImage");

        Apartment apartment = this.apartmentRepository.findByIdAndOwnerUsername(apartmentId, username);
        String imageName = apartment.getImageUrl();
        try{
            if(newImage != null){
                OutputStream out = Files.newOutputStream(Paths.get("src/main/resources/static/" + imageName + ".jpg"));
                out.write(newImage.getBytes());
                out.flush();
                out.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<Apartment> getApartmentByCityName(String cityName){
        City city = new City();
        city.setCityName(cityName);
        return this.cityReposiotry.findByCityName(cityName)
                .map(City::getApartmentList)
                .orElseThrow();
    }

    @Transactional
    public Apartment saveApartment(MultipartHttpServletRequest multipartRequest){
        final Apartment apartment = new Apartment();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User owner = this.userRepository.findByUsername(username);

        apartment.setOwner(owner);
        try{
            apartment.setBathCount(Integer.parseInt(new String(multipartRequest.getPart("bathCount").getInputStream().readAllBytes())));
            apartment.setBedCount(Integer.parseInt(new String(multipartRequest.getPart("bedCount").getInputStream().readAllBytes())));
            final String officeAddress = new String(multipartRequest.getPart("officeAddress").getInputStream().readAllBytes());
            final String email = new String(multipartRequest.getPart("email").getInputStream().readAllBytes());
            final String phoneNumber = new String(multipartRequest.getPart("phoneNumber").getInputStream().readAllBytes());
            final String description = new String(multipartRequest.getPart("description").getInputStream().readAllBytes());
            final int metrage = Integer.parseInt(new String(multipartRequest.getPart("metrage").getInputStream().readAllBytes()));
            Address address = new Address();
            address.setEmail(email);
            address.setPhoneNumber(phoneNumber);
            address.setOfficeAddress(officeAddress);
            String cityName = new String(multipartRequest.getPart("cityName").getInputStream().readAllBytes());

            apartment.setAddress(address);
            apartment.setDescription(description);
            apartment.setMetrage(metrage);

            Apartment saved = apartmentRepository.save(apartment);
            City city = this.cityReposiotry.findByCityName(cityName)
                    .orElseGet(() ->{
                        City c = new City();
                        c.setCityName(cityName);
                        c.getApartmentList().add(apartment);
                        saved.setCity(c);
                        return cityReposiotry.save(c);
                    });
            Apartment savedApart = apartmentRepository.save(saved);

            String imageName = "apartmentImage" + saved.getId() + ".jpg";
            InputStream file = multipartRequest.getPart("apartmentImage").getInputStream();

            if(file != null){

                Path imagePath = Paths.get("src/main/resources/static/" + imageName);
                Path p = Files.createFile(imagePath);

                FileCopyUtils.copy(file, Files.newOutputStream(p));
            }

            savedApart.setCity(city);
            owner.getApartment().add(savedApart);
            this.userRepository.save(owner);
            return savedApart;
        }catch (IOException | ServletException e){
            e.printStackTrace();
            return null;

        }

    }

    public void deleteById(int id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        this.apartmentRepository.deleteByIdAndOwnerUsername(id, username);
    }

    public Apartment getById(int id){
        return this.apartmentRepository.findById(id).orElseThrow();
    }

    public void updateApartment(Map<String, Object> newPropertiesValues, int id){
        this.apartmentRepository.findById(id)
                .map(apartment -> {
                    for(Map.Entry<String, Object> entry: newPropertiesValues.entrySet()){
                        Field field = ReflectionUtils.findField(Apartment.class, entry.getKey());
                        field.setAccessible(true);
                        ReflectionUtils.setField(field, apartment, entry.getValue());
                    }
                    return apartment;
                }).ifPresent(apartmentRepository::save);
    }

    public byte[] getApartmentImage(String imageName){
        try{
            boolean exists=Files.exists(Paths.get("src/main/resources/static/" + imageName));
            if(exists){
                InputStream in = Files.newInputStream(Paths.get("src/main/resources/static/" + imageName));
                return in.readAllBytes();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;

    }
}
