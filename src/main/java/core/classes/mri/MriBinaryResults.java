package core.classes.mri;

public class MriBinaryResults {
	private int resultId;
	private int resultValue;
	private MriTestRequest mriTestRequest;
	public int getResultId() {
		return resultId;
	}
	public void setResultId(int resultId) {
		this.resultId = resultId;
	}
	public int getResultValue() {
		return resultValue;
	}
	public void setResultValue(int resultValue) {
		this.resultValue = resultValue;
	}
	public MriTestRequest getMriTestRequest() {
		return mriTestRequest;
	}
	public void setMriTestRequest(MriTestRequest mriTestRequest) {
		this.mriTestRequest = mriTestRequest;
	}
	public MriBinaryResults(int resultId, int resultValue,
			MriTestRequest mriTestRequest) {
		super();
		this.resultId = resultId;
		this.resultValue = resultValue;
		this.mriTestRequest = mriTestRequest;
	}
	public MriBinaryResults() {
	}
}
