package dev.mhandharbeni.termoapps20.responses.whoisitmm;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("person_in_picture")
	private String personInPicture;

	@SerializedName("pip_nik")
	private String pipNik;

	@SerializedName("pip_loc")
	private List<Integer> pipLoc;

	@SerializedName("pip_real")
	private List<String> pipReal;

	public void setPersonInPicture(String personInPicture){
		this.personInPicture = personInPicture;
	}

	public String getPersonInPicture(){
		return personInPicture;
	}

	public void setPipNik(String pipNik){
		this.pipNik = pipNik;
	}

	public String getPipNik(){
		return pipNik;
	}

	public void setPipLoc(List<Integer> pipLoc){
		this.pipLoc = pipLoc;
	}

	public List<Integer> getPipLoc(){
		return pipLoc;
	}

	public void setPipReal(List<String> pipReal){
		this.pipReal = pipReal;
	}

	public List<String> getPipReal(){
		return pipReal;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"person_in_picture = '" + personInPicture + '\'' + 
			",pip_nik = '" + pipNik + '\'' + 
			",pip_loc = '" + pipLoc + '\'' + 
			",pip_real = '" + pipReal + '\'' + 
			"}";
		}
}