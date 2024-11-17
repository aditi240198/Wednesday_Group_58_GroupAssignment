/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Business;

import model.Business.Business;
import model.CustomerManagement.CustomerDirectory;
import model.CustomerManagement.CustomerProfile;
import model.MarketingManagement.MarketingPersonDirectory;
import model.MarketingManagement.MarketingPersonProfile;
import model.OrderManagement.MasterOrderList;
import model.OrderManagement.Order;
import model.OrderManagement.OrderItem;
import model.Personnel.EmployeeDirectory;
import model.Personnel.EmployeeProfile;
import model.Personnel.Person;
import model.Personnel.PersonDirectory;
import model.ProductManagement.Product;
import model.ProductManagement.ProductCatalog;
import model.SalesManagement.SalesPersonDirectory;
import model.SalesManagement.SalesPersonProfile;
import model.Supplier.Supplier;
import model.Supplier.SupplierDirectory;
import model.UserAccountManagement.UserAccount;
import model.UserAccountManagement.UserAccountDirectory;

/**
 *
 * @author kal bugrara
 */
public class ConfigureABusiness {
    
    private static SalesPersonProfile salesPersonProfile;

  public static Business initialize() {
      Business business = new Business("Xerox");
    Random random = new Random();
    
    
    PersonDirectory personDirectory = business.getPersonDirectory();
    CustomerDirectory customerDirectory = business.getCustomerDirectory();
    
    for (int i = 1; i <= 10; i++){
        Person person = personDirectory.newPerson("Customer " +i);
        customerDirectory.newCustomerProfile(person);
    }
    
    Person salesPerson = personDirectory.newPerson("Xerox sales");
    SalesPersonProfile salesPersonProfile = new SalesPersonProfile(salesPerson);
    Person marketingPerson = personDirectory.newPerson("Xerox Marketing");
    
    SalesPersonDirectory salesPersonDirectory = business.getSalesPersonDirectory();
    MarketingPersonDirectory marketingPersonDirectory = business.getMarketingPersonDirectory();
    
    
    SupplierDirectory supplierDirectory = business.getSupplierDirectory();
    
     //Create 5 supplier each with 10 product
    for (int i = 1; i <= 5; i++){
        Supplier supplier = supplierDirectory.newSupplier("Supplier" +i);
        ProductCatalog productCatalog = supplier.getProductCatalog();
        
        for (int j = 1; j <= 10; j++){
            int floorprice = 1000 * j;
            int ceilingPrice = floorprice + random.nextInt(1000) + 5000;
            int targetPrice = ceilingPrice + random.nextInt(500) + 1000;
            productCatalog.newProduct("Product " + j, floorprice, ceilingPrice, targetPrice);
        }
    }
    
      MasterOrderList masterOrderList = business.getMasterOrderList();
      
      for (Supplier supplier : supplierDirectory.getSupplierList()) {
          for (Product product : supplier.getProductCatalog().getProductList()) {
              for (int k = 0; k < 10; k++) {
                  CustomerProfile customer = customerDirectory.getCustomerList().get(random.nextInt(10));
                  Order order = masterOrderList.newOrder(customer, salesPersonProfile);
                  int quantity = random.nextInt(10) + 1;
                  int actualPrice = product.getTargetPrice() + random.nextInt(2000) - 1000;
                  order.newOrderItem(product, actualPrice, quantity);
              }
          }
      }
      
      //Output
      System.out.println("Generated Customer:");
      for (CustomerProfile customerProfile : customerDirectory.getCustomerList()) {
          System.out.println(" Customer:" + customerProfile.getPerson().getPersonId());
      }
    
      System.out.println("\n");
      
      for (int supplierIndex = 0; supplierIndex < supplierDirectory.getSupplierList().size(); supplierIndex++){
          Supplier supplier = supplierDirectory.getSupplierList().get(supplierIndex);
          
          System.out.println("Supplier:" +supplier.getName());
          
          for (int productIndex = 0; productIndex < supplier.getProductCatalog().getProductList().size(); productIndex++) {
              Product product = supplier.getProductCatalog().getProductList().get(productIndex);
              
              String uniqueProductName = "Product_S" + (supplierIndex + 1) + "_p" + (productIndex + 1);
              product.setName(uniqueProductName);
              
              ProductSummary productSummary = new ProductSummary(product);
              System.out.println("   Product: " + product.getName());
              System.out.println("   Sales Volume: " + productSummary.getSalesRevenues());
              System.out.println("   Profit Margin: " + productSummary.getProductPricePerformance());
              System.out.println("   Frequency above target: " + productSummary.getNumberAboveTarget());
              System.out.println("   Frequency below target: " + productSummary.getNumberBelowTarget());

              
              System.out.println("       Order for " + product.getName()+ "+");
              
              for (int i = 0; i < 10; i++){
                  
                  CustomerProfile customer = customerDirectory.getCustomerList().get(i % customerDirectory.getCustomerList().size());
                  Order order = masterOrderList.newOrder(customer,salesPersonProfile);
                  int quantity = random.nextInt(10) + 1;
                  int actualPrice = product.getTargetPrice() + random.nextInt(1000) - 500;
                  order.newOrderItem(product, actualPrice, quantity);
                  
                  System.out.println("     Order "+ (i+1) + ": Customer: " + customer.getPersonId() + "  Quantity:" + quantity + ",");
              }
          }
          System.out.println("\n");
      }
      
    return business;
  }

    private static Object productSummary() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
     
  }
}
