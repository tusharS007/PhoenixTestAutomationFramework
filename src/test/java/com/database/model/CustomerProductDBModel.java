package com.database.model;

public record CustomerProductDBModel(int id, int tr_customer_id, int mst_model_id, String dop, String popurl, String imei2,
		String imei1, String serial_number) {

}
