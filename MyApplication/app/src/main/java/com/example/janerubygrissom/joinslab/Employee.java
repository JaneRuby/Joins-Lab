package com.example.janerubygrissom.joinslab;

/**
 * Created by janerubygrissom on 7/17/16.
 */
public class Employee {
    private String firstName;
    private String lastName;
    private String city;
    private String year;
    private String EmpSSN;


                public Employee(){}

                public Employee (String firstName, String lastName, String city, String year, String ssn){
                this.firstName = firstName;
                this.city = city;
                this.lastName = lastName;
                this.year = year;
            }

                public String getFirstName() {
                return firstName;
            }

                public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

                public String getLastName() {
                return lastName;
           }

    public void setLastName(String lastName) {
             this.lastName = lastName;
          }

    public String getCity() {
               return city;
            }

                public void setCity(String city) {
                this.city = city;
          }
       public String getYear() {
              return year;
           }

                public void setYear(String year) {
              this.year = year;
           }

    public String getEmpSSN() {
        return EmpSSN;
    }

    public void setEmpSSN(String empSSN) {
        EmpSSN = empSSN;
    }
}


