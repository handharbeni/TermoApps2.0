package dev.mhandharbeni.termoapps20.responses.verify;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class VerifyResponse{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("err_code")
	private int errCode;

	@SerializedName("status")
	private String status;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

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
			"VerifyResponse{" + 
			"data = '" + data + '\'' + 
			",err_code = '" + errCode + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}