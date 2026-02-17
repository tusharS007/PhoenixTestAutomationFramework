package com.database.dao;

import java.sql.SQLException;

import com.database.model.CustomerDBModel;

public class DemoDaoRunner {

	public static void main(String[] args) throws SQLException {
		
		CustomerDBModel cust = CustomerDao.getCustomerInfo();
	System.out.println(cust);
	}

}
