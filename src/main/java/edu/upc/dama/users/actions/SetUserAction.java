package edu.upc.dama.users.actions;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.Years;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.opensymphony.xwork2.ActionSupport;

import edu.upc.dama.mongodb.MongoDBAware;
import edu.upc.dama.users.model.Global;
import edu.upc.dama.users.model.User;

public class SetUserAction extends ActionSupport implements MongoDBAware {

	private static final long serialVersionUID = 1L;
	private DB db;
	private User user;
	
	@SuppressWarnings("deprecation")
	public void validate() {
		// Comprovo que l'username existeix
		DBCollection coll = db.getCollection(Global.C_USERS);
		coll.setObjectClass(User.class);
		BasicDBObject q = new BasicDBObject(Global.A_USERNAME, user.getUsername());
		if (!coll.find(q).hasNext()) {
			addActionError("The user doesn't exist");
		}
		// Comprovo que el nom té contingut
		if (user.getNom().length() == 0) {
			addActionError("The name cannot be empty");
		}
		// De la imatge no en comprovo res?
		// Comprovo que la contrasenya tingui almenys 8 caràcters i que hi hagi, com a mínim, un número, una majúscula i una minúscula
		if (user.getMotDePas().length() < 8) {
			addActionError("The password must contain at least 8 characters");
		}
		
	    Pattern lowLetter = Pattern.compile("[a-z]");  
	    Pattern digit = Pattern.compile("[0-9]");  
	    Pattern letter = Pattern.compile("[A-Z]");  
	    
	    Matcher hasLowLetter = lowLetter.matcher(user.getMotDePas());  
	    Matcher hasDigit = digit.matcher(user.getMotDePas());
	    Matcher hasMaj = letter.matcher(user.getMotDePas());
	    if (!(hasLowLetter.find() && hasDigit.find() && hasMaj.find())) {
	    	addActionError("The password must contain at least a capital letter, a lower case letter and a number");
	    }
	    // Comprovo que la data no sigui nul, que la data no sigui anterior al 1900 i que tingui més de 18 anys
	    if (user.getDataNaix() == null) {
			addActionError("You must insert the date of birth");
	    }
	    if (user.getDataNaix().before(new Date(1900,1,1))) {
	    	addActionError("The date of birth cannot be lower than 1/1/1900");
	    }
	    DateTime birthDate = new DateTime(user.getDataNaix());
	    if (Years.yearsBetween(birthDate, new DateTime()).getYears() < 18) {
	    	addActionError("The user must be at least 18 years old");
	    }
	    // Comprovo que el sexe sigui H o D
	    if (!(user.getSexe().equals(new String("H")) || user.getSexe().equals(new String("D")))) {
	    	addActionError("The sex of the user must be either H or D");
	    }
	    // Comprovo que el país existeixi
	    DBCollection collP = db.getCollection(Global.C_COUNTRIES);
		BasicDBObject q1 = new BasicDBObject(Global.A_COUNTRYCODE, user.getPais());
		if (!collP.find(q1).hasNext()) {
			addActionError("There's no country with that code");
		}
	}

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String execute() throws Exception {
		DBCollection coll = db.getCollection(Global.C_USERS);
		coll.setObjectClass(User.class);
		BasicDBObject q = new BasicDBObject(Global.A_USERNAME, user.getUsername());
		coll.update(q, user);
		return "success";
	}

	public void setDb(DB db) {
		this.db = db;
	}

	public DB getDb() {
		return db;
	}

}
