package com.springboot.mongodb.configuration.customcodec;

/**
 * This class StringObjectIdCodec will be used when converting from DAO object to
 * Java Object so that conversion of ObjectId to String and String to Object Id is without 
 * and exception
 */
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

public class StringObjectIdCodec implements Codec<String> {

	@Override
	public void encode(BsonWriter writer, String value, EncoderContext encoderContext) {
		writer.writeObjectId(new ObjectId(value));
	}

	@Override
	public Class<String> getEncoderClass() {
		return String.class;
	}

	@Override
	public String decode(BsonReader reader, DecoderContext decoderContext) {
		return reader.readObjectId().toHexString();
	}

}
