package dev.leonzimmermann.demo.extendablespringdemo.controllers

import dev.leonzimmermann.demo.extendablespringdemo.models.Address
import dev.leonzimmermann.demo.extendablespringdemo.repositories.AddressRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/address")
class AddressController {

  @Autowired
  private lateinit var addressRepository: AddressRepository

  @GetMapping("/all")
  fun getAllAddresses(): List<Address> {
    return addressRepository.findAll()
  }

  @GetMapping("/get/{id}")
  fun getAddressById(@RequestParam("id") id: Long): Address {
    return addressRepository.findById(id)
      .orElseThrow { java.lang.IllegalArgumentException("Could not find person with id $id") }
  }

  @PostMapping("/add")
  fun addAddress(@RequestBody address: Address): Address {
    return addressRepository.save(address)
  }
}