package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.AddressEditDTO;
import ittalents.dominos.model.DTOs.AddressInfoDTO;
import ittalents.dominos.service.AddressService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AddressController extends AbstractController {
    @Autowired
    private AddressService addressService;

    @PostMapping("/dominos/addresses")
    public AddressInfoDTO addAddress(@Valid @RequestBody AddressInfoDTO addressInfoDTO, HttpSession session) {
        getLoggedId(session);
        return addressService.addAddress(addressInfoDTO);
    }

    @DeleteMapping("/dominos/addresses/{id}")
    public void deleteAddress(HttpSession session, @PathVariable("id") int addressId) {
        getLoggedId(session);
        addressService.deleteAddress(addressId);
    }

    @GetMapping("/dominos/addresses")
    public List<AddressInfoDTO> getAllAddressesOfLoggedUser(HttpSession session) {
        getLoggedId(session);
        return addressService.getAllAddressesByUser(getLoggedId(session));
    }

    @PutMapping("/dominos/addresses/{id}")
    public AddressEditDTO editAddress(@Valid @RequestBody AddressInfoDTO newAddress, HttpSession session, @PathVariable int id){
        getLoggedId(session);
        String name = newAddress.getAddressName();
        AddressInfoDTO updateAddress = addressService.editAddress(name, id);
        return new AddressEditDTO(id, updateAddress.getAddressName());
    }




}