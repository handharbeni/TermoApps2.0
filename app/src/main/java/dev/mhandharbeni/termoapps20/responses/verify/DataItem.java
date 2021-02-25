package dev.mhandharbeni.termoapps20.responses.verify;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("person_in_picture")
	private String personInPicture;

	@SerializedName("score")
	private String score;

	@SerializedName("is_same_person")
	private boolean isSamePerson;

	@SerializedName("pip_nik")
	private String pipNik;

	public void setPersonInPicture(String personInPicture){
		this.personInPicture = personInPicture;
	}

	public String getPersonInPicture(){
		return personInPicture;
	}

	public void setScore(String score){
		this.score = score;
	}

	public String getScore(){
		return score;
	}

	public void setIsSamePerson(boolean isSamePerson){
		this.isSamePerson = isSamePerson;
	}

	public boolean isIsSamePerson(){
		return isSamePerson;
	}

	public void setPipNik(String pipNik){
		this.pipNik = pipNik;
	}

	public String getPipNik(){
		return pipNik;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"person_in_picture = '" + personInPicture + '\'' + 
			",score = '" + score + '\'' + 
			",is_same_person = '" + isSamePerson + '\'' + 
			",pip_nik = '" + pipNik + '\'' + 
			"}";
		}
}