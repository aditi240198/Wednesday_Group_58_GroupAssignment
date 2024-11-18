/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Business;

import java.util.Random;
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
import model.ProductManagement.ProductSummary;
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
    
    public static Business initialize() {
    Business business = new Business("Xerox");
    Random random = new Random();

    // Create Persons for customers
    PersonDirectory personDirectory = business.getPersonDirectory();
    CustomerDirectory customerDirectory = business.getCustomerDirectory();

    // Generate 10 customer profiles
    for (int i = 1; i <= 10; i++) {
        Person person = personDirectory.newPerson("Customer " + i);
        customerDirectory.newCustomerProfile(person);
    }

    // Create sales and marketing persons
    Person salesPerson = personDirectory.newPerson("Xerox sales");
    Person marketingPerson = personDirectory.newPerson("Xerox marketing");

    SalesPersonDirectory salesPersonDirectory = business.getSalesPersonDirectory();
    MarketingPersonDirectory marketingPersonDirectory = business.getMarketingPersonDirectory();

    SalesPersonProfile salesPersonProfile = salesPersonDirectory.newSalesPersonProfile(salesPerson);
    MarketingPersonProfile marketingPersonProfile = marketingPersonDirectory.newMarketingPersonProfile(marketingPerson);

    // Create Supplier Directory
    SupplierDirectory supplierDirectory = business.getSupplierDirectory();

    // Generate 5 suppliers, each with 10 products
    for (int i = 1; i <= 5; i++) {
        Supplier supplier = supplierDirectory.newSupplier("Supplier " + i);
        ProductCatalog productCatalog = supplier.getProductCatalog();

        for (int j = 1; j <= 10; j++) {
            int floorprice = 500 * j;
            int cielingPrice = floorprice + random.nextInt(1000) + 2000;
            int targetPrice = cielingPrice + random.nextInt(500) + 700; // some variation
            productCatalog.newProduct("Product " + j, floorprice, cielingPrice, targetPrice);
        }
    }

//    // Create User Accounts
//    UserAccountDirectory userAccountDirectory = business.getUserAccountDirectory();
//    userAccountDirectory.newUserAccount(salesPersonProfile, "Sales", "password");
//    userAccountDirectory.newUserAccount(marketingPersonProfile, "Marketing", "password");

    // Process Orders for each product
    MasterOrderList masterOrderList = business.getMasterOrderList();

    for (Supplier supplier : supplierDirectory.getSuplierList()) {
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

    // Print stats
    
    
    // Output the 10 generated customers
    System.out.println("Generated Customers:");
    for (CustomerProfile customerProfile : customerDirectory.getCustomerList()) {
        System.out.println("    Customer: " + customerProfile.getPerson().getPersonId());  // Assuming getPersonName() exists
    }
      System.out.println("\n");

    // Output statistics for each supplier and their products

    for (int supplierIndex = 0; supplierIndex < supplierDirectory.getSuplierList().size(); supplierIndex++) {
        Supplier supplier = supplierDirectory.getSuplierList().get(supplierIndex);

        // Print supplier name
        System.out.println("Supplier: " + supplier.getName());

        // Iterate through each product of the supplier
        for (int productIndex = 0; productIndex < supplier.getProductCatalog().getProductList().size(); productIndex++) {
            Product product = supplier.getProductCatalog().getProductList().get(productIndex);

            // Set unique product name by adding supplier index
            String uniqueProductName = "Product-S" + (supplierIndex + 1) + "-P" + (productIndex + 1);
            product.setName(uniqueProductName);

            ProductSummary productSummary = new ProductSummary(product);
            System.out.println("    Product: " + product.getProductName());
            System.out.println("        Sales Volume: " + productSummary.getSalesRevenues());
            System.out.println("        Profit Margin: " + productSummary.getProductPricePerformance());
            System.out.println("        Frequency above target: " + productSummary.getNumberAboveTarget());
            System.out.println("        Frequency below target: " + productSummary.getNumberBelowTarget());

            // Generate 10 orders per product and display them
            System.out.println("        Orders for " + product.getProductName() + ":");

            for (int i = 0; i < 10; i++) {
                // Get the same customer for this cycle of 10 orders
                CustomerProfile customer = customerDirectory.getCustomerList().get(i % customerDirectory.getCustomerList().size());
                Order order = masterOrderList.newOrder(customer, salesPersonProfile);
                int quantity = random.nextInt(10) + 1;
                int actualPrice = product.getTargetPrice() + random.nextInt(1000) - 500;
                order.newOrderItem(product, actualPrice, quantity);

                System.out.println("            Order " + (i+1) + ": Customer: " + customer.getPerson().getPersonId() + ", Quantity: " + quantity + ", Price: " + actualPrice);
            }
        }

        System.out.println("\n");
    }

    return business;
  }
}
