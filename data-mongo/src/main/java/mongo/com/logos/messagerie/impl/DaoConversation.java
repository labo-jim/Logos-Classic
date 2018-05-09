package mongo.com.logos.messagerie.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.logos.entity.messagerie.Conversation;
import com.logos.entity.user.Utilisateur;

import api.com.logos.data.messagerie.IDaoConversation;
import mongo.com.logos.config.NextSequenceService;

public class DaoConversation implements IDaoConversation {
	
	@Autowired
	MongoOperations mongoOps;
	
	@Autowired
	private NextSequenceService sequence;
	
	private static final String COLLECTION = "conversation";
	
	public DaoConversation(MongoOperations mongoOps) {
		this.mongoOps = mongoOps;
	}

	@Override
	public List<Conversation> getAllConversation(Utilisateur user) {
		// TODO Auto-generated method stub
		return mongoOps.findAll(Conversation.class);
	}

	@Override
	public Conversation addConversation(Conversation conversation) {
		// TODO Auto-generated method stub
		conversation.setIdConversation(sequence.getNextSequence(COLLECTION));
		mongoOps.insert(conversation);
		return conversation;
	}

	@Override
	public Conversation addConversation(Conversation conversation, Utilisateur... utilisateurs) {
		// TODO Auto-generated method stub
		for (Utilisateur utilisateur : utilisateurs) {
			Query q = new Query(Criteria.where("_id").is(utilisateur.getIdUtilisateur()));
			utilisateur = mongoOps.findOne(q, Utilisateur.class);
			Set<Conversation> listConv = utilisateur.getConversations();
			utilisateur.getConversations().size();
			utilisateur.getConversations().add(conversation);
		}
		mongoOps.save(conversation);
		return conversation;
	}

	@Override
	public Conversation getConversationById(Integer id) {
		// TODO Auto-generated method stub
		return mongoOps.findById(id, Conversation.class);
	}

	@Override
	public Boolean deleteConversation(Conversation conversation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Conversation updateConversation(Conversation chawa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Utilisateur> getUsersByConversation(Conversation conversation) {
		// TODO Auto-generated method stub
		Conversation conv = mongoOps.findById(conversation.getIdConversation(), Conversation.class);
		return conv.getUtilisateurs();
	}

}