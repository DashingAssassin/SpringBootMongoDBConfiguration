package com.springboot.mongodb.dao;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.springboot.mongodb.model.User;

public class UserDAO extends AbstractDAO {

	public static String USER_COLLECTION = "users";

	private MongoCollection<User> userCollection;

	private CodecRegistry pojoCodecRegistry;

	private final Logger log;

	protected UserDAO(MongoClient mongoClient, String databaseName) {
		super(mongoClient, databaseName);
		log = LoggerFactory.getLogger(this.getClass());
		this.db = this.mongoClient.getDatabase(MFLIX_DATABASE);
		this.pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		this.userCollection = db.getCollection(USER_COLLECTION, User.class).withCodecRegistry(pojoCodecRegistry);
	}

}
