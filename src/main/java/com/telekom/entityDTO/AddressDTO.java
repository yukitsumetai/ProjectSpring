package com.telekom.entityDTO;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


public class AddressDTO implements Serializable {

        private int id;

        @NotBlank
        @Size(min=2, max=20)
        private String street;

        @NotBlank
        @Size(min=1, max=4)
        private String houseNo;

        @NotBlank
        @Size(min=2, max=20)
        private String city;

        @NotBlank
        @Size(min=2, max=20)
        private String country;

        @NotNull
        @Digits(integer=5, fraction=0)
        private int zip;


        private ClientDTO client;

        public ClientDTO getClient() {
                return client;
        }

        public void setClient(ClientDTO client) {
                this.client = client;
        }

        public int getAddressID() {
                return id;
        }

        public void setAddressID(int addressID) {
                this.id = addressID;
        }

        public String getHouseNo() {
                return houseNo;
        }

        public void setHouseNo(String houseNo) {
                this.houseNo = houseNo;
        }


        public String getStreet() {
                return street;
        }

        public void setStreet(String street) {
                this.street = street;
        }

        public String getCity() {
                return city;
        }

        public void setCity(String city) {
                this.city = city;
        }

        public int getZip() {
                return zip;
        }

        public void setZip(int zip) {
                this.zip = zip;
        }

        public String getCountry() {
                return country;
        }

        public void setCountry(String country) {
                this.country = country;
        }






    }

