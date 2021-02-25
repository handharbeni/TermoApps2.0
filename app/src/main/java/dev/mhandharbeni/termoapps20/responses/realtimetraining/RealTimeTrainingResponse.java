package dev.mhandharbeni.termoapps20.responses.realtimetraining;

import com.google.gson.annotations.SerializedName;

public class RealTimeTrainingResponse{

	@SerializedName("message")
	private String message;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"RealTimeTrainingResponse{" + 
			"message = '" + message + '\'' + 
			"}";
		}
}