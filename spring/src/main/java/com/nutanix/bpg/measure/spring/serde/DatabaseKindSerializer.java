package com.nutanix.bpg.measure.spring.serde;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.nutanix.bpg.measure.model.DatabaseKind;

public class DatabaseKindSerializer extends StdSerializer<DatabaseKind>{

	protected DatabaseKindSerializer() {
		super(DatabaseKind.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void serialize(DatabaseKind value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeStringField("kind", value.getName());
		gen.writeEndObject();
	}

}
