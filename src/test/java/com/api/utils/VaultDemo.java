package com.api.utils;

import java.util.Map;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

public class VaultDemo {

	public static void main(String[] args) throws VaultException {
		
		VaultConfig config = new VaultConfig()
				.address("http://13.233.74.231:8200")
				.token("root")
				.build();
		
		Vault vault = new Vault(config);
		
		LogicalResponse logicalResponse = vault.logical().read("secret/phoenix/qa/database");
	
		Map<String,String> map =logicalResponse.getData();
		
		System.out.println(map.get("DB_URL"));
		
	}
	
}
