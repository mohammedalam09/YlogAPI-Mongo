package com.ylog.service.sequence;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ylog.entities.Sequence;

@Service
public class SequenceService {

	@Autowired
	private MongoOperations operations;

	public Long getNextSequence(String sequenceName) {

		Query query = new Query(Criteria.where("_id").is(sequenceName));

		Update update = new Update().inc("sequenceValue", 1);

		Sequence counter = operations.findAndModify(query, update, options().returnNew(true).upsert(true),
				Sequence.class);

		return !Objects.isNull(counter) ? counter.getSequenceValue() : 1;

	}

}
