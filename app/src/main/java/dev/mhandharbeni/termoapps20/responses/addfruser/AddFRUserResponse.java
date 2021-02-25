package dev.mhandharbeni.termoapps20.responses.addfruser;

import com.google.gson.annotations.SerializedName;

public class AddFRUserResponse{

	@SerializedName("err_code")
	private int errCode;

	@SerializedName("status")
	private String status;

	public void setErrCode(int errCode){
		this.errCode = errCode;
	}

	public int getErrCode(){
		return errCode;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"AddFRUserResponse{" + 
			"err_code = '" + errCode + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}