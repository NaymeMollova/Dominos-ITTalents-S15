package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.AddressInfoDTO;
import ittalents.dominos.model.entities.Address;
import ittalents.dominos.service.AddressService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AddressController extends AbstractController {
    @Autowired
    private AddressService addressService;

    //ADD ADDRESS
    @PostMapping("/dominos/addresses")
    public AddressInfoDTO addAddress(HttpSession session, @RequestBody AddressInfoDTO address) {
        isUserLoggedIn(session);
        return addressService.saveAddress(getLoggedId(session), address.getAddress());
    }

    //DELETE ADDRESS
    @DeleteMapping("/dominos/addresses/{id}")
    public AddressInfoDTO deleteAddress(HttpSession session, @PathVariable int id) {
        isUserLoggedIn(session);
        return addressService.deleteAddress(getLoggedId(session), id);
    }
    //VIEW ADDRESSES
    @GetMapping("/dominos/addresses")
    public List<AddressInfoDTO> getAllAddressesOfLoggedUser(HttpSession session) {
        isUserLoggedIn(session);
        List<Address> addresses = addressService.getAllAddressesByOwner(getLoggedId(session));
        return addresses.stream()
                .map(address -> new AddressInfoDTO(address.getAddressName()))
                .collect(Collectors.toList());
    }

    //EDIT ADDRESS
    @PutMapping("/dominos/addresses/{id}")
    public AddressInfoDTO editAddress(HttpSession session, @PathVariable int id, @RequestBody AddressInfoDTO newAddress){
        isUserLoggedIn(session);
        return addressService.editAddress(newAddress.getAddress(), getLoggedId(session), id);

    }




}