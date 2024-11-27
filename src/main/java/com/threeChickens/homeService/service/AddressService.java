package com.threeChickens.homeService.service;

import com.threeChickens.homeService.dto.address.CreateAddressDto;
import com.threeChickens.homeService.dto.address.GetAddressDto;
import com.threeChickens.homeService.dto.googleMap.DistanceMatrixDto;
import com.threeChickens.homeService.dto.googleMap.GeocodeDto;
import com.threeChickens.homeService.dto.googleMap.PlaceDto;
import com.threeChickens.homeService.dto.province.DistrictDto;
import com.threeChickens.homeService.dto.province.ProvinceDto;
import com.threeChickens.homeService.dto.province.WardDto;
import com.threeChickens.homeService.entity.*;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import com.threeChickens.homeService.key.DistrictKey;
import com.threeChickens.homeService.key.WardKey;
import com.threeChickens.homeService.repository.AddressRepository;
import com.threeChickens.homeService.repository.DistrictRepository;
import com.threeChickens.homeService.repository.ProvinceRepository;
import com.threeChickens.homeService.repository.WardRepository;
import com.threeChickens.homeService.utils.GoogleMapUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AddressService {
    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GoogleMapUtil googleMapUtil;

    public List<PlaceDto> searchPlaceByGoogleMap(String input) throws IOException {
        return googleMapUtil.placeAutocomplete(input);
    }

    public GeocodeDto geocode(String placeId, String latitude, String longitude) throws IOException {
        return googleMapUtil.geocode(placeId, latitude,longitude);
    }

    public DistanceMatrixDto distanceMatrix(String sourcePlaceId, String destinationPlaceId) throws IOException {
        return googleMapUtil.distanceMatrix(sourcePlaceId, destinationPlaceId);
    }

    public List<WardDto> getProvinces(Integer provinceCode, Integer districtCode) {
        if(provinceCode != null && districtCode != null) {
            DistrictKey districtKey = DistrictKey.builder().code(districtCode).provinceCode(provinceCode).build();
            District district = districtRepository.findById(districtKey).orElseThrow(
                    () -> new AppException(StatusCode.DISTRICT_NOT_FOUND)
            );
            return district.getWards().stream().map(
                    ward -> WardDto.builder().code(ward.getCode().getCode()).name(ward.getName()).build()
            ).toList();
        }else if(provinceCode != null){
            Province province = provinceRepository.findById(provinceCode).orElseThrow(
                    ()->new AppException(StatusCode.PROVINCE_NOT_FOUND)
            );
            return province.getDistricts().stream().map(
                    district -> WardDto.builder().code(district.getCode().getCode()).name(district.getName()).build()
            ).toList();
        }
        else{
            List<Province> provinces = provinceRepository.findAll();
            return provinces.stream().map(
                    province -> WardDto.builder().code(province.getCode()).name(province.getName()).build()
            ).toList();
        }
    }

//    public void initProvinces(){
//        if(provinceRepository.count()==0){
//            String url = "https://provinces.open-api.vn/api/?depth=3";
//            List<ProvinceDto> provinces = List.of(Objects.requireNonNull(restTemplate.getForObject(url, ProvinceDto[].class)));
//
//            provinces.forEach(provinceDto -> {
//                Province province = modelMapper.map(provinceDto, Province.class);
//                provinceRepository.save(province);
//                provinceDto.getDistricts().forEach(districtDto -> {
//                    DistrictKey districtKey = DistrictKey.builder().code(districtDto.getCode()).provinceCode(province.getCode()).build();
//                    District district = District.builder().code(districtKey).name(districtDto.getName()).province(province).build();
//                    districtRepository.save(district);
//                    districtDto.getWards().forEach(wardDto -> {
//                        WardKey wardKey = WardKey.builder().code(wardDto.getCode()).districtCode(district.getCode()).build();
//                        Ward ward = Ward.builder().code(wardKey).name(wardDto.getName()).district(district).build();
//                        wardRepository.save(ward);
//                    });
//                });
//            });
//        }
//    }

    public Ward findWardByCode(int provinceCode, int districtCode, int wardCode){
        DistrictKey districtKey = DistrictKey.builder().code(districtCode).provinceCode(provinceCode).build();
        WardKey wardKey = WardKey.builder().code(wardCode).districtCode(districtKey).build();
        return wardRepository.findByCode(wardKey).orElseThrow(
                () -> new AppException(StatusCode.ADDRESS_NOT_FOUND)
        );
    }

    public Address findById(String id){
        return addressRepository.findByIdAndDeletedIsFalse(id).orElseThrow(
                () -> new AppException(StatusCode.ADDRESS_NOT_FOUND)
        );
    }

    public void deletedById(String addressId){
        Address address = findById(addressId);
        address.setDeleted(true);
        if(address.isDefault()) {
            address.setDefault(false);
            List<Address> addresses = address.getUser().getAddresses().stream().filter(
                    address1 -> !address1.isDeleted()
            ).toList();
            if(!addresses.isEmpty()) {
                addresses.getFirst().setDefault(true);
                addressRepository.save(addresses.getFirst());
            }
        }
        addressRepository.save(address);
    }

    public GetAddressDto create(CreateAddressDto createAddressDto, User user){
//        Ward ward = findWardByCode(createAddressDto.getProvinceCode(), createAddressDto.getDistrictCode(), createAddressDto.getWardCode());
        Address address = modelMapper.map(createAddressDto, Address.class);
//        address.setWard(ward);

        address.setUser(user);
        address.setDefault(user.getAddresses().isEmpty());
        address = addressRepository.save(address);
        return modelMapper.map(address, GetAddressDto.class);
    }


    public GetAddressDto update(String addressId, CreateAddressDto updateAddressDto){
        Address address = findById(addressId);
        boolean previousDefault = address.isDefault();
        boolean currentDefault = updateAddressDto.isDefault();

        modelMapper.map(updateAddressDto, address);

        // set default
        if(!previousDefault && currentDefault) {
            address.setDefault(true);
            address.getUser().getAddresses().stream().filter(
                    address1 -> !address1.isDeleted() && address1.isDefault()
            ).forEach(
                    address1 -> {
                        address1.setDefault(true);
                        addressRepository.save(address1);
                    }
            );
        }else{
            address.setDefault(previousDefault);
        }

        // set ward
//        if(updateAddressDto.getWardCode()!=null) {
//            Ward ward = findWardByCode(updateAddressDto.getProvinceCode(), updateAddressDto.getDistrictCode(), updateAddressDto.getWardCode());
//            address.setWard(ward);
//        }

        address = addressRepository.save(address);
        return modelMapper.map(address, GetAddressDto.class);
    }
}
