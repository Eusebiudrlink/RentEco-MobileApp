    package com.example.serverrenteco.Domain;

    import jakarta.persistence.*;

    @Entity
    @Table(name = "rides")
    public class Ride {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name = "from_address")
        private String fromAddress;

        @Column(name = "to_address")
        private String toAddress;

        @Column(name = "start_time")
        private String startTime;

        @Column(name = "end_time")
        private String endTime;

        @Column(name = "price")
        private int price;

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User userId;

        @ManyToOne
        @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
        private AutoVehicle vehicleId;

        // Constructori, getteri și setteri

        public Ride() {
        }

          public Ride(String fromAddress, String toAddress, String startTime, String endTime, int price, User userId, AutoVehicle vehicleId) {
                this.fromAddress = fromAddress;
                this.toAddress = toAddress;
                this.startTime = startTime;
                this.endTime = endTime;
                this.price = price;
                this.userId = userId;
                this.vehicleId = vehicleId;
            }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFromAddress() {
            return fromAddress;
        }

        public void setFromAddress(String fromAddress) {
            this.fromAddress = fromAddress;
        }

        public String getToAddress() {
            return toAddress;
        }

        public void setToAddress(String toAddress) {
            this.toAddress = toAddress;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public User getUserId() {
            return userId;
        }

        public void setUserId(User userId) {
            this.userId = userId;
        }

        public AutoVehicle getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(AutoVehicle vehicleId) {
            this.vehicleId = vehicleId;
        }
    }