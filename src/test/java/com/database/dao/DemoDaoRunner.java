package com.database.dao;

import java.sql.SQLException;

import com.database.model.CustomerAddressDBModel;

public class DemoDaoRunner {

	public static void main(String[] args) throws SQLException {

		CustomerAddressDBModel cust = CustomerAddressDao.getCustomerAddressData(189325);
		System.out.println(cust);
        
	}

}
 