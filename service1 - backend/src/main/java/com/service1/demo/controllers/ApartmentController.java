package com.service1.demo.controllers;

import com.service1.demo.entities.Apartment;
import com.service1.demo.services.ApartmentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

@RestController
public class ApartmentController {

    private ApartmentService service;

    public ApartmentController(ApartmentService service){
        this.service = service;
    }

    @CrossOrigin("*")
    @GetMapping("/apartments")
    public List<Apartment> apartments(){
        return service.getAll();
    }

    @GetMapping(value = "/apartments", params = {"city"})
    public List<Apartment> getApartmentsForCity(
            @RequestParam("city") String city
    ){
        return this.service.getApartmentByCityName(city);
    }

    @GetMapping("/apartments/{id}")
    public Apartment getById(@PathVariable("id") int id){
        return service.getById(id);
    }

    @DeleteMapping("/apartments/{id}")
    public void deleteApartment(@PathVariable("id") int id){
        this.service.deleteById(id);
    }

    @PatchMapping("/apartments/{id}")
    public void updateapartment(Map<String, Object> newPropertiesValues, @PathVariable("id") int id ){
        this.service.updateApartment(newPropertiesValues, id);
    }

    @PatchMapping("/{username}/apartments/{id}/image")
    public void changeImage(MultipartHttpServletRequest request,
                            @PathVariable("id") int apartmentId,
                            @PathVariable("username") String username){
        this.service.updateApartmentImage(request,apartmentId,username);
    }

    @CrossOrigin("*")
    @GetMapping("/users/{username}/apartments")
    public List<Apartment> myApartemnts(@PathVariable("username") String  login){
        return this.service.getAllForUser(login);
    }

    @CrossOrigin("*")
    @PostMapping("/apartments")
    public Apartment addApartemnt(MultipartHttpServletRequest multipartRequest){
        return this.service.saveApartment(multipartRequest);
    }

    @CrossOrigin("*")
    @GetMapping(value = "/images/{imagename}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] copyImageToInputStream(@PathVariable("imagename") String imageName){
        return this.service.getApartmentImage(imageName);
    }
}
