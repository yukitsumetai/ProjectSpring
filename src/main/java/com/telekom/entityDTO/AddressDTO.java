package com.telekom.entityDTO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "addresses")
public class AddressDTO implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name="addressID")
        private int id;
        private String street;
        private String houseNo;
        private String city;
        private String country;
        private int zip;


        @OneToOne(mappedBy = "address")
        private ClientDTO client;

        public AddressDTO(){}



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

