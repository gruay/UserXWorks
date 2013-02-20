package edu.upc.dama.users.model;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;
import org.bson.types.ObjectId;


import com.mongodb.DBObject;


public class User implements DBObject{

	private ObjectId _id;
	private String nom;
	private String username;
	private String imatge;
	private String twitter;
	private String facebook;
	private String linkedin;
	private String password;
	private Date dataNaix;
	private String sexe; // 'h' - home, 'd' - dona
	private String pais;
	private LinkedList<String> partners;
	private LinkedList<String> projectes;
	private boolean active;
	
	public ObjectId getId() {
		return this._id;
	}
	
	public void setId(ObjectId _id) {
		this._id = _id;
	}
	
	public void generateId() {
		if (this._id == null) this._id = new ObjectId();
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getImatge() {
		return imatge;
	}
	public void setImatge(String imatge) {
		this.imatge = imatge;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getFacebook() {
		return facebook;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public String getLinkedin() {
		return linkedin;
	}
	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}
	public String getMotDePas() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getDataNaix() {
		return dataNaix;
	}
	public void setDataNaix(Date dataNaix) {
		this.dataNaix = dataNaix;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public LinkedList<String> getPartners() {
		return partners;
	}
	public void setPartners(LinkedList<String> partners) {
		this.partners = partners;
	}
	public LinkedList<String> getProjectes() {
		return projectes;
	}
	public void setProjectes(LinkedList<String> projectes) {
		this.projectes = projectes;
	}
	public User(String nom, String username, String imatge, String twitter,
			String facebook, String linkedin, String password, Date dataNaix,
			String sexe, String pais, LinkedList<String> partners,
			LinkedList<String> projectes) {
		super();
		this.nom = nom;
		this.username = username;
		this.imatge = imatge;
		this.twitter = twitter;
		this.facebook = facebook;
		this.linkedin = linkedin;
		this.password = password;
		this.dataNaix = dataNaix;
		this.sexe = sexe;
		this.pais = pais;
		this.partners = partners;
		this.projectes = projectes;
	}
	
	public User() {
		super();
	}

	public boolean containsField(String field) {
		return (field.equals(Global.A_DATANAIX) || field.equals(Global.A_FACEBOOK) || field.equals(Global.A_ID) || field.equals(Global.A_IMATGE) || field.equals(Global.A_LINKEDIN) || field.equals(Global.A_NOM) || field.equals(Global.A_PAIS) || field.equals(Global.A_PARTNERS) || field.equals(Global.A_PASSWORD) || field.equals(Global.A_PROJECTES) || field.equals(Global.A_SEXE) || field.equals(Global.A_TWITTER) || field.equals(Global.A_USERNAME));
	}
	
	@Deprecated
	public boolean containsKey(String key) {
		return containsField(key);
	}
	
	public Object get(String field) {
		if (field.equals(Global.A_DATANAIX)) return this.dataNaix;
		if (field.equals(Global.A_FACEBOOK)) return this.facebook;
		if (field.equals(Global.A_IMATGE)) return this.imatge;
		if (field.equals(Global.A_LINKEDIN)) return this.linkedin;
		if (field.equals(Global.A_NOM)) return this.nom;
		if (field.equals(Global.A_PAIS)) return this.pais;
		if (field.equals(Global.A_PARTNERS)) return this.partners;
		if (field.equals(Global.A_PASSWORD)) return this.password;
		if (field.equals(Global.A_PROJECTES)) return this.projectes;
		if (field.equals(Global.A_SEXE)) return this.sexe;
		if (field.equals(Global.A_TWITTER)) return this.twitter;
		if (field.equals(Global.A_USERNAME)) return this.username;
		if (field.equals(Global.A_ID)) return this._id;
		return null;
	}
	
	public Set<String> keySet() {
		Set<String> set = new HashSet<String>();
		set.add(Global.A_DATANAIX);
		set.add(Global.A_FACEBOOK);
		set.add(Global.A_ID);
		set.add(Global.A_IMATGE);
		set.add(Global.A_LINKEDIN);
		set.add(Global.A_NOM);
		set.add(Global.A_PAIS);
		set.add(Global.A_PARTNERS);
		set.add(Global.A_PASSWORD);
		set.add(Global.A_PROJECTES);
		set.add(Global.A_SEXE);
		set.add(Global.A_TWITTER);
		set.add(Global.A_USERNAME);
		return set;
	}
	@SuppressWarnings("unchecked")
	
	public Object put(String field, Object object) {
		if (field.equals(Global.A_DATANAIX)) {
			this.dataNaix = (Date) object;
			return object;
		}
		if (field.equals(Global.A_FACEBOOK)) {
			this.facebook = (String) object;
			return object;
		}
		if (field.equals(Global.A_IMATGE)) {
			this.imatge = (String) object;
			return object;
		}
		if (field.equals(Global.A_LINKEDIN)) {
			this.linkedin = (String) object;
			return object;
		}
		if (field.equals(Global.A_NOM)) {
			this.nom = (String) object;
			return object;
		}
		if (field.equals(Global.A_PAIS)) {
			this.pais = (String) object;
			return object;
		}
		if (field.equals(Global.A_PARTNERS)) {
			this.partners = (LinkedList<String>) object;
			return object;
		}
		if (field.equals(Global.A_PASSWORD)) {
			this.password = (String) object;
			return object;
		}
		if (field.equals(Global.A_PROJECTES)) {
			this.projectes = (LinkedList<String>) object;
			return object;
		}
		if (field.equals(Global.A_SEXE)) {
			this.sexe = (String) object;
			return object;
		}
		if (field.equals(Global.A_TWITTER)) {
			this.twitter = (String) object;
			return object;
		}
		if (field.equals(Global.A_USERNAME)) {
			this.username = (String) object;
			return object;
		}
		if (field.equals(Global.A_ID)) {
			this._id = (ObjectId) object;
			return object;
		}
		return null;
	}
	
	public void putAll(BSONObject bson) {
		for (String key : bson.keySet()) put(key, bson.get(key));
	}
	@SuppressWarnings("unchecked")
	
	public void putAll(@SuppressWarnings("rawtypes") Map map) {
		for (Map.Entry<String, Object> entry : (Set<Map.Entry<String, Object>>) map.entrySet()) 
			put(entry.getKey().toString(), entry.getValue());
	}
	
	public Object removeField(String arg0) {
		throw new RuntimeException("Unsupported method.");
	}
	@SuppressWarnings("rawtypes")
	
	public Map toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (this._id != null) map.put(Global.A_ID, this._id);
		if (this.dataNaix != null) map.put(Global.A_DATANAIX, this.dataNaix);
		if (this.facebook != null) map.put(Global.A_FACEBOOK, this.facebook);
		if (this.imatge != null) map.put(Global.A_IMATGE, this.imatge);
		if (this.linkedin != null) map.put(Global.A_LINKEDIN, this.linkedin);
		if (this.nom != null) map.put(Global.A_NOM, this.nom);
		if (this.pais != null) map.put(Global.A_PAIS, this.pais);
		if (this.partners != null) map.put(Global.A_PARTNERS, this.partners);
		if (this.password != null) map.put(Global.A_PASSWORD, this.password);
		if (this.projectes != null) map.put(Global.A_PROJECTES, this.projectes);
		if (this.sexe != null) map.put(Global.A_SEXE, this.sexe);
		if (this.twitter != null) map.put(Global.A_TWITTER, this.twitter);
		if (this.username != null) map.put(Global.A_USERNAME, this.username);
		return map;
	}
	
	public boolean isPartialObject() {
		return false;
	}
	
	public void markAsPartialObject() {
		throw new RuntimeException("Method not implemented.");
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
