package com.r2s.mockproject.rest;

import com.r2s.mockproject.constants.ResponseCode;
import com.r2s.mockproject.dto.AddressDTOResponse;
import com.r2s.mockproject.dto.ProductDTOResponse;
import com.r2s.mockproject.entity.Address;
import com.r2s.mockproject.entity.Category;
import com.r2s.mockproject.entity.Product;
import com.r2s.mockproject.entity.User;
import com.r2s.mockproject.service.AddressService;
import com.r2s.mockproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/addresses")
public class AddressController extends BaseRestController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAllAddress(){
        try {
            List<Address> addresses = this.addressService.getAllAddress();
            List<AddressDTOResponse> responses = addresses.stream()
                    .map(address -> new AddressDTOResponse(address))
                    .collect(Collectors.toList());
            return super.success(responses);
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }

    @PostMapping("")
    public ResponseEntity<?> addAddress(@RequestBody(required = true) Map<String, Object> newAddress){
        try{
            if(ObjectUtils.isEmpty(newAddress)
                    || ObjectUtils.isEmpty(newAddress.get("street"))
                    || ObjectUtils.isEmpty(newAddress.get("district"))
                    || ObjectUtils.isEmpty(newAddress.get("city"))
                    || ObjectUtils.isEmpty(newAddress.get("country"))
                    || ObjectUtils.isEmpty(newAddress.get("userId"))){
                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
            }

            Long userId = Long.parseLong(newAddress.get("userId").toString());
            User foundUser = this.userService.findUserById(userId);
            if (ObjectUtils.isEmpty(foundUser)) {
                return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
            }

            Address insertedAddress = addressService.addAddress(newAddress, foundUser);
//            return super.success(new AddressDTOResponse(insertedAddress));
            return super.success(insertedAddress);
        }catch(Exception e){
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable long id,
                                           @RequestBody(required = false) Map<String, Object> newAddress){
        try{
            if(ObjectUtils.isEmpty(newAddress)
                    || ObjectUtils.isEmpty(newAddress.get("street"))
                    || ObjectUtils.isEmpty(newAddress.get("district"))
                    || ObjectUtils.isEmpty(newAddress.get("city"))
                    || ObjectUtils.isEmpty(newAddress.get("country"))){
                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
            }

            Address foundAddress = this.addressService.findAddressById(id);
            if (ObjectUtils.isEmpty(foundAddress)) {
                return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
            }

            Address updatedAddress = addressService.updateAddress(id, newAddress);
            return super.success(new AddressDTOResponse(updatedAddress));
//            return super.success(updatedAddress);
        }catch(Exception e){
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }
}
