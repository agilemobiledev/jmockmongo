/**
 * Copyright (c) 2012, Thilo Planz. All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License, Version 2.0
 * as published by the Apache Software Foundation (the "License").
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * You should have received a copy of the License along with this program.
 * If not, see <http://www.apache.org/licenses/LICENSE-2.0>.
 */

package jmockmongo;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bson.BSONObject;
import org.bson.types.ObjectId;

public class MockDBCollection {

	private final String name;

	private final Map<Object, BSONObject> data = new ConcurrentHashMap<Object, BSONObject>();

	MockDBCollection(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public BSONObject findOne(Object id) {
		// byte[] don't hash...
		if (id instanceof byte[]) {
			id = new BigInteger((byte[]) id);
		}
		return data.get(id);
	}

	public void insert(BSONObject b) {
		if (!b.containsField("_id")) {
			b.put("_id", new ObjectId());
		}
		Object id = b.get("_id");
		// byte[] don't hash...
		if (id instanceof byte[]) {
			id = new BigInteger((byte[]) id);
		}
		data.put(id, b);
	}
}