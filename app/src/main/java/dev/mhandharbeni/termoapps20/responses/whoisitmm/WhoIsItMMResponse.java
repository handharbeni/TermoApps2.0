package dev.mhandharbeni.termoapps20.responses.whoisitmm;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class WhoIsItMMResponse{

	@SerializedName("reference")
	private String reference;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("err_code")
	private int errCode;

	@SerializedName("time")
	private String time;

	@SerializedName("status")
	private String status;

	public void setReference(String reference){
		this.reference = reference;
	}

	public String getReference(){
		return reference;
	}

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

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
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
			"WhoIsItMMResponse{" + 
			"reference = '" + reference + '\'' + 
			",data = '" + data + '\'' + 
			",err_code = '" + errCode + '\'' + 
			",time = '" + time + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}