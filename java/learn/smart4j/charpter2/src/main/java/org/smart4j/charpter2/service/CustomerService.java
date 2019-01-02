package org.smart4j.charpter2.service;

import org.smart4j.charpter2.helper.DatabaseHelper;
import org.smart4j.charpter2.model.Customer;

import java.util.List;
import java.util.Map;

public class CustomerService {

    public List<Customer> getCustomerList() {
        String sql = "select * from Customer";
        return DatabaseHelper.queryEntityList(Customer.class, sql);
    }

    public Customer getCustomer(long id) {
        String sql = "select * from Customer where id=?";
        return DatabaseHelper.queryEntity(Customer.class, sql, id);
    }

    public boolean createCustomer(Map<String, Object> fieldMap) {
        return DatabaseHelper.insertEntity(Customer.class, fieldMap);
    }

    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
    }

    public boolean deleteCustomer(long id) {
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }
}
