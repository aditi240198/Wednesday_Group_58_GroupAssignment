/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.CustomerManagement;

import java.util.ArrayList;

import model.MarketModel.Market;
import model.OrderManagement.Order;
import model.Personnel.Person;

/**
 *
 * @author kal bugrara
 */
public class CustomerProfile {
    ArrayList<Order> orders;
    ArrayList<Market> markets;
    
    Person person;

    public CustomerProfile(Person p) {

        person = p;
        orders = new ArrayList();
        markets = new ArrayList();
    }

           
    public int getTotalPricePerformance(){
        int sum = 0;
        for (Order o : orders){
            sum += o.getOrderPricePerformance();
        }
 
        //for each order in the customer orderlist 
        //calculate order price performance and add it to the sum

        return sum;
    }
 
    public int    getNumberOfOrdersAboveTotalTarget(){
        //for each order in the customer order list 
        //calculate if order is positive (actual order total is greater than sum of item targets
        //if yes then add 1 to total 
        int sum = 0;
        for(Order o: orders){
            if(o.isOrderAboveTotalTarget()) 
                sum++;
        }
        
        return sum;}
    
    public int getNumberOfOrdersBelowTotalTarget(){
     int sum = 0;
     for(Order o: orders){
            if(!o.isOrderAboveTotalTarget()) 
                sum++;
        }
     return sum;
    }
    
    
    
         //for each order in the customer order list 
        //calculate if order is negative
        //if yes then add 1 to total 
        
    public boolean isMatch(String id){
        return (person.getPersonId().equals(id)); 
          
    }
    public void addCustomerOrder(Order o){
        orders.add(o);
    }
    @Override
    public String toString(){
        return person.getPersonId();
    }
        public String getCustomerId(){
        return person.getPersonId();
    }
            public Person getPerson(){
        return person;
    }

    public String getPersonId() {
        return person.getPersonId();
    }
        
        
    
}
