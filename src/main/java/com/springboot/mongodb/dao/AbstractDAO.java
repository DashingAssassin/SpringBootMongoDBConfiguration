package com.springboot.mongodb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

@Configuration
public abstract class AbstractDAO {
	
	@Value("${spring.mongodb.uri}")
	private String connectionString;

	protected final String MFLIX_DATABASE;

	protected MongoDatabase db;

	protected MongoClient mongoClient;

	protected AbstractDAO(MongoClient mongoClient, String databaseName) {
		this.mongoClient = mongoClient;
		MFLIX_DATABASE = databaseName;
		this.db = this.mongoClient.getDatabase(MFLIX_DATABASE);
	}

	public ObjectId generateObjectId() {
		return new ObjectId();
	}

	public Map<String, Object> getConfiguration() {
		ConnectionString connString = new ConnectionString(connectionString);
		Bson command = new Document("connectionStatus", 1);
		Document connectionStatus = this.mongoClient.getDatabase(MFLIX_DATABASE).runCommand(command);

		List authUserRoles = ((Document) connectionStatus.get("authInfo")).get("authenticatedUserRoles", List.class);

		Map<String, Object> configuration = new HashMap<>();

		if (!authUserRoles.isEmpty()) {
			configuration.put("role", ((Document) authUserRoles.get(0)).getString("role"));
			configuration.put("pool_size", connString.getMaxConnectionPoolSize());
			configuration.put("wtimeout",
					this.mongoClient.getDatabase("mflix").getWriteConcern().getWTimeout(TimeUnit.MILLISECONDS));
		}
		return configuration;
	}
}
