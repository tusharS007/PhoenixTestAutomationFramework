package com.api.utils;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

public class VaultDBConfig {

	private static final Logger LOGGER = LogManager.getLogger(VaultDBConfig.class);
	private static VaultConfig vaultConfig;
	private static Vault vault = null;

	private VaultDBConfig() {

	}

	static {
		try {
			vaultConfig = new VaultConfig().address(System.getenv("VAULT_SERVER")).token(System.getenv("VAULT_TOKEN"))
					.build();
		} catch (VaultException e) {
			LOGGER.error("Something went wrong with the Vault config", e);
			e.printStackTrace();
		}

		vault = new Vault(vaultConfig);
	}

	public static String getSecret(String key) {

		LogicalResponse logicalResponse = null;
		try {
			logicalResponse = vault.logical().read("secret/phoenix/qa/database");
		} catch (VaultException e) {
			LOGGER.error("Something went wrong with the Vault response", e);
			e.printStackTrace();
			return null;
		}

		Map<String, String> dataMap = logicalResponse.getData();

		String secretValue = dataMap.get(key);
		LOGGER.info("Secret found in the vault");
		return secretValue;
	}
}
